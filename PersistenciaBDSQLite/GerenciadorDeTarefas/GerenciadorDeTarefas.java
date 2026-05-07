//javac -cp ".;sqlite-jdbc-3.36.0.3.jar" CadastroDeLivros.java
//java -cp ".;sqlite-jdbc-3.36.0.3.jar" CadastroDeLivros

import java.sql.Connection;          // Representa a conexão com o banco
import java.sql.DriverManager;       // Cria conexões com o banco
import java.sql.PreparedStatement;   // Permite executar SQL com parâmetros
import java.sql.ResultSet;           // Guarda os resultados de um SELECT
import java.sql.SQLException;        // Exceção para erros de banco
import java.sql.Statement;           // Executa comandos SQL simples
import java.util.Scanner;            // Lê dados digitados pelo usuário

// Classe responsável pelo acesso ao banco de tarefas
class BancoTarefas {

    // URL de conexão com o SQLite
    private static final String URL = "jdbc:sqlite:tarefas.db";

    // Cria a tabela, se ainda não existir
    public void criarTabela() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tarefas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    descricao TEXT NOT NULL,
                    concluida INTEGER NOT NULL DEFAULT 0
                );
                """;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }

    // Insere uma nova tarefa
    public void adicionarTarefa(String descricao) {
        String sql = "INSERT INTO tarefas (descricao, concluida) VALUES (?, 0)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, descricao);
            pstmt.executeUpdate();

            System.out.println("Tarefa cadastrada com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar tarefa: " + e.getMessage());
        }
    }

    // Lista todas as tarefas
    public void listarTarefas() {
        String sql = "SELECT id, descricao, concluida FROM tarefas ORDER BY id";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean encontrou = false;

            System.out.println("\n=== TAREFAS ===");

            while (rs.next()) {
                encontrou = true;

                int id = rs.getInt("id");
                String descricao = rs.getString("descricao");
                int concluida = rs.getInt("concluida");

                // Traduz 0 e 1 para um texto mais amigável
                String status;
                if (concluida == 1) {
                    status = "Concluída";
                } else {
                    status = "Pendente";
                }

                System.out.println(id + " - " + descricao + " [" + status + "]");
            }

            if (!encontrou) {
                System.out.println("Nenhuma tarefa cadastrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
        }
    }

    // Marca uma tarefa como concluída
    public void concluirTarefa(int id) {
        String sql = "UPDATE tarefas SET concluida = 1 WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            // executeUpdate retorna quantas linhas foram alteradas
            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Tarefa concluída com sucesso.");
            } else {
                System.out.println("Nenhuma tarefa encontrada com esse id.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao concluir tarefa: " + e.getMessage());
        }
    }

    // Exclui uma tarefa pelo id
    public void excluirTarefa(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Tarefa excluída com sucesso.");
            } else {
                System.out.println("Nenhuma tarefa encontrada com esse id.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir tarefa: " + e.getMessage());
        }
    }
}

// Classe principal
public class GerenciadorDeTarefas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BancoTarefas banco = new BancoTarefas();

        // Garante a existência da tabela
        banco.criarTabela();

        int opcao;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Adicionar tarefa");
            System.out.println("2 - Listar tarefas");
            System.out.println("3 - Concluir tarefa");
            System.out.println("4 - Excluir tarefa");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Digite a descrição da tarefa: ");
                    String descricao = scanner.nextLine();
                    banco.adicionarTarefa(descricao);
                    break;

                case 2:
                    banco.listarTarefas();
                    break;

                case 3:
                    System.out.print("Digite o id da tarefa a concluir: ");
                    int idConcluir = Integer.parseInt(scanner.nextLine());
                    banco.concluirTarefa(idConcluir);
                    break;

                case 4:
                    System.out.print("Digite o id da tarefa a excluir: ");
                    int idExcluir = Integer.parseInt(scanner.nextLine());
                    banco.excluirTarefa(idExcluir);
                    break;

                case 5:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 5);

        scanner.close();
    }
}
