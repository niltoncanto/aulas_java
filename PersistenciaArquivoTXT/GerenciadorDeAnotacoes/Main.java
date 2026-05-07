import java.io.BufferedReader;   // Permite ler texto do arquivo linha por linha
import java.io.BufferedWriter;   // Permite escrever texto no arquivo
import java.io.File;             // Representa um arquivo no sistema
import java.io.FileReader;       // Abre o arquivo para leitura
import java.io.FileWriter;       // Abre o arquivo para escrita
import java.io.IOException;      // Excecao usada em operacoes de entrada e saida
import java.util.Scanner;        // Le dados digitados pelo usuario

// Classe responsavel pelas operacoes com o arquivo de anotacoes
class GerenciadorArquivo {
    // Nome do arquivo que sera usado pelo programa
    private final String nomeArquivo;

    // Construtor da classe
    public GerenciadorArquivo() {
        // Define o nome fixo do arquivo
        this.nomeArquivo = "anotacoes.txt";
    }

    // Grava uma anotacao no final do arquivo
    public void escreverAnotacao(String texto) {
        // Cria uma referencia para o arquivo
        File arquivo = new File(nomeArquivo);

        try {
            // Abre o arquivo em modo de adicao
            FileWriter fw = new FileWriter(arquivo, true);

            // Cria um escritor mais eficiente
            BufferedWriter bw = new BufferedWriter(fw);

            // Escreve o texto digitado
            bw.write(texto);

            // Pula para a proxima linha
            bw.newLine();

            // Fecha o arquivo
            bw.close();

            // Informa sucesso ao usuario
            System.out.println("Anotacao salva com sucesso.");
        } catch (IOException e) {
            // Informa erro se a escrita falhar
            System.out.println("Erro ao escrever no arquivo.");
        }
    }

    // Le e mostra todo o conteudo do arquivo
    public void lerArquivo() {
        // Cria uma referencia para o arquivo
        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo existe
        if (!arquivo.exists()) {
            System.out.println("O arquivo ainda nao existe.");
            return;
        }

        try {
            // Abre o arquivo para leitura
            FileReader fr = new FileReader(arquivo);

            // Cria um leitor mais eficiente
            BufferedReader br = new BufferedReader(fr);

            // Guarda cada linha lida do arquivo
            String linha;

            // Indica se alguma linha foi encontrada
            boolean vazio = true;

            // Le linha por linha ate o final do arquivo
            while ((linha = br.readLine()) != null) {
                // Na primeira linha lida, exibe um titulo
                if (vazio) {
                    System.out.println("\nConteudo do arquivo:");
                    vazio = false;
                }

                // Exibe a linha atual
                System.out.println(linha);
            }

            // Se nenhuma linha foi lida, o arquivo esta vazio
            if (vazio) {
                System.out.println("O arquivo esta vazio.");
            }

            // Fecha o arquivo
            br.close();
        } catch (IOException e) {
            // Informa erro se a leitura falhar
            System.out.println("Erro ao ler o arquivo.");
        }
    }

    // Exclui o arquivo de anotacoes
    public void excluirArquivo() {
        // Cria uma referencia para o arquivo
        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo existe antes de excluir
        if (!arquivo.exists()) {
            System.out.println("O arquivo nao existe.");
            return;
        }

        // Tenta excluir o arquivo
        if (arquivo.delete()) {
            // Mensagem de sucesso
            System.out.println("Arquivo excluido com sucesso.");
        } else {
            // Mensagem de falha
            System.out.println("Nao foi possivel excluir o arquivo.");
        }
    }
}

// Classe principal do programa
public class Main {

    // Metodo principal onde a execucao comeca
    public static void main(String[] args) {
        // Cria o leitor de teclado
        Scanner scanner = new Scanner(System.in);

        // Cria o objeto que manipula o arquivo
        GerenciadorArquivo gerenciador = new GerenciadorArquivo();

        // Guarda a opcao escolhida pelo usuario
        int opcao;

        // Repete o menu ate o usuario escolher sair
        do {
            // Exibe o menu na tela
            exibirMenu();

            // Pede a opcao ao usuario
            System.out.print("Escolha uma opcao: ");

            // Valida se a entrada eh um numero inteiro
            while (!scanner.hasNextInt()) {
                // Informa erro de digitacao
                System.out.println("Opcao invalida. Digite um numero de 1 a 4.");

                // Descarta a entrada invalida
                scanner.nextLine();

                // Pede novamente a opcao
                System.out.print("Escolha uma opcao: ");
            }

            // Le a opcao digitada
            opcao = scanner.nextInt();

            // Consome o Enter pendente do teclado
            scanner.nextLine();

            // Executa a acao correspondente a opcao escolhida
            switch (opcao) {
                case 1:
                    // Pede o texto da anotacao
                    System.out.print("Digite a anotacao: ");

                    // Le a anotacao inteira
                    String texto = scanner.nextLine();

                    // Salva a anotacao no arquivo
                    gerenciador.escreverAnotacao(texto);
                    break;

                case 2:
                    // Le e exibe o arquivo
                    gerenciador.lerArquivo();
                    break;

                case 3:
                    // Exclui o arquivo
                    gerenciador.excluirArquivo();
                    break;

                case 4:
                    // Encerra o programa
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    // Trata numeros fora do intervalo esperado
                    System.out.println("Opcao invalida. Escolha entre 1 e 4.");
            }

            // Imprime uma linha em branco para organizar a saida
            System.out.println();

        } while (opcao != 4);

        // Fecha o Scanner ao final do programa
        scanner.close();
    }

    // Metodo que exibe o menu de opcoes
    private static void exibirMenu() {
        System.out.println("====== GERENCIADOR DE ANOTACOES ======");
        System.out.println("1 - Escrever anotacao");
        System.out.println("2 - Ler arquivo");
        System.out.println("3 - Excluir arquivo");
        System.out.println("4 - Sair");
    }
}
