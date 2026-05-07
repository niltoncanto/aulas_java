import java.io.BufferedReader;   // Permite ler texto do arquivo linha por linha
import java.io.BufferedWriter;   // Permite escrever texto no arquivo
import java.io.File;             // Representa um arquivo no sistema
import java.io.FileReader;       // Abre o arquivo para leitura
import java.io.FileWriter;       // Abre o arquivo para escrita
import java.io.IOException;      // Excecao usada em operacoes de entrada e saida
import java.util.ArrayList;      // Implementacao de lista usada para armazenar contatos
import java.util.List;           // Interface de lista usada no retorno dos contatos
import java.util.Scanner;        // Le dados digitados pelo usuario
// Classe responsavel pelas operacoes com o arquivo de contatos
class GerenciadorContatos {

    // Nome do arquivo que armazenara os contatos
    private final String nomeArquivo;

    // Construtor da classe
    public GerenciadorContatos() {
        this.nomeArquivo = "contatos.txt";
    }

    // Adiciona um contato ao arquivo
    public void adicionarContato(Contato contato) {
        // Cria uma referencia para o arquivo
        File arquivo = new File(nomeArquivo);

        try {
            // Abre o arquivo em modo de adicao
            FileWriter fw = new FileWriter(arquivo, true);

            // Cria um escritor mais eficiente
            BufferedWriter bw = new BufferedWriter(fw);

            // Escreve o contato convertido em texto
            bw.write(contato.toFileString());

            // Pula para a proxima linha
            bw.newLine();

            // Fecha o arquivo
            bw.close();

            // Informa sucesso ao usuario
            System.out.println("Contato salvo com sucesso.");
        } catch (IOException e) {
            // Informa erro em caso de falha
            System.out.println("Erro ao salvar contato.");
        }
    }

    // Le o arquivo e devolve todos os contatos em uma lista
    public List<Contato> listarContatos() {
        // Cria a lista que armazenara os contatos lidos
        List<Contato> contatos = new ArrayList<>();

        // Cria uma referencia para o arquivo
        File arquivo = new File(nomeArquivo);

        // Se o arquivo nao existir, retorna lista vazia
        if (!arquivo.exists()) {
            return contatos;
        }

        try {
            // Abre o arquivo para leitura
            FileReader fr = new FileReader(arquivo);

            // Cria um leitor mais eficiente
            BufferedReader br = new BufferedReader(fr);

            // Guarda cada linha lida do arquivo
            String linha;

            // Le linha por linha ate o final do arquivo
            while ((linha = br.readLine()) != null) {
                // Ignora linhas vazias
                if (!linha.trim().isEmpty()) {
                    // Converte a linha em objeto Contato
                    Contato contato = Contato.fromFileString(linha);

                    // Adiciona o contato na lista se a linha for valida
                    if (contato != null) {
                        contatos.add(contato);
                    }
                }
            }

            // Fecha o arquivo
            br.close();
        } catch (IOException e) {
            // Informa erro em caso de falha
            System.out.println("Erro ao ler contatos.");
        }

        // Retorna a lista de contatos lidos
        return contatos;
    }
}

// Classe que representa um contato
class Contato {

    // Nome do contato
    private String nome;

    // Telefone do contato
    private String telefone;

    // Email do contato
    private String email;

    // Construtor da classe
    public Contato(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // Retorna o nome do contato
    public String getNome() {
        return nome;
    }

    // Retorna o telefone do contato
    public String getTelefone() {
        return telefone;
    }

    // Retorna o email do contato
    public String getEmail() {
        return email;
    }

    // Converte o objeto em uma linha de texto para gravar no arquivo
    public String toFileString() {
        return nome + ";" + telefone + ";" + email;
    }

    // Cria um objeto Contato a partir de uma linha lida do arquivo
    public static Contato fromFileString(String linha) {
        // Divide a linha em partes usando ; como separador
        String[] partes = linha.split(";");

        // Valida se a linha possui os tres campos esperados
        if (partes.length != 3) {
            return null;
        }

        // Cria e retorna um novo objeto Contato
        return new Contato(partes[0], partes[1], partes[2]);
    }

    // Define como o contato sera mostrado no console
    @Override
    public String toString() {
        return "Nome: " + nome + " | Telefone: " + telefone + " | Email: " + email;
    }
}


// Classe principal do programa
public class Main {

    // Scanner compartilhado para ler entradas do teclado
    private static Scanner scanner = new Scanner(System.in);

    // Metodo principal do programa
    public static void main(String[] args) {
        // Cria o gerenciador de contatos
        GerenciadorContatos gerenciador = new GerenciadorContatos();

        // Guarda a opcao escolhida pelo usuario
        int opcao;

        // Repete o menu ate o usuario escolher sair
        do {
            // Exibe o menu
            exibirMenu();

            // Le a opcao do usuario
            opcao = lerOpcao();

            // Executa a acao correspondente
            switch (opcao) {
                case 1:
                    adicionarContato(gerenciador);
                    break;

                case 2:
                    listarContatos(gerenciador);
                    break;

                case 3:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }

            // Linha em branco para organizar a saida
            System.out.println();

        } while (opcao != 3);

        // Fecha o Scanner ao final do programa
        scanner.close();
    }

    // Exibe o menu principal
    private static void exibirMenu() {
        System.out.println("===== GERENCIADOR DE CONTATOS =====");
        System.out.println("1 - Adicionar contato");
        System.out.println("2 - Listar contatos");
        System.out.println("3 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    // Le e valida a opcao digitada pelo usuario
    private static int lerOpcao() {
        // Enquanto a entrada nao for inteira, repete a leitura
        while (!scanner.hasNextInt()) {
            System.out.println("Digite um numero valido.");
            scanner.nextLine();
            System.out.print("Escolha uma opcao: ");
        }

        // Le a opcao digitada
        int opcao = scanner.nextInt();

        // Consome o Enter pendente
        scanner.nextLine();

        // Retorna a opcao lida
        return opcao;
    }

    // Le os dados e adiciona um novo contato
    private static void adicionarContato(GerenciadorContatos gerenciador) {
        // Pede o nome do contato
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        // Pede o telefone do contato
        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();

        // Pede o email do contato
        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        // Cria o objeto contato com os dados informados
        Contato contato = new Contato(nome, telefone, email);

        // Salva o contato no arquivo
        gerenciador.adicionarContato(contato);
    }

    // Le os contatos do arquivo e mostra no console
    private static void listarContatos(GerenciadorContatos gerenciador) {
        // Recupera a lista de contatos do arquivo
        List<Contato> contatos = gerenciador.listarContatos();

        // Se a lista estiver vazia, informa ao usuario
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
            return;
        }

        // Exibe titulo da listagem
        System.out.println("===== CONTATOS CADASTRADOS =====");

        // Percorre a lista e exibe cada contato
        for (Contato contato : contatos) {
            System.out.println(contato);
        }
    }
}
