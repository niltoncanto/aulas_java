import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Contato {
    private String nome;
    private String telefone;
    private String email;

    public Contato(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String toFileString() {
        return nome + ";" + telefone + ";" + email;
    }

    public static Contato fromFileString(String linha) {
        String[] partes = linha.split(";");
        if (partes.length != 3) {
            return null;
        }

        String nome = partes[0];
        String telefone = partes[1];
        String email = partes[2];

        return new Contato(nome, telefone, email);
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " | Telefone: " + telefone + " | Email: " + email;
    }
}

class GerenciadorContatos {
    private String nomeArquivo;

    public GerenciadorContatos() {
        this.nomeArquivo = "contatos.txt";
    }

    public void adicionarContato(Contato contato) {
        Path caminho = Paths.get(nomeArquivo);

        try {
            Files.write(
                caminho,
                (contato.toFileString() + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            System.out.println("Contato salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar contato: " + e.getMessage());
        }
    }

    public List<Contato> listarContatos() {
        List<Contato> contatos = new ArrayList<>();
        Path caminho = Paths.get(nomeArquivo);

        if (!Files.exists(caminho)) {
            return contatos;
        }

        try {
            List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8);

            for (String linha : linhas) {
                if (!linha.trim().isEmpty()) {
                    Contato contato = Contato.fromFileString(linha);
                    if (contato != null) {
                        contatos.add(contato);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler contatos: " + e.getMessage());
        }

        return contatos;
    }
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GerenciadorContatos gerenciador = new GerenciadorContatos();
        int opcao;

        do {
            exibirMenu();
            opcao = lerOpcao();

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
                    System.out.println("Opção inválida.");
            }

            System.out.println();

        } while (opcao != 3);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("===== GERENCIADOR DE CONTATOS =====");
        System.out.println("1 - Adicionar contato");
        System.out.println("2 - Listar contatos");
        System.out.println("3 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        while (!scanner.hasNextInt()) {
            System.out.println("Digite um número válido.");
            scanner.nextLine();
            System.out.print("Escolha uma opção: ");
        }

        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    private static void adicionarContato(GerenciadorContatos gerenciador) {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        Contato contato = new Contato(nome, telefone, email);
        gerenciador.adicionarContato(contato);
    }

    private static void listarContatos(GerenciadorContatos gerenciador) {
        List<Contato> contatos = gerenciador.listarContatos();

        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
            return;
        }

        System.out.println("===== CONTATOS CADASTRADOS =====");
        for (Contato contato : contatos) {
            System.out.println(contato);
        }
    }
}