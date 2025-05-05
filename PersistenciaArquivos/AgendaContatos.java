import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AgendaContatos {

    // Caminho do arquivo onde os contatos serão armazenados
    private static final String NOME_ARQUIVO = "contatos.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            // Exibe o menu de opções
            System.out.println("\n=== AGENDA DE CONTATOS ===");
            System.out.println("1 - Adicionar contato");
            System.out.println("2 - Listar contatos");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            // Verifica qual opção foi escolhida e chama o método correspondente
            switch (opcao) {
                case 1:
                    adicionarContato(scanner);
                    break;
                case 2:
                    listarContatos();
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close(); // Fecha o scanner ao final
    }

    // Método para adicionar um novo contato ao arquivo
    private static void adicionarContato(Scanner scanner) {
        try {
            // Solicita os dados ao usuário
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            // Cria o FileWriter no modo de "append" para não sobrescrever o arquivo
            BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO, true));

            // Escreve os dados no formato Nome;Telefone;Email
            writer.write(nome + ";" + telefone + ";" + email);
            writer.newLine(); // Adiciona uma nova linha após cada contato
            writer.close(); // Fecha o writer

            System.out.println("Contato salvo com sucesso!");

        } catch (IOException e) {
            // Captura e exibe qualquer erro de entrada/saída
            System.out.println("Erro ao salvar contato: " + e.getMessage());
        }
    }

    // Método para listar os contatos salvos no arquivo
    private static void listarContatos() {
        try {
            File arquivo = new File(NOME_ARQUIVO);

            // Verifica se o arquivo existe
            if (!arquivo.exists()) {
                System.out.println("Nenhum contato encontrado.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String linha;
            int contador = 1;

            System.out.println("\n=== CONTATOS CADASTRADOS ===");

            // Lê linha por linha do arquivo e exibe formatado
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    System.out.printf("%d - Nome: %s | Telefone: %s | Email: %s%n",
                        contador++, dados[0], dados[1], dados[2]);
                }
            }

            reader.close(); // Fecha o reader

        } catch (IOException e) {
            // Captura e exibe erro de leitura
            System.out.println("Erro ao ler contatos: " + e.getMessage());
        }
    }
}
