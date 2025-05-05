import java.sql.*;

//classe data access objeto
public class SQLiteDAO {

    private static final String URL = "jdbc:sqlite:meubanco.db";

    // Método para estabelecer conexão com o banco de dados
    public Connection getConexao() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    // Método para inserir dados
    public void insert(String nome, int idade) {
        String sql = "INSERT INTO usuarios (nome, idade) VALUES (?, ?)";
        try (Connection conn = getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, idade);
            pstmt.executeUpdate();
            System.out.println("Registro inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir registro: " + e.getMessage());
        }
    }

    // Método para atualizar dados
    public void update(String novoNome, int idade) {
        String sql = "UPDATE usuarios SET nome = ? WHERE idade = ?";
        try (Connection conn = getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoNome);
            pstmt.setInt(2, idade);
            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println("Registros atualizados: " + linhasAfetadas);
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar registro: " + e.getMessage());
        }
    }

    // Método para deletar dados
    public void delete(int idade) {
        String sql = "DELETE FROM usuarios WHERE idade = ?";
        try (Connection conn = getConexao(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idade);
            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println("Registros deletados: " + linhasAfetadas);
        } catch (SQLException e) {
            System.out.println("Erro ao deletar registro: " + e.getMessage());
        }
    }

    // Método para selecionar e exibir dados
    public void select() {
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = getConexao(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                System.out.println("Nome: " + nome + ", Idade: " + idade);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar registros: " + e.getMessage());
        }
    }
}


public class Main {
    public static void main(String[] args) {
        SQLiteDAO dao = new SQLiteDAO();

        // Criando a tabela (caso ainda não exista)
        try (Connection conn = dao.getConexao();
             Statement stmt = conn.createStatement()) {
            String criarTabela = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "nome TEXT, "
                    + "idade INTEGER)";
            stmt.execute(criarTabela);
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }

        // Testando os métodos da SQLiteDAO
        System.out.println("=== Inserindo Dados ===");
        dao.insert("João", 25);
        dao.insert("Maria", 30);

        System.out.println("\n=== Selecionando Dados ===");
        dao.select();

        System.out.println("\n=== Atualizando Dados ===");
        dao.update("José", 25);

        System.out.println("\n=== Selecionando Dados Após Atualização ===");
        dao.select();

        System.out.println("\n=== Deletando Dados ===");
        dao.delete(30);

        System.out.println("\n=== Selecionando Dados Após Exclusão ===");
        dao.select();
    }
}

