// GerenciarBD.java
// Classe de acesso a dados (DAO simples) para SQLite.
// Fornece métodos para criar tabela, inserir, buscar e atualizar produtos.
// Todos os métodos são comentados linha a linha para fins didáticos.
 
import java.sql.Connection;          // Conexão com o banco
import java.sql.DriverManager;       // Cria conexões JDBC
import java.sql.PreparedStatement;   // Comandos SQL parametrizados
import java.sql.ResultSet;           // Resultado de SELECT
import java.sql.SQLException;        // Exceções de banco
import java.sql.Statement;           // Constantes e operações gerais
import java.util.ArrayList;          // Lista concreta
import java.util.List;               // Interface de lista
 
public class GerenciarBD {
 
    // URL JDBC do SQLite. Ex.: "jdbc:sqlite:loja.db"
    private final String dbUrl;
 
    // Construtor recebe caminho do arquivo do banco (ex.: "loja.db")
    public GerenciarBD(String dbFilePath) {
        // H2 (arquivo em ./loja)
        try { Class.forName("org.h2.Driver"); } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver H2 ausente no classpath.", e);
        }
        this.dbUrl = "jdbc:h2:file:./loja;AUTO_SERVER=TRUE"; // ignora dbFilePath aqui
    }
 
    // Abre e retorna uma conexão com o banco de dados.
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl); // Abre conexão via         DriverManager
    }
 
    // Cria a tabela 'produtos' se não existir.
    public void criarTabelaSeNaoExistir() throws SQLException {
        /*final String sql = "CREATE TABLE IF NOT EXISTS produtos (" +
                           "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                           "nome TEXT NOT NULL," +
                           "preco REAL NOT NULL," +
                           "quantidade INTEGER NOT NULL DEFAULT 0" +
                           ")"; */


        final String sql = "CREATE TABLE produtos (" +
                            "id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                            "nome VARCHAR(255) NOT NULL," +
                            "preco DECIMAL(10,2) NOT NULL," +
                            "quantidade INT NOT NULL DEFAULT 0" +
                            ")";


        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute(); // Executa DDL
        }
    }
 
    // Insere um produto e retorna o id gerado.
    public long inserirProduto(String nome, double preco, int quantidade) throws SQLException {
        final String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nome);      // Param 1: nome
            ps.setDouble(2, preco);     // Param 2: preco
            ps.setInt(3, quantidade);   // Param 3: quantidade
            ps.executeUpdate();         // Executa INSERT
            try (ResultSet rs = ps.getGeneratedKeys()) { // Recupera chave gerada
                if (rs.next()) {
                    return rs.getLong(1); // Primeiro valor é o id
                }
            }
        }
        return -1L; // Caso raro: não retornou id
    }
 
    // Retorna todos os produtos.
    public List<ProdutoDTO> listarTodos() throws SQLException {
        final String sql = "SELECT id, nome, preco, quantidade FROM produtos ORDER BY id";
        List<ProdutoDTO> lista = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { // Percorre linhas
                ProdutoDTO p = new ProdutoDTO(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                );
                lista.add(p);
            }
        }
        return lista;
    }
 
    // Retorna produtos com estoque <= limite informado.
    public List<ProdutoDTO> listarPorEstoqueAte(int limite) throws SQLException {
        final String sql = "SELECT id, nome, preco, quantidade FROM produtos WHERE quantidade <= ? ORDER BY quantidade, id";
        List<ProdutoDTO> lista = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limite);             // Define limite
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdutoDTO p = new ProdutoDTO(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco"),
                            rs.getInt("quantidade")
                    );
                    lista.add(p);
                }
            }
        }
        return lista;
    }
 
    // Atualiza a quantidade de um produto pelo id. Retorna linhas afetadas (0 ou 1).
    public int atualizarQuantidade(int id, int novaQuantidade) throws SQLException {
        final String sql = "UPDATE produtos SET quantidade = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, novaQuantidade); // Nova quantidade
            ps.setInt(2, id);             // ID alvo
            return ps.executeUpdate();    // Executa UPDATE
        }
    }
 
    // (Opcional) Atualiza o preço de um produto pelo id.
    public int atualizarPreco(int id, double novoPreco) throws SQLException {
        final String sql = "UPDATE produtos SET preco = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, novoPreco);   // Novo preço
            ps.setInt(2, id);             // ID alvo
            return ps.executeUpdate();    // Executa UPDATE
        }
    }
 
    // DTO simples para transportar dados.
    public static class ProdutoDTO {
        public final int id;
        public final String nome;
        public final double preco;
        public final int quantidade;
 
        public ProdutoDTO(int id, String nome, double preco, int quantidade) {
            this.id = id;             // Campo id
            this.nome = nome;         // Campo nome
            this.preco = preco;       // Campo preco
            this.quantidade = quantidade; // Campo quantidade
        }
 
        @Override
        public String toString() {
            return id + ";" + nome + ";" + preco + ";" + quantidade;
        }
    }
}
