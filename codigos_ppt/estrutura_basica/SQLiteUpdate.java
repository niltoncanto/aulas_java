
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SQLiteUpdate {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:meubanco.db"; // Caminho do banco de dados SQLite

        // Atualizar o nome do usuário com idade 25
        String sql = "UPDATE usuarios SET nome = ? WHERE idade = ?";

        try (Connection conexao = DriverManager.getConnection(url);
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            // Substitui os placeholders (?) pelos valores reais
            pstmt.setString(1, "Maria");
            pstmt.setInt(2, 25);

            // Executa o comando de atualização
            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println("Linhas atualizadas: " + linhasAfetadas);

        } catch (Exception e) {
            System.out.println("Erro ao atualizar o banco de dados: " + e.getMessage());
        }
    }
}