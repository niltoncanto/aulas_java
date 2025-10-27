// Main.java
// Esqueleto do sistema de E-commerce em Java, convertido do template Python.
// Objetivo: fornecer somente as estruturas básicas + comentários orientando a implementação.
// Obs.: para valores monetários, prefira BigDecimal a double.

import java.math.BigDecimal;     // recomendado para preços (evita erro de ponto flutuante)
import java.util.*;              // List, Map, UUID, etc. (use conforme necessário)

/* =========================
   CLASSE PESSOA
   ========================= */
/**
 * Representa uma pessoa com atributos básicos.
 * Atributos: nome, cpf, email.
 * Tarefas do aluno:
 *  - Implementar construtor
 *  - (Opcional) Validar CPF / email
 *  - Criar getters/setters conforme necessidade
 */
class Pessoa {
    // Atributos (privados para encapsulamento)
    private String nome;   // nome completo da pessoa
    private String cpf;    // documento (string)
    private String email;  // email de contato

    // Construtor: receber nome, cpf, email e armazenar nos atributos
    public Pessoa(String nome, String cpf, String email) {
        // TODO: atribuir campos e (opcional) validar entradas
    }

    // Getters/Setters (implementar conforme necessidade didática)
    // public String getNome() { ... }
    // public void setNome(String nome) { ... }
    // public String getCpf() { ... }
    // public void setCpf(String cpf) { ... }
    // public String getEmail() { ... }
    // public void setEmail(String email) { ... }

    // (Opcional) toString para facilitar depuração/listagem
    // @Override public String toString() { ... }
}

/* =========================
   CLASSE CLIENTE
   ========================= */
/**
 * Representa um cliente do e-commerce, associado a uma Pessoa.
 * Atributos: pessoa (composição), idCliente (String).
 * Tarefas do aluno:
 *  - Implementar construtor recebendo Pessoa
 *  - Gerar idCliente via UUID.randomUUID().toString()
 *  - Criar getters conforme necessidade
 */
class Cliente {
    private Pessoa pessoa;       // dados da pessoa
    private String idCliente;    // identificador único (gerado por UUID)

    public Cliente(Pessoa pessoa) {
        // TODO: armazenar pessoa
        // TODO: this.idCliente = UUID.randomUUID().toString();
    }

    // Getters necessários
    // public Pessoa getPessoa() { ... }
    // public String getIdCliente() { ... }

    // @Override public String toString() { ... }
}

/* =========================
   CLASSE PRODUTO
   ========================= */
/**
 * Representa um produto disponível para venda.
 * Atributos: nome, preco (BigDecimal), estoque (int).
 * Tarefas do aluno:
 *  - Implementar construtor
 *  - (Opcional) Validar preço >= 0 e estoque >= 0
 *  - Getters/Setters necessários (ex.: atualizar preço)
 */
class Produto {
    private String nome;           // nome do produto
    private BigDecimal preco;      // preço unitário (usar BigDecimal)
    private int estoque;           // quantidade disponível

    public Produto(String nome, BigDecimal preco, int estoque) {
        // TODO: atribuir campos e (opcional) validar valores
    }

    // Getters/Setters conforme necessidade
    // public String getNome() { ... }
    // public void setNome(String nome) { ... }
    // public BigDecimal getPreco() { ... }
    // public void setPreco(BigDecimal preco) { ... }
    // public int getEstoque() { ... }
    // public void setEstoque(int estoque) { ... }

    // (Opcional) métodos utilitários como podeAtender(qtd), debitar/creditar
    // mas deixe para o momento da confirmação do pedido, se o enunciado exigir.
}

/* =========================
   CLASSE ITEMPEDIDO
   ========================= */
/**
 * Representa um item dentro de um pedido.
 * Contém um produto e uma quantidade.
 * Atributos: produto, quantidade.
 * Tarefas do aluno:
 *  - Implementar construtor
 *  - Implementar calcularTotal(): preco * quantidade
 */
class ItemPedido {
    private Produto produto;   // referência ao produto escolhido
    private int quantidade;    // quantidade solicitada

    public ItemPedido(Produto produto, int quantidade) {
        // TODO: atribuir campos e (opcional) validar quantidade > 0
    }

    /**
     * Retorna o subtotal deste item.
     * Fórmula: produto.preco * quantidade.
     */
    public BigDecimal calcularTotal() {
        // TODO: retornar produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
        return null; // placeholder
    }

    // Getters necessários
    // public Produto getProduto() { ... }
    // public int getQuantidade() { ... }
}

/* =========================
   CLASSE PEDIDO
   ========================= */
/**
 * Representa um pedido realizado por um cliente.
 * Contém o cliente e uma lista de itens.
 * Atributos: cliente, itens (List<ItemPedido>).
 * Tarefas do aluno:
 *  - Implementar construtor (lista vazia inicialmente)
 *  - Implementar adicionarItem(produto, quantidade)
 *  - Implementar calcularTotal(): soma dos totais dos itens
 *  - (Opcional) confirmar(): validar estoque e debitar (se fizer parte do enunciado)
 */
class Pedido {
    private Cliente cliente;            // quem realizou o pedido
    private List<ItemPedido> itens;     // itens que compõem o pedido

    public Pedido(Cliente cliente) {
        // TODO: atribuir cliente e inicializar itens = new ArrayList<>();
    }

    /**
     * Adiciona um item ao pedido a partir de um produto e uma quantidade.
     */
    public void adicionarItem(Produto produto, int quantidade) {
        // TODO: criar ItemPedido e adicionar à lista
    }

    /**
     * Calcula o total do pedido somando os subtotais dos itens.
     */
    public BigDecimal calcularTotal() {
        // TODO: iterar pelos itens e somar it.calcularTotal()
        return null; // placeholder
    }

    // Getters necessários
    // public Cliente getCliente() { ... }
    // public List<ItemPedido> getItens() { ... }
}

/* =========================
   CLASSE MENU
   ========================= */
/**
 * Gerencia o e-commerce, permitindo o cadastro de clientes, produtos e pedidos.
 * Atributos: clientes, produtos, pedidos (coleções em memória).
 * Dicas:
 *  - Para busca rápida de cliente por CPF, use Map<String, Cliente>.
 *  - Para produtos, pode usar Map<String, Produto> (por ID) ou armazenar uma lista e buscar por nome.
 *  - Para pedidos, use List<Pedido>.
 * Tarefas do aluno:
 *  - Implementar os métodos conforme descrito no template Python original
 *  - Definir uma CLI simples em exibirMenu() (Scanner + switch)
 */
class Menu {
    // Estruturas de dados sugeridas
    private Map<String, Cliente> clientes;  // por CPF
    private Map<String, Produto> produtos;  // por ID ou por nome (defina a chave)
    private List<Pedido> pedidos;           // lista de pedidos

    public Menu() {
        // TODO: inicializar as coleções (ex.: new HashMap<>(), new ArrayList<>())
    }

    /**
     * Cadastra um cliente a partir de nome, cpf e email.
     */
    public void cadastrarCliente(String nome, String cpf, String email) {
        // TODO: criar Pessoa, criar Cliente e inserir em 'clientes' por CPF
    }

    /**
     * Cadastra um produto a partir de nome, preço e estoque.
     */
    public void cadastrarProduto(String nome, BigDecimal preco, int estoque) {
        // TODO: criar Produto e inserir em 'produtos'
        // Dica: se usar Map por ID, gere um ID (UUID) dentro do Produto
    }

    /**
     * Cria um pedido para o CPF informado.
     * Fluxo sugerido:
     *  - Buscar Cliente por CPF
     *  - Permitir adicionar itens (loop de leitura)
     *  - Mostrar total
     *  - (Opcional) Confirmar e debitar estoque
     */
    public void criarPedido(String cpf) {
        // TODO: implementar interação conforme proposta da disciplina
    }

    /**
     * Lista pedidos (resumo).
     */
    public void listarPedidos() {
        // TODO: iterar sobre 'pedidos' e imprimir um resumo
    }

    /**
     * Exibe menu e executa as opções escolhidas (CLI).
     * Dica: usar Scanner para ler opções e dados.
     */
    public void exibirMenu() {
        // TODO: laço principal (while), imprimir opções e chamar métodos acima
    }
}

/* =========================
   PONTO DE ENTRADA
   ========================= */
/**
 * Método main: cria o Menu e chama exibirMenu().
 * Aluno deve implementar a lógica interna do Menu.
 */
public class Ecommerce_ex {
    public static void main(String[] args) {
        Menu menu = new Menu();   // TODO: certifique-se de inicializar as coleções no construtor
        menu.exibirMenu();        // TODO: implementar fluxo do menu
    }
}

