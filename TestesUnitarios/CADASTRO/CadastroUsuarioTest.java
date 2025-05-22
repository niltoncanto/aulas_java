// CadastroUsuarioTest.java

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class CadastroUsuarioTest {

    CadastroUsuario usuarioValido = new CadastroUsuario("João Silva", "123.456.789-10", "01/01/1990", "Rua A, 123", "(11) 98765-4321", "senha123");

    @Test
    public void testValidarNomeValido() {
        assertDoesNotThrow(() -> usuarioValido.validarNome());
    }

    @Test
    public void testValidarNomeInvalido() {
        CadastroUsuario u = new CadastroUsuario("João", "123.456.789-10", "01/01/1990", "Rua A", "(11) 98765-4321", "senha123");
        assertThrows(IllegalArgumentException.class, u::validarNome);
    }

    @Test
    public void testValidarCpfInvalido() {
        CadastroUsuario u = new CadastroUsuario("João Silva", "12345678910", "01/01/1990", "Rua A", "(11) 98765-4321", "senha123");
        assertThrows(IllegalArgumentException.class, u::validarCpf);
    }

    @Test
    public void testValidarNascimentoFuturo() {
        CadastroUsuario u = new CadastroUsuario("João Silva", "123.456.789-10", "01/01/3000", "Rua A", "(11) 98765-4321", "senha123");
        assertThrows(IllegalArgumentException.class, u::validarNascimento);
    }

    @Test
    public void testValidarNascimentoFormatoInvalido() {
        CadastroUsuario u = new CadastroUsuario("João Silva", "123.456.789-10", "1990-01-01", "Rua A", "(11) 98765-4321", "senha123");
        assertThrows(IllegalArgumentException.class, u::validarNascimento);
    }

    @Test
    public void testValidarEnderecoInvalido() {
        CadastroUsuario u = new CadastroUsuario("João Silva", "123.456.789-10", "01/01/1990", "", "(11) 98765-4321", "senha123");
        assertThrows(IllegalArgumentException.class, u::validarEndereco);
    }

    @Test
    public void testValidarTelefoneInvalido() {
        CadastroUsuario u = new CadastroUsuario("João Silva", "123.456.789-10", "01/01/1990", "Rua A", "11987654321", "senha123");
        assertThrows(IllegalArgumentException.class, u::validarTelefone);
    }

    @Test
    public void testValidarSenhaInvalida() {
        CadastroUsuario u = new CadastroUsuario("João Silva", "123.456.789-10", "01/01/1990", "Rua A", "(11) 98765-4321", "1234567");
        assertThrows(IllegalArgumentException.class, u::validarSenha);
    }

    @Test
    public void testGravarInfoNaoLancaExcecao() {
        assertDoesNotThrow(() -> usuarioValido.gravarInfo());
    }
}
