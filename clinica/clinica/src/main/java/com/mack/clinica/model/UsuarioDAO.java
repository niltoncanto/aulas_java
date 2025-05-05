package com.mack.clinica.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mack.clinica.util.DatabaseConnection;

public class UsuarioDAO {

    /**
     * Consulta o usuário pelo email e senha no banco.
     * @param email Email do usuário.
     * @param senha Senha do usuário.
     * @param realPathBase Caminho real da aplicação para localizar o banco.
     * @return Objeto Usuario encontrado ou null se não encontrado.
     */
    public static Usuario buscarUsuario(String email, String senha, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "SELECT id, nome, email,cpf,celular, tipo FROM usuarios WHERE email = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Se encontrou o usuário, cria um objeto Usuario
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setCPF(rs.getString("cpf"));
                usuario.setCelular(rs.getString("celular"));
                usuario.setTipo(rs.getString("tipo"));
                return usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário no banco de dados.", e);
        }
        return null;
    }
    public static Usuario buscarPorEmail(String email, String tipo, String realPathBase) {
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            String sql = "SELECT id, nome, email, cpf, celular, tipo, senha FROM usuarios WHERE email = ? AND tipo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, tipo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Se encontrou o usuário, cria um objeto Usuario
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setCPF(rs.getString("cpf"));
                usuario.setCelular(rs.getString("celular"));
                usuario.setTipo(rs.getString("tipo"));
                usuario.setSenha(rs.getString("senha"));
                return usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário no banco de dados.", e);
        }
        return null;
    }

    public Boolean atualizarUsuario(Usuario usuario, String realPathBase) {
        // Tentativa de conexão com o banco de dados
        try (Connection conn = DatabaseConnection.getConnection(realPathBase)) {
            // Cria a instrução SQL de atualização
            String sql = "UPDATE usuarios SET nome = ?, email = ?, cpf = ?, celular = ?, senha = ? WHERE id = ?";
            System.out.println("SQL: " + sql);
            PreparedStatement stmt = conn.prepareStatement(sql);
    
            // Define os valores nos parâmetros da instrução SQL
            stmt.setString(1, usuario.getNome());    // 1º parâmetro: novo nome
            stmt.setString(2, usuario.getEmail());   // 2º parâmetro: novo email
            stmt.setString(3, usuario.getCPF());     // 3º parâmetro: novo CPF
            stmt.setString(4, usuario.getCelular()); // 4º parâmetro: novo celular
            stmt.setString(5, usuario.getSenha());   // 5º parâmetro: nova senha
            stmt.setInt(6, usuario.getId());         // 6º parâmetro: ID do usuário para identificar qual atualizar
            System.out.println("SQL: " + sql);
            // Executa o update
            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("linhasAfetadas: " + linhasAfetadas);
            // Retorna true se atualizou alguma linha
            return linhasAfetadas > 0;
    
        } catch (SQLException e) {
            // Em caso de erro, exibe o erro e relança uma exceção de tempo de execução
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar usuário no banco de dados.", e);
        }
    }
    
}
