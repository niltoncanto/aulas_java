import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class GerenciarBanco {
    private static final String URL = "jdbc:mysql://localhost:3306/nome_do_banco";
    private static final String USER = "seu_usuario";
    private static final String PASS = "sua_senha";

    public Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados", e);
        }
    }

    public void inserir(Restaurante restaurante) {
        String sql = "INSERT INTO restaurantes (nome, tipo, estrelasMichelin) VALUES (?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, restaurante.getNome());
            if (restaurante instanceof FastFood) {
                stmt.setString(2, "FastFood");
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setString(2, "FineDining");
                stmt.setInt(3, ((FineDining) restaurante).estrelasMichelin);
            }
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir restaurante", e);
        }
    }

    public void atualizar(Restaurante restaurante) {
        // Implementação similar ao método inserir, mas usando "UPDATE" em vez de "INSERT"
    }

    public void deletar(String nomeRestaurante) {
        String sql = "DELETE FROM restaurantes WHERE nome = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeRestaurante);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar restaurante", e);
        }
    }

    public Restaurante selecionar(String nomeRestaurante) {
        String sql = "SELECT nome, tipo, estrelasMichelin FROM restaurantes WHERE nome = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeRestaurante);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                if ("FastFood".equals(tipo)) {
                    return new FastFood(nome, TipoFastFood.HAMBURGUER); // Simplificação para o exemplo
                } else {
                    int estrelas = rs.getInt("estrelasMichelin");
                    return new FineDining(nome, estrelas);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao selecionar restaurante", e);
        }
    }
}

