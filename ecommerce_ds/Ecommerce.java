// Main.java
// Sistema de E-commerce (versão Java) baseado no enunciado do PDF e no template Python.
// Comentários detalhados foram incluídos para facilitar o uso didático.

import java.math.BigDecimal;           // Representação correta de valores monetários
import java.util.*;                    // List, Map, Scanner, UUID etc.

////////////////////////////////////////////////////////////////////////////////
// Classe Pessoa: representa dados básicos (nome, cpf, email) usados por Cliente
////////////////////////////////////////////////////////////////////////////////
class Pessoa {
    // Atributos privados para manter encapsulamento
    private final String nome;   // nome completo da pessoa
    private final String cpf;    // documento no formato texto (não calcularemos máscara aqui)
    private final String email;  // email de contato

    // Construtor: recebe os três campos obrigatórios
    public Pessoa(String nome, String cpf, String email) {
        this.nome  = Objects.requireNonNull(nome,  "nome obrigatório");
        this.cpf   = Objects.requireNonNull(cpf,   "cpf obrigatório");
        this.email = Objects.requireNonNull(email, "email obrigatório");
    }

    // Getters: acesso somente leitura
    public String getNome()  { return nome; }
    public String getCpf()   { return cpf; }
    public String getEmail() { return email; }

    // toString: útil para depuração e listagens
    @Override
    public String toString() {
        return "Pessoa{nome='" + nome + "', cpf='" + cpf + "', email='" + email + "'}";
    }
}

////////////////////////////////////////////////////////////////////////////////
// Classe Cliente: associa uma Pessoa e um identificador único (UUID)
////////////////////////////////////////////////////////////////////////////////
class Cliente {
    private final Pessoa pessoa;        // composição: Cliente contém uma Pessoa
    private final String idCliente;     // identificador único como String (UUID)

    // Construtor: gera um UUID automaticamente
    public Cliente(Pessoa pessoa) {
        this.pessoa = Objects.requireNonNull(pessoa, "pessoa obrigatória");
        this.idCliente = UUID.randomUUID().toString(); // equivalente a uuid.uuid4() no Python
    }

    public Pessoa getPessoa()      { return pessoa; }
    public String getIdCliente()   { return idCliente; }

    @Override
    public String toString() {
        return "Cliente{id='" + idCliente + "', " + pessoa + "}";
    }
}

////////////////////////////////////////////////////////////////////////////////
// Classe Produto: representa item vendável com nome, preço (BigDecimal) e estoque
////////////////////////////////////////////////////////////////////////////////
class Produto {
    private final String idProduto;    // identificador único do produto
    private String nome;               // nome do produto
    private BigDecimal preco;          // preço unitário (BigDecimal evita problemas de ponto flutuante)
    private int estoque;               // quantidade disponível

    // Construtor: inicializa todos os campos
    public Produto(String nome, BigDecimal preco, int estoque) {
        this.idProduto = UUID.randomUUID().toString();   // gera id único
        this.nome = Objects.requireNonNull(nome, "nome obrigatório");
        this.preco = Objects.requireNonNull(preco, "preço obrigatório");
        if (estoque < 0) throw new IllegalArgumentException("estoque não pode ser negativo");
        this.estoque = estoque;
    }

    // Getters e setters mínimos (apenas onde faz sentido mudar)
    public String getIdProduto()  { return idProduto; }
    public String getNome()       { return nome; }
    public BigDecimal getPreco()  { return preco; }
    public int getEstoque()       { return estoque; }

    public void setNome(String nome)            { this.nome = Objects.requireNonNull(nome); }
    public void setPreco(BigDecimal novoPreco)  { this.preco = Objects.requireNonNull(novoPreco); }

    // Métodos de estoque: debitar/creditar com validação simples
    public boolean podeAtender(int quantidade) {
        return quantidade > 0 && estoque >= quantidade;
    }
    public void debitarEstoque(int quantidade) {
        if (!podeAtender(quantidade)) throw new IllegalStateException("estoque insuficiente");
        this.estoque -= quantidade;
    }
    public void creditarEstoque(int quantidade) {
        if (quantidade < 0) throw new IllegalArgumentException("quantidade inválida");
        this.estoque += quantidade;
    }

    @Override
    public String toString() {
        return "Produto{id='" + idProduto + "', nome='" + nome + "', preco=" + preco + ", estoque=" + estoque + "}";
    }
}

////////////////////////////////////////////////////////////////////////////////
// Classe ItemPedido: liga um Produto a uma quantidade e calcula subtotal
////////////////////////////////////////////////////////////////////////////////
class ItemPedido {
    private final Produto produto;  // referência ao produto escolhido
    private final int quantidade;   // quantidade solicitada

    public ItemPedido(Produto produto, int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("quantidade deve ser > 0");
        this.produto = Objects.requireNonNull(produto, "produto obrigatório");
        this.quantidade = quantidade;
    }

    public Produto getProduto()   { return produto; }
    public int getQuantidade()    { return quantidade; }

    // calcular_total: preço unitário * quantidade
    public BigDecimal calcularTotal() {
        return produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public String toString() {
        return "ItemPedido{produto=" + produto.getNome() + ", qtd=" + quantidade + ", subtotal=" + calcularTotal() + "}";
    }
}

////////////////////////////////////////////////////////////////////////////////
// Enum StatusPedido: ajuda a tornar explícito o ciclo de vida do pedido
////////////////////////////////////////////////////////////////////////////////
enum StatusPedido { ABERTO, CONFIRMADO, CANCELADO }

////////////////////////////////////////////////////////////////////////////////
// Classe Pedido: contém o Cliente e uma lista de itens; confirma e calcula total
////////////////////////////////////////////////////////////////////////////////
class Pedido {
    private final String idPedido;         // identificador único do pedido
    private final Cliente cliente;         // quem fez o pedido
    private final List<ItemPedido> itens;  // itens (produto + quantidade)
    private StatusPedido status;           // estado atual (ABERTO/CONFIRMADO/CANCELADO)

    // Construtor: inicia a lista de itens vazia e status ABERTO
    public Pedido(Cliente cliente) {
        this.idPedido = UUID.randomUUID().toString();
        this.cliente = Objects.requireNonNull(cliente, "cliente obrigatório");
        this.itens = new ArrayList<>();
        this.status = StatusPedido.ABERTO;
    }

    public String getIdPedido()         { return idPedido; }
    public Cliente getCliente()         { return cliente; }
    public List<ItemPedido> getItens()  { return Collections.unmodifiableList(itens); }
    public StatusPedido getStatus()     { return status; }

    // adicionar_item: apenas adiciona enquanto o pedido está ABERTO (não debita estoque aqui)
    public void adicionarItem(Produto produto, int quantidade) {
        if (status != StatusPedido.ABERTO) throw new IllegalStateException("pedido não está ABERTO");
        itens.add(new ItemPedido(produto, quantidade));
    }

    // calcular_total: soma dos subtotais
    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido it : itens) {
            total = total.add(it.calcularTotal());
        }
        return total;
    }

    // confirmar: valida estoques de todos os itens e então debita de cada produto
    public boolean confirmar() {
        if (status != StatusPedido.ABERTO) return false;         // só confirma uma vez
        // 1) valida se todos os produtos têm estoque suficiente
        for (ItemPedido it : itens) {
            if (!it.getProduto().podeAtender(it.getQuantidade())) {
                return false; // se algum falhar, aborta sem debitar nada
            }
        }
        // 2) debita dos estoques (agora com segurança)
        for (ItemPedido it : itens) {
            it.getProduto().debitarEstoque(it.getQuantidade());
        }
        status = StatusPedido.CONFIRMADO;                         // atualiza status
        return true;
    }

    // cancelar: apenas troca o status se ainda estiver ABERTO (não há débito para reverter)
    public void cancelar() {
        if (status == StatusPedido.ABERTO) status = StatusPedido.CANCELADO;
    }

    @Override
    public String toString() {
        return "Pedido{id='" + idPedido + "', cliente=" + cliente.getPessoa().getNome()
               + ", itens=" + itens.size() + ", total=" + calcularTotal() + ", status=" + status + "}";
    }
}

////////////////////////////////////////////////////////////////////////////////
// Classe Menu: gerencia cadastros e a interação via linha de comando
////////////////////////////////////////////////////////////////////////////////
class Menu {
    // Estruturas de dados do “sistema” (em memória):
    // - clientes por CPF (Map para acesso rápido)
    // - produtos por ID (Map) e lista de pedidos
    private final Map<String, Cliente> clientesPorCpf = new HashMap<>();
    private final Map<String, Produto> produtosPorId  = new HashMap<>();
    private final List<Pedido> pedidos                = new ArrayList<>();
    private final Scanner in = new Scanner(System.in); // leitura de entradas do usuário

    // cadastrar_cliente: cria Pessoa e Cliente e guarda por CPF
    public void cadastrarCliente(String nome, String cpf, String email) {
        Pessoa p = new Pessoa(nome, cpf, email);
        Cliente c = new Cliente(p);
        clientesPorCpf.put(p.getCpf(), c);
        System.out.println("Cliente cadastrado: " + c);
    }

    // cadastrar_produto: cria Produto e adiciona ao Map por ID
    public void cadastrarProduto(String nome, BigDecimal preco, int estoque) {
        Produto pr = new Produto(nome, preco, estoque);
        produtosPorId.put(pr.getIdProduto(), pr);
        System.out.println("Produto cadastrado: " + pr);
    }

    // criar_pedido: recebe CPF, permite adicionar itens e, ao final, confirmar (debitando estoque)
    public void criarPedido(String cpf) {
        Cliente c = clientesPorCpf.get(cpf);
        if (c == null) {
            System.out.println("Cliente não encontrado para CPF: " + cpf);
            return;
        }
        Pedido pedido = new Pedido(c);
        System.out.println("== Novo pedido para " + c.getPessoa().getNome());

        // Laço de inclusão de itens
        while (true) {
            listarProdutosSintetico(); // mostra ids e estoques atuais
            System.out.print("Digite o ID do produto (ou ENTER para encerrar a inclusão): ");
            String id = in.nextLine().trim();
            if (id.isEmpty()) break;

            Produto pr = produtosPorId.get(id);
            if (pr == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }

            System.out.print("Quantidade: ");
            int qtd;
            try {
                qtd = Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Quantidade inválida.");
                continue;
            }

            // Adiciona no pedido (a validação de estoque será feita na confirmação)
            try {
                pedido.adicionarItem(pr, qtd);
                System.out.println("Item adicionado: " + pr.getNome() + " x " + qtd);
            } catch (Exception e) {
                System.out.println("Falha ao adicionar item: " + e.getMessage());
            }
        }

        // Mostra resumo e total
        System.out.println("Itens no pedido:");
        for (ItemPedido it : pedido.getItens()) {
            System.out.println(" - " + it);
        }
        System.out.println("Total: " + pedido.calcularTotal());

        // Confirma e debita estoque conforme o enunciado do PDF
        System.out.print("Confirmar pedido? (s/n): ");
        String conf = in.nextLine().trim().toLowerCase();
        if (conf.equals("s")) {
            boolean ok = pedido.confirmar();
            if (ok) {
                pedidos.add(pedido); // só adiciona à lista se confirmado
                System.out.println("Pedido confirmado! ID: " + pedido.getIdPedido());
            } else {
                System.out.println("Não foi possível confirmar: estoque insuficiente em algum item.");
                pedido.cancelar();
            }
        } else {
            pedido.cancelar();
            System.out.println("Pedido cancelado.");
        }
    }

    // listar_pedidos: percorre a lista mostrando um resumo
    public void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Não há pedidos confirmados.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }

    // Utilitário: mostra produtos resumidos (ID, nome, preço, estoque)
    private void listarProdutosSintetico() {
        System.out.println("== Produtos cadastrados ==");
        if (produtosPorId.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (Produto pr : produtosPorId.values()) {
            System.out.println(pr.getIdProduto() + " | " + pr.getNome()
                    + " | R$" + pr.getPreco() + " | estoque=" + pr.getEstoque());
        }
    }

    // exibir_menu: loop principal de interação com o usuário
    public void exibirMenu() {
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1) Cadastrar cliente");
            System.out.println("2) Cadastrar produto");
            System.out.println("3) Criar pedido");
            System.out.println("4) Listar pedidos");
            System.out.println("0) Sair");
            System.out.print("Opção: ");

            String op = in.nextLine().trim();
            try {
                switch (op) {
                    case "1":
                        System.out.print("Nome: ");  String nome = in.nextLine().trim();
                        System.out.print("CPF: ");   String cpf  = in.nextLine().trim();
                        System.out.print("Email: "); String email= in.nextLine().trim();
                        cadastrarCliente(nome, cpf, email);
                        break;
                    case "2":
                        System.out.print("Nome: "); String nomeP = in.nextLine().trim();
                        System.out.print("Preço (ex: 199.90): ");
                        BigDecimal preco = new BigDecimal(in.nextLine().trim());
                        System.out.print("Estoque: ");
                        int estoque = Integer.parseInt(in.nextLine().trim());
                        cadastrarProduto(nomeP, preco, estoque);
                        break;
                    case "3":
                        System.out.print("CPF do cliente: ");
                        String cpfCli = in.nextLine().trim();
                        criarPedido(cpfCli);
                        break;
                    case "4":
                        listarPedidos();
                        break;
                    case "0":
                        System.out.println("Encerrando...");
                        return; // sai do método e encerra o programa
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                // Captura genérica para manter o programa vivo em caso de erro de entrada
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
// Classe pública com método main: ponto de entrada do programa
////////////////////////////////////////////////////////////////////////////////
public class Ecommerce {
    public static void main(String[] args) {
        // Cria o menu e inicia o loop de interação
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}
