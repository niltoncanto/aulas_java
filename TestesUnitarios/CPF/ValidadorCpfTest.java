// ValidadorCpfTest.java
package CPF;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidadorCpfTest {

    @Test
    public void testCpfValido() {
        assertTrue(ValidadorCpf.validarCpf("52998224725"));
    }

    @Test
    public void testCpfInvalidoDigitos() {
        assertFalse(ValidadorCpf.validarCpf("52998224700"));
    }

    @Test
    public void testCpfComCaracteresInvalidos() {
        assertFalse(ValidadorCpf.validarCpf("abc98224700"));
    }

    @Test
    public void testCpfTamanhoInvalido() {
        assertFalse(ValidadorCpf.validarCpf("123456789"));
    }

    @Test
    public void testCpfTodosIguais() {
        assertFalse(ValidadorCpf.validarCpf("11111111111"));
    }
}
