import java.io.IOException; // Importa a exceção usada em operações de entrada e saída
import java.nio.charset.StandardCharsets; // Permite definir a codificação UTF-8
import java.nio.file.Files; // Classe utilitária para manipulação de arquivos
import java.nio.file.Path; // Representa o caminho de um arquivo
import java.nio.file.Paths; // Permite criar objetos Path
import java.nio.file.StandardOpenOption; // Define opções de abertura/escrita de arquivo
import java.util.List; // Interface usada para armazenar as linhas lidas do arquivo
import java.util.Scanner; // Classe usada para ler dados digitados pelo usuário

class GerenciadorArquivo {
    // Nome do arquivo que será manipulado pelo sistema.
    // final indica que o valor será atribuído uma única vez.
    private final String nomeArquivo;

    // Construtor da classe.
    // Define o nome do arquivo que será usado no programa.
    public GerenciadorArquivo() {
        this.nomeArquivo = "anotacoes.txt";
    }

    // Método responsável por gravar uma nova anotação no arquivo.
    public void escreverAnotacao(String texto) {
        // Cria um objeto Path apontando para o arquivo.
        Path caminho = Paths.get(nomeArquivo);

        try {
            // Escreve o texto no arquivo.
            // System.lineSeparator() adiciona uma quebra de linha ao final.
            // getBytes(StandardCharsets.UTF_8) converte a String em bytes usando UTF-8.
            Files.write(
                caminho,
                (texto + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, // Cria o arquivo caso ele não exista
                StandardOpenOption.APPEND  // Adiciona o conteúdo ao final, sem apagar o anterior
            );

            // Mensagem exibida se a escrita ocorrer com sucesso.
            System.out.println("Anotação salva com sucesso.");
        } catch (IOException e) {
            // Captura erro de escrita e informa o problema ao usuário.
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    // Método responsável por ler e exibir o conteúdo do arquivo.
    public void lerArquivo() {
        // Cria o caminho para o arquivo.
        Path caminho = Paths.get(nomeArquivo);

        // Verifica se o arquivo existe antes de tentar lê-lo.
        if (!Files.exists(caminho)) {
            System.out.println("O arquivo ainda não existe.");
            return; // Encerra o método caso o arquivo não exista
        }

        try {
            // Lê todas as linhas do arquivo e armazena em uma lista.
            List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8);

            // Verifica se o arquivo existe, mas está vazio.
            if (linhas.isEmpty()) {
                System.out.println("O arquivo está vazio.");
                return; // Encerra o método nesse caso
            }

            // Exibe um título antes de mostrar as linhas.
            System.out.println("\nConteúdo do arquivo:");

            // Percorre todas as linhas do arquivo e imprime cada uma.
            for (String linha : linhas) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            // Captura erro de leitura e informa ao usuário.
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    // Método responsável por excluir o arquivo.
    public void excluirArquivo() {
        // Cria o caminho para o arquivo.
        Path caminho = Paths.get(nomeArquivo);

        try {
            // Tenta excluir o arquivo.
            // Retorna true se conseguiu excluir.
            // Retorna false se o arquivo não existia.
            boolean removido = Files.deleteIfExists(caminho);

            // Verifica o resultado da exclusão.
            if (removido) {
                System.out.println("Arquivo excluído com sucesso.");
            } else {
                System.out.println("O arquivo não existe. Nada foi excluído.");
            }
        } catch (IOException e) {
            // Captura erro durante a exclusão.
            System.out.println("Erro ao excluir o arquivo: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Cria o objeto Scanner para ler entradas do teclado.
        Scanner scanner = new Scanner(System.in);

        // Cria o objeto responsável pelas operações com o arquivo.
        GerenciadorArquivo gerenciador = new GerenciadorArquivo();

        // Variável que armazenará a opção escolhida pelo usuário.
        int opcao;

        // Estrutura de repetição do menu.
        // O programa continua até o usuário escolher a opção 4.
        do {
            // Exibe o menu na tela.
            exibirMenu();
            System.out.print("Escolha uma opção: ");

            // Valida se o usuário digitou um número inteiro.
            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Digite um número de 1 a 4.");
                scanner.nextLine(); // Descarta a entrada inválida
                System.out.print("Escolha uma opção: ");
            }

            // Lê a opção digitada.
            opcao = scanner.nextInt();

            // Consome o Enter pendente deixado pelo nextInt().
            // Isso evita problemas na próxima leitura com nextLine().
            scanner.nextLine();

            // Executa uma ação com base na opção escolhida.
            switch (opcao) {
                case 1:
                    // Solicita a anotação ao usuário.
                    System.out.print("Digite a anotação: ");
                    String texto = scanner.nextLine();

                    // Chama o método para gravar a anotação no arquivo.
                    gerenciador.escreverAnotacao(texto);
                    break;

                case 2:
                    // Chama o método para ler e exibir o conteúdo do arquivo.
                    gerenciador.lerArquivo();
                    break;

                case 3:
                    // Chama o método para excluir o arquivo.
                    gerenciador.excluirArquivo();
                    break;

                case 4:
                    // Mensagem exibida antes de encerrar o programa.
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    // Caso o número digitado esteja fora do intervalo de 1 a 4.
                    System.out.println("Opção inválida. Escolha entre 1 e 4.");
            }

            // Linha em branco para melhorar a visualização no console.
            System.out.println();

        } while (opcao != 4);

        // Fecha o Scanner ao final do programa.
        scanner.close();
    }

    // Método auxiliar para exibir o menu do sistema.
    private static void exibirMenu() {
        System.out.println("====== GERENCIADOR DE ANOTAÇÕES ======");
        System.out.println("1 - Escrever anotação");
        System.out.println("2 - Ler arquivo");
        System.out.println("3 - Excluir arquivo");
        System.out.println("4 - Sair");
    }
}