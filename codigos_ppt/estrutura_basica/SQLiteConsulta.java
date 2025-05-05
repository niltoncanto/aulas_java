

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteConsulta {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:meubanco.db"; // Caminho do banco de dados SQLite

        try (Connection conexao = DriverManager.getConnection(url);
            Statement stmt = conexao.createStatement()) {

            // Consulta de dados
            String consulta = "SELECT * FROM usuarios";
            ResultSet resultado = stmt.executeQuery(consulta);

            // Recupera e exibe os dados
            while (resultado.next()) {
                String nome = resultado.getString("nome");
                int idade = resultado.getInt("idade");
                System.out.println("Nome: " + nome + ", Idade: " + idade);
            }

        } catch (Exception e) {
            System.out.println("Erro ao conectar ou manipular o banco de dados: " + e.getMessage());
        }
    }
}



