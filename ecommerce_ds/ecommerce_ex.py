# e_commerce.py
# Esqueleto do sistema de E-commerce em Python.
# Objetivo: fornecer somente as estruturas básicas + comentários orientando a implementação.
# Observações didáticas:
# - Para valores monetários, prefira 'decimal.Decimal' a 'float' (precisão).
# - IDs podem ser gerados com uuid.uuid4().
# - Se desejar, adicione validações (ex.: CPF, email) como extensão.

import uuid                     # gerar identificadores únicos (UUID)
from decimal import Decimal     # representação apropriada de dinheiro
from typing import List, Dict   # anotações de tipo (listas e dicionários)


# =========================
# CLASSE PESSOA
# =========================
class Pessoa:
    """
    Representa uma pessoa com atributos básicos.
    Atributos obrigatórios: nome, cpf, email.

    Tarefas do aluno:
    - Implementar o construtor (__init__) atribuindo os atributos.
    - (Opcional) Validar CPF / email.
    - (Opcional) Criar __str__/__repr__ para facilitar a depuração.
    """

    def __init__(self, nome: str, cpf: str, email: str) -> None:
        # TODO: atribuir os campos (ex.: self.nome = nome, etc.)
        # TODO (opcional): validar formato do cpf/email.
        pass


# =========================
# CLASSE CLIENTE
# =========================
class Cliente:
    """
    Representa um cliente do e-commerce, associado a uma Pessoa.
    Atributos: pessoa (objeto Pessoa), id_cliente (str).

    Tarefas do aluno:
    - Implementar o construtor recebendo uma Pessoa.
    - Gerar id_cliente com str(uuid.uuid4()).
    - (Opcional) Criar __str__/__repr__.
    """

    def __init__(self, pessoa: Pessoa) -> None:
        # TODO: self.pessoa = pessoa
        # TODO: self.id_cliente = str(uuid.uuid4())
        pass


# =========================
# CLASSE PRODUTO
# =========================
class Produto:
    """
    Representa um produto disponível para venda.
    Atributos: nome (str), preco (Decimal), estoque (int).

    Tarefas do aluno:
    - Implementar o construtor e armazenar os atributos.
    - (Opcional) Validar: preco >= 0 e estoque >= 0.
    - (Opcional) Criar __str__/__repr__.
    - (Opcional) Métodos utilitários (ex.: pode_atender, debitar, creditar) se o enunciado pedir.
    """

    def __init__(self, nome: str, preco: Decimal, estoque: int) -> None:
        # TODO: self.nome = nome
        # TODO: self.preco = preco (usar Decimal('199.90') ao instanciar)
        # TODO: self.estoque = estoque
        # TODO (opcional): validar não-negatividade de preco/estoque
        pass


# =========================
# CLASSE ITEMPEDIDO
# =========================
class ItemPedido:
    """
    Representa um item dentro de um pedido (produto + quantidade).
    Atributos: produto (Produto), quantidade (int).

    Tarefas do aluno:
    - Implementar o construtor.
    - Implementar calcular_total() = produto.preco * quantidade.
    """

    def __init__(self, produto: Produto, quantidade: int) -> None:
        # TODO: self.produto = produto
        # TODO: self.quantidade = quantidade
        # TODO (opcional): validar quantidade > 0
        pass

    def calcular_total(self) -> Decimal:
        """
        Retorna o subtotal do item:
        Fórmula: produto.preco * Decimal(quantidade).
        """
        # TODO: retornar self.produto.preco * Decimal(self.quantidade)
        raise NotImplementedError("Implementar cálculo do subtotal do item.")


# =========================
# CLASSE PEDIDO
# =========================
class Pedido:
    """
    Representa um pedido realizado por um cliente.
    Atributos: cliente (Cliente), itens (List[ItemPedido]).

    Tarefas do aluno:
    - Implementar o construtor inicializando a lista de itens vazia.
    - Implementar adicionar_item(produto, quantidade): cria ItemPedido e adiciona à lista.
    - Implementar calcular_total(): soma dos totais dos itens.
    - (Opcional) confirmar(): validar estoques e debitar (se fizer parte do enunciado).
    """

    def __init__(self, cliente: Cliente) -> None:
        # TODO: self.cliente = cliente
        # TODO: self.itens = []
        pass

    def adicionar_item(self, produto: Produto, quantidade: int) -> None:
        """
        Adiciona um ItemPedido à lista de itens.
        """
        # TODO: criar ItemPedido(produto, quantidade) e adicionar em self.itens
        raise NotImplementedError("Implementar inclusão de item no pedido.")

    def calcular_total(self) -> Decimal:
        """
        Calcula o total somando os subtotais dos itens.
        """
        # TODO: somar it.calcular_total() para cada item em self.itens
        raise NotImplementedError("Implementar cálculo do total do pedido.")


# =========================
# CLASSE MENU
# =========================
class Menu:
    """
    Gerencia o e-commerce: cadastro de clientes, produtos e pedidos.
    Sugestões de estruturas em memória:
    - clientes: Dict[str, Cliente]  (chave = CPF)
    - produtos: Dict[str, Produto]  (chave = ID ou nome; defina a estratégia)
    - pedidos: List[Pedido]

    Tarefas do aluno:
    - Implementar o construtor, inicializando as coleções.
    - Implementar métodos de cadastro e criação de pedidos.
    - Implementar exibir_menu() com interação via input() (CLI simples).
    """

    def __init__(self) -> None:
        # TODO: self.clientes: Dict[str, Cliente] = {}
        # TODO: self.produtos: Dict[str, Produto] = {}
        # TODO: self.pedidos: List[Pedido] = []
        pass

    def cadastrar_cliente(self, nome: str, cpf: str, email: str) -> None:
        """
        Cria Pessoa -> Cliente e armazena em self.clientes por CPF.
        """
        # TODO: implementar cadastro
        raise NotImplementedError("Implementar cadastro de cliente.")

    def cadastrar_produto(self, nome: str, preco: Decimal, estoque: int) -> None:
        """
        Cria Produto e armazena em self.produtos.
        Dica: se optar por chavear por ID, gere uma string com uuid.uuid4().
        """
        # TODO: implementar cadastro
        raise NotImplementedError("Implementar cadastro de produto.")

    def criar_pedido(self, cpf: str) -> None:
        """
        Fluxo sugerido:
        - Buscar cliente por CPF.
        - Permitir adicionar itens (loop de leitura).
        - Mostrar total.
        - (Opcional) Confirmar e debitar estoque (se previsto no enunciado).
        """
        # TODO: implementar criação do pedido conforme a proposta da disciplina
        raise NotImplementedError("Implementar criação de pedido.")

    def listar_pedidos(self) -> None:
        """
        Exibe um resumo dos pedidos armazenados.
        """
        # TODO: iterar por self.pedidos e imprimir/formatar
        raise NotImplementedError("Implementar listagem de pedidos.")

    def exibir_menu(self) -> None:
        """
        Exibe o menu e executa as opções escolhidas (CLI).
        Dica: usar input() e um loop while para o menu principal.
        """
        # TODO: implementar interface de linha de comando
        raise NotImplementedError("Implementar menu interativo.")


# =========================
# PONTO DE ENTRADA
# =========================
if __name__ == "__main__":
    # Instancia o Menu e inicia a CLI.
    # Aluno deve implementar os métodos do Menu para que a interação funcione.
    menu = Menu()
    menu.exibir_menu()
