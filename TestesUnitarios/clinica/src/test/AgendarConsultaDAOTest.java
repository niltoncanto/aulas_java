package com.mack.clinica.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mack.clinica.util.DatabaseConnection;

public class AgendarConsultaDAOTest {

    private AgendarConsultaDAO dao;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        // Mock dos objetos de banco de dados
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock do método estático DatabaseConnection.getConnection()
        mockStatic(DatabaseConnection.class);
        when(DatabaseConnection.getConnection(anyString())).thenReturn(mockConnection);

        // Configuração inicial do DAO
        dao = new AgendarConsultaDAO("caminho/ficticio");
    }

    @Test
    public void testListarMedicos() throws Exception {
        // Configurar o comportamento do ResultSet
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Simular os dados retornados pelo ResultSet
        when(mockResultSet.next()).thenReturn(true, true, false); // 2 registros
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("nome")).thenReturn("Dr. João", "Dr. Maria");

        // Executar o método
        List<Usuario> medicos = dao.listarMedicos();

        // Verificar os resultados
        assertNotNull(medicos);
        assertEquals(2, medicos.size());
        assertEquals("Dr. João", medicos.get(0).getNome());
        assertEquals("Dr. Maria", medicos.get(1).getNome());

        // Verificar se os métodos foram chamados corretamente
        verify(mockConnection).prepareStatement("SELECT id, nome FROM usuarios WHERE tipo = 'medico'");
        verify(mockStatement).executeQuery();
        verify(mockResultSet, times(2)).next();
    }
}
