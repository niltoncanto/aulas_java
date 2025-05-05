
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLiteConexao {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:meubanco.db"; // Caminho do banco de dados SQLite

        try (Connection conexao = DriverManager.getConnection(url);
            Statement stmt = conexao.createStatement()) {

            // Criação da tabela
            String criarTabela = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "nome TEXT, "
                    + "idade INTEGER)";
            stmt.execute(criarTabela);

            // Inserção de dados
            String inserirDados = "INSERT INTO usuarios (nome, idade) VALUES ('João', 25)";
            stmt.execute(inserirDados);

            System.out.println("Tabela criada e dados inseridos com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao conectar ou manipular o banco de dados: " + e.getMessage());
        }
    }
}