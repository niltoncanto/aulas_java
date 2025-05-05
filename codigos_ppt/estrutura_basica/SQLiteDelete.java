

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SQLiteDelete {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:meubanco.db"; // Caminho do banco de dados SQLite

        // SQL para deletar um registro com idade = 25
        String sql = "DELETE FROM usuarios WHERE idade = ?";

        try (Connection conexao = DriverManager.getConnection(url);
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            // Substitui o placeholder (?) pelo valor real
            pstmt.setInt(1, 25);

            // Executa o comando de exclusão
            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println("Linhas deletadas: " + linhasAfetadas);

        } catch (Exception e) {
            System.out.println("Erro ao deletar registro no banco de dados: " + e.getMessage());
        }
    }
}


