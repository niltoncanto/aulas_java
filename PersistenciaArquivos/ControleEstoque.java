import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.text.DecimalFormat;

public class ControleEstoque {

    private static final String NOME_ARQUIVO = "produtos.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            // Exibe o menu principal
            System.out.println("\n=== CONTROLE DE ESTOQUE ===");
            System.out.println("1 - Adicionar produto");
            System.out.println("2 - Listar todos os produtos");
            System.out.println("3 - Listar produtos com estoque baixo");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    adicionarProduto(scanner);
                    break;
                case 2:
                    listarTodosProdutos();
                    break;
                case 3:
                    listarProdutosBaixoEstoque(scanner);
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close(); // Fecha o scanner
    }

    // Método para adicionar um produto no arquivo
    private static void adicionarProduto(Scanner scanner) {
        try {
            System.out.print("Nome do produto: ");
            String nome = scanner.nextLine();

            System.out.print("Preço (R$): ");
            double preco = scanner.nextDouble();

            System.out.print("Quantidade em estoque: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer

            BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO, true));
            writer.write(nome + ";" + preco + ";" + quantidade);
            writer.newLine();
            writer.close();

            System.out.println("Produto salvo com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao salvar produto: " + e.getMessage());
        }
    }

    // Método para listar todos os produtos do arquivo
    private static void listarTodosProdutos() {
        try {
            File arquivo = new File(NOME_ARQUIVO);

            if (!arquivo.exists()) {
                System.out.println("Nenhum produto cadastrado ainda.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String linha;
            DecimalFormat df = new DecimalFormat("#0.00");
            int contador = 1;

            System.out.println("\n=== LISTA DE PRODUTOS ===");

            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    String nome = dados[0];
                    double preco = Double.parseDouble(dados[1]);
                    int quantidade = Integer.parseInt(dados[2]);

                    System.out.printf("%d - %s | R$ %s | Estoque: %d%n",
                            contador++, nome, df.format(preco), quantidade);
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Erro ao ler produtos: " + e.getMessage());
        }
    }

    // Método para listar produtos com estoque baixo
    private static void listarProdutosBaixoEstoque(Scanner scanner) {
        try {
            System.out.print("Digite o limite mínimo de estoque: ");
            int limite = scanner.nextInt();
            scanner.nextLine();

            File arquivo = new File(NOME_ARQUIVO);

            if (!arquivo.exists()) {
                System.out.println("Nenhum produto cadastrado.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String linha;
            DecimalFormat df = new DecimalFormat("#0.00");
            int contador = 1;
            boolean encontrou = false;

            System.out.println("\n=== PRODUTOS COM ESTOQUE BAIXO (≤ " + limite + ") ===");

            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    String nome = dados[0];
                    double preco = Double.parseDouble(dados[1]);
                    int quantidade = Integer.parseInt(dados[2]);

                    if (quantidade <= limite) {
                        System.out.printf("%d - %s | R$ %s | Estoque: %d%n",
                                contador++, nome, df.format(preco), quantidade);
                        encontrou = true;
                    }
                }
            }

            reader.close();

            if (!encontrou) {
                System.out.println("Nenhum produto com estoque baixo encontrado.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro nos dados do arquivo.");
        }
    }
}
