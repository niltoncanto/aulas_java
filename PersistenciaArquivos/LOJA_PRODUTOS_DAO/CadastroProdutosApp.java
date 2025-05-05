import java.util.Scanner;
import java.util.List;

// Classe principal que contém o menu para interação com o usuário
public class CadastroProdutosApp {
    public static void main(String[] args) {
        // Criação do DAO que manipula o arquivo de produtos
        ProdutoDAO dao = new ProdutoArquivoDAO();
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        // Estrutura de repetição para manter o menu ativo até o usuário escolher sair
        do {
            // Exibição das opções do menu
            System.out.println("\n=== MENU ===");
            System.out.println("1. Adicionar produto");
            System.out.println("2. Listar produtos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            // Leitura da opção digitada pelo usuário
            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
					case 0:
                        // Finaliza o programa
                        System.out.println("Encerrando...");
                        break;
						
                    case 1:
                        // Solicita os dados do novo produto
                        System.out.print("Nome do produto: ");
                        String nome = scanner.nextLine();

                        System.out.print("Preço do produto: ");
                        double preco = Double.parseDouble(scanner.nextLine());

                        System.out.print("Quantidade do produto: ");
                        int quantidade = Integer.parseInt(scanner.nextLine());

                        // Criação do objeto Produto e chamada do DAO para salvar
                        Produto novoProduto = new Produto(nome, preco, quantidade);
                        dao.adicionarProduto(novoProduto);
                        System.out.println("Produto adicionado com sucesso!");
                        break;

                    case 2:
                        // Chamada ao DAO para listar os produtos armazenados
                        List<Produto> produtos = dao.listarProdutos();
                        System.out.println("\n--- Lista de Produtos ---");
                        for (Produto p : produtos) {
                            System.out.println(p);
                        }
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }

            } catch (NumberFormatException e) {
                // Trata erros de conversão de números
                System.out.println("Erro: entrada inválida. Use apenas números onde solicitado.");
            } catch (Exception e) {
                // Captura outros erros inesperados
                System.out.println("Erro inesperado: " + e.getMessage());
            }

        } while (opcao != 0);

        scanner.close(); // Fecha o Scanner ao final
    }
}

