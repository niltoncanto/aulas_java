# e_commerce_solution.py
# Solução de referência do sistema de E-commerce em Python.
# Objetivo didático: mostrar uma implementação clara a partir do esqueleto.
# Observações:
# - Para dinheiro, usamos decimal.Decimal (evita erros de ponto flutuante).
# - IDs são gerados com uuid.uuid4().
# - Estoque é checado/debitado somente na confirmação do pedido (fluxo mais realista).

import uuid                             # gera identificadores únicos (UUID)
from decimal import Decimal             # tipo apropriado para dinheiro
from typing import List, Dict, Optional # tipos para anotações estáticas


# =========================
# CLASSE PESSOA
# =========================
class Pessoa:
    """
    Representa uma pessoa com atributos básicos.
    Atributos: nome (str), cpf (str), email (str).
    """

    def __init__(self, nome: str, cpf: str, email: str) -> None:
        # Armazena os atributos informados
        self.nome = nome
        self.cpf = cpf
        self.email = email
        # Nota: validações (CPF, email) poderiam ser adicionadas aqui como extensão.

    def __repr__(self) -> str:
        # Representação útil para depuração e listagem
        return f"Pessoa(nome='{self.nome}', cpf='{self.cpf}', email='{self.email}')"


# =========================
# CLASSE CLIENTE
# =========================
class Cliente:
    """
    Representa um cliente do e-commerce.
    Atributos: pessoa (Pessoa), id_cliente (str).
    id_cliente é gerado automaticamente via UUID.
    """

    def __init__(self, pessoa: Pessoa) -> None:
        self.pessoa = pessoa
        self.id_cliente = str(uuid.uuid4())  # gera um identificador único

    def __repr__(self) -> str:
        return f"Cliente(id_cliente='{self.id_cliente}', pessoa={self.pessoa})"


# =========================
# CLASSE PRODUTO
# =========================
class Produto:
    """
    Representa um produto à venda.
    Atributos: nome (str), preco (Decimal), estoque (int).
    """

    def __init__(self, nome: str, preco: Decimal, estoque: int) -> None:
        # Guarda os atributos; conversão e validação simples
        self.nome = nome
        self.preco = Decimal(preco)          # garante que seja Decimal
        if self.preco < 0:
            raise ValueError("Preço não pode ser negativo.")
        if estoque < 0:
            raise ValueError("Estoque não pode ser negativo.")
        self.estoque = int(estoque)

    def __repr__(self) -> str:
        return f"Produto(nome='{self.nome}', preco={self.preco}, estoque={self.estoque})"


# =========================
# CLASSE ITEMPEDIDO
# =========================
class ItemPedido:
    """
    Representa um item dentro de um pedido: produto + quantidade.
    """

    def __init__(self, produto: Produto, quantidade: int) -> None:
        if quantidade <= 0:
            raise ValueError("Quantidade deve ser positiva.")
        self.produto = produto
        self.quantidade = int(quantidade)

    def calcular_total(self) -> Decimal:
        """
        Retorna o subtotal deste item: preco_unitario * quantidade.
        """
        return self.produto.preco * Decimal(self.quantidade)

    def __repr__(self) -> str:
        return f"ItemPedido(produto='{self.produto.nome}', quantidade={self.quantidade}, subtotal={self.calcular_total()})"


# =========================
# CLASSE PEDIDO
# =========================
class Pedido:
    """
    Representa um pedido realizado por um cliente.
    Atributos: cliente (Cliente), itens (List[ItemPedido]).
    Fluxo recomendado:
      - Criar pedido
      - Adicionar itens
      - Confirmar (valida e debita estoques)
    """

    def __init__(self, cliente: Cliente) -> None:
        self.cliente = cliente              # referência ao cliente que faz o pedido
        self.itens: List[ItemPedido] = []   # lista de itens inicialmente vazia
        self.id_pedido: str = str(uuid.uuid4())  # ID do pedido (opcional, mas prático)
        self.confirmado: bool = False       # status do pedido

    def adicionar_item(self, produto: Produto, quantidade: int) -> None:
        """
        Cria um ItemPedido e adiciona à lista de itens.
        """
        if self.confirmado:
            # Impede adicionar itens após confirmação (regra comum)
            raise RuntimeError("Não é possível adicionar itens em um pedido já confirmado.")
        self.itens.append(ItemPedido(produto, quantidade))

    def calcular_total(self) -> Decimal:
        """
        Soma os subtotais de todos os itens do pedido.
        """
        total = Decimal("0.00")
        for it in self.itens:
            total += it.calcular_total()
        return total

    def confirmar(self) -> bool:
        """
        Valida se há estoque suficiente para TODOS os itens.
        Se houver, debita os estoques e marca o pedido como confirmado.
        Retorna True em caso de sucesso; False se faltar estoque para algum produto.
        """
        if self.confirmado:
            return True  # já está confirmado

        # 1) Agrega as quantidades por produto para validação correta (mesmo produto repetido)
        demanda: Dict[Produto, int] = {}
        for it in self.itens:
            demanda[it.produto] = demanda.get(it.produto, 0) + it.quantidade

        # 2) Verifica se todos os produtos possuem estoque suficiente
        for produto, qtd_total in demanda.items():
            if produto.estoque < qtd_total:
                return False  # se faltar estoque para qualquer produto, não confirma

        # 3) Debita o estoque de todos os produtos (agora com segurança)
        for produto, qtd_total in demanda.items():
            produto.estoque -= qtd_total

        self.confirmado = True
        return True

    def __repr__(self) -> str:
        return f"Pedido(id='{self.id_pedido}', cliente='{self.cliente.pessoa.nome}', itens={len(self.itens)}, total={self.calcular_total()}, confirmado={self.confirmado})"


# =========================
# CLASSE MENU (CLI)
# =========================
class Menu:
    """
    Gerencia o e-commerce em memória.
    Estruturas:
      - clientes: Dict[str, Cliente]  (chave = CPF)
      - produtos: Dict[str, Produto]  (chave = ID do produto, gerado via UUID)
      - pedidos:  List[Pedido]
    """

    def __init__(self) -> None:
        # Dicionário de clientes indexado por CPF (acesso direto ao buscar cliente)
        self.clientes: Dict[str, Cliente] = {}
        # Dicionário de produtos indexado por ID (string UUID)
        self.produtos: Dict[str, Produto] = {}
        # Lista de pedidos confirmados
        self.pedidos: List[Pedido] = {}

        # Corrigindo: pedidos deve ser lista, não dicionário
        self.pedidos = []

    # -----------------------
    # CADASTRAR CLIENTE
    # -----------------------
    def cadastrar_cliente(self, nome: str, cpf: str, email: str) -> None:
        """
        Cria Pessoa -> Cliente e salva em self.clientes por CPF.
        Se já existir um cliente com o CPF, apenas substitui/atualiza.
        """
        pessoa = Pessoa(nome, cpf, email)
        cliente = Cliente(pessoa)
        self.clientes[cpf] = cliente
        print(f"[OK] Cliente cadastrado: {cliente}")

    # -----------------------
    # CADASTRAR PRODUTO
    # -----------------------
    def cadastrar_produto(self, nome: str, preco: Decimal, estoque: int) -> None:
        """
        Cria Produto e armazena em self.produtos sob um ID (UUID).
        """
        produto = Produto(nome, Decimal(preco), estoque)
        id_produto = str(uuid.uuid4())
        self.produtos[id_produto] = produto
        print(f"[OK] Produto cadastrado: {id_produto} | {produto.nome} | R${produto.preco} | estoque={produto.estoque}")

    # -----------------------
    # CRIAR PEDIDO
    # -----------------------
    def criar_pedido(self, cpf: str) -> None:
        """
        Fluxo:
          - Busca cliente por CPF.
          - Permite adicionar itens (loop).
          - Exibe total.
          - Pergunta se confirma; se sim, valida e debita estoque.
        """
        cliente = self.clientes.get(cpf)
        if not cliente:
            print("[ERRO] Cliente não encontrado para o CPF informado.")
            return

        pedido = Pedido(cliente)  # cria um pedido em aberto
        print(f"== Novo pedido para: {cliente.pessoa.nome}")

        # Laço de inclusão de itens
        while True:
            self._listar_produtos_sintetico()
            prod_id = input("ID do produto (ENTER para encerrar): ").strip()
            if prod_id == "":
                break  # encerra inclusão

            produto = self.produtos.get(prod_id)
            if not produto:
                print("[ERRO] Produto não encontrado.")
                continue

            # Lê quantidade
            try:
                qtd = int(input("Quantidade: ").strip())
                if qtd <= 0:
                    print("[ERRO] Quantidade deve ser positiva.")
                    continue
            except ValueError:
                print("[ERRO] Quantidade inválida.")
                continue

            # Adiciona item ao pedido (estoque será checado na confirmação)
            try:
                pedido.adicionar_item(produto, qtd)
                print(f"[OK] Item adicionado: {produto.nome} x {qtd}")
            except Exception as e:
                print(f"[ERRO] Falha ao adicionar item: {e}")

        # Exibe resumo do pedido e total
        if not pedido.itens:
            print("[AVISO] Pedido vazio. Nada a confirmar.")
            return

        print("\nItens do pedido:")
        for it in pedido.itens:
            print(f" - {it}")

        print(f"Total do pedido: R${pedido.calcular_total()}")

        # Confirmação do pedido: valida estoque e debita
        confirma = input("Confirmar pedido? (s/n): ").strip().lower()
        if confirma == "s":
            if pedido.confirmar():
                self.pedidos.append(pedido)  # guarda apenas pedidos confirmados
                print(f"[OK] Pedido confirmado! ID: {pedido.id_pedido}")
            else:
                print("[ERRO] Estoque insuficiente para um ou mais itens. Pedido não confirmado.")
        else:
            print("[INFO] Pedido cancelado pelo usuário.")

    # -----------------------
    # LISTAR PEDIDOS
    # -----------------------
    def listar_pedidos(self) -> None:
        """
        Lista pedidos confirmados com um resumo.
        """
        if not self.pedidos:
            print("[INFO] Não há pedidos confirmados.")
            return

        print("== Pedidos confirmados ==")
        for p in self.pedidos:
            print(f"- ID: {p.id_pedido} | Cliente: {p.cliente.pessoa.nome} | Itens: {len(p.itens)} | Total: R${p.calcular_total()}")

    # -----------------------
    # MENU (CLI)
    # -----------------------
    def exibir_menu(self) -> None:
        """
        Menu de linha de comando simples para interação manual.
        """
        while True:
            print("\n=== MENU ===")
            print("1) Cadastrar cliente")
            print("2) Cadastrar produto")
            print("3) Criar pedido")
            print("4) Listar pedidos")
            print("0) Sair")
            op = input("Opção: ").strip()

            try:
                if op == "1":
                    nome = input("Nome: ").strip()
                    cpf = input("CPF: ").strip()
                    email = input("Email: ").strip()
                    self.cadastrar_cliente(nome, cpf, email)

                elif op == "2":
                    nome = input("Nome do produto: ").strip()
                    preco_str = input("Preço (ex: 199.90): ").strip().replace(",", ".")
                    estoque_str = input("Estoque (inteiro): ").strip()
                    preco = Decimal(preco_str)
                    estoque = int(estoque_str)
                    self.cadastrar_produto(nome, preco, estoque)

                elif op == "3":
                    cpf = input("CPF do cliente: ").strip()
                    self.criar_pedido(cpf)

                elif op == "4":
                    self.listar_pedidos()

                elif op == "0":
                    print("Encerrando...")
                    return

                else:
                    print("[ERRO] Opção inválida.")

            except Exception as e:
                # Captura genérica para manter a CLI ativa mesmo em caso de erro
                print(f"[ERRO] {e}")

    # -----------------------
    # Utilitário: lista produtos
    # -----------------------
    def _listar_produtos_sintetico(self) -> None:
        """
        Exibe a lista de produtos com seus IDs (chave do dicionário), nome, preço e estoque.
        Facilita a seleção por ID na criação do pedido.
        """
        print("\n== Produtos cadastrados ==")
        if not self.produtos:
            print("(vazio)")
            return

        for prod_id, produto in self.produtos.items():
            print(f"{prod_id} | {produto.nome} | R${produto.preco} | estoque={produto.estoque}")


# =========================
# PONTO DE ENTRADA
# =========================
if __name__ == "__main__":
    # Instancia o Menu e inicia a CLI.
    menu = Menu()
    menu.exibir_menu()
