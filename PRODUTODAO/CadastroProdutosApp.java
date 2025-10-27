import java.util.Scanner;

/**
 * Classe principal da aplicação de cadastro de produtos
 */
public class CadastroProdutosApp {
    private static Scanner scanner = new Scanner(System.in);
    private static IProdutoDAO produtoDAO = new ProdutoArquivoDAOImpl();
    
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE CADASTRO DE PRODUTOS ===");
        System.out.println();
        
        boolean executando = true;
        
        while (executando) {
            exibirMenu();
            int opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    adicionarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    System.out.println("Encerrando aplicação...");
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
            
            if (executando) {
                System.out.println();
                System.out.println("Pressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Exibe o menu principal
     */
    private static void exibirMenu() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│            MENU PRINCIPAL           │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 1. Adicionar um novo produto       │");
        System.out.println("│ 2. Listar todos os produtos        │");
        System.out.println("│ 3. Sair                            │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Escolha uma opção: ");
    }
    
    /**
     * Lê a opção escolhida pelo usuário
     * @return opção escolhida
     */
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Opção inválida
        }
    }
    
    /**
     * Adiciona um novo produto
     */
    private static void adicionarProduto() {
        System.out.println("\n=== ADICIONAR NOVO PRODUTO ===");
        
        try {
            System.out.print("Nome do produto: ");
            String nome = scanner.nextLine().trim();
            
            if (nome.isEmpty()) {
                System.out.println("Nome não pode estar vazio!");
                return;
            }
            
            System.out.print("Preço do produto (R$): ");
            double preco = Double.parseDouble(scanner.nextLine().replace(",", "."));
            
            if (preco < 0) {
                System.out.println("Preço não pode ser negativo!");
                return;
            }
            
            System.out.print("Quantidade em estoque: ");
            int quantidade = Integer.parseInt(scanner.nextLine());
            
            if (quantidade < 0) {
                System.out.println("Quantidade não pode ser negativa!");
                return;
            }
            
            Produto produto = new Produto(nome, preco, quantidade);
            
            if (produtoDAO.adicionar(produto)) {
                System.out.println("Produto adicionado com sucesso!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Erro: Formato de número inválido!");
        }
    }
    
    /**
     * Lista todos os produtos cadastrados
     */
    private static void listarProdutos() {
        System.out.println("\n=== LISTA DE PRODUTOS ===");
        
        java.util.List<Produto> produtos = produtoDAO.listarTodos();
        
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out.println("Total de produtos: " + produtos.size());
            System.out.println();
            
            // Cabeçalho da tabela
            System.out.println("┌─────────────────────────────────────────────────────────────┐");
            System.out.println("│                         PRODUTOS                            │");
            System.out.println("├─────────────────────────────────────────────────────────────┤");
            
            for (int i = 0; i < produtos.size(); i++) {
                Produto produto = produtos.get(i);
                System.out.printf("│ %-2d │ %-20s │ R$ %8.2f │ Qtd: %-8d │%n", 
                                (i + 1), 
                                produto.getNome(), 
                                produto.getPreco(), 
                                produto.getQuantidade());
            }
            
            System.out.println("└─────────────────────────────────────────────────────────────┘");
        }
    }
}
