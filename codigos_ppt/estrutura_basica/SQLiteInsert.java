

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SQLiteInsert {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:meubanco.db"; // Caminho do banco de dados SQLite

        // Atualizar o nome do usuário com idade 25
        String sql = "INSERT INTO usuarios (nome, idade) VALUES (?, ?)";
        try (Connection conn = getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Substitui os placeholders (?) pelos valores reais
            pstmt.setString(1, "Maria");
            pstmt.setInt(2, 25);
            pstmt.executeUpdate();linhasAf
            System.out.println("Registro inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir registro: " + e.getMessage());
        }
    }
}

