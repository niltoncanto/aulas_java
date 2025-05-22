// NomeUtilsTest.java
package NOME;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NomeUtilsTest {

    @Test
    public void testNomeSimples() {
        assertEquals("Maria", NomeUtils.normalizarNome("maria"));
    }

    @Test
    public void testNomeComEspacosExtras() {
        assertEquals("João Da Silva", NomeUtils.normalizarNome("   jOÃO   da  silva "));
    }

    @Test
    public void testNomeComTudoMaiusculo() {
        assertEquals("Ana Beatriz Costa", NomeUtils.normalizarNome("ANA BEATRIZ COSTA"));
    }

    @Test
    public void testNomeVazio() {
        assertEquals("", NomeUtils.normalizarNome("   "));
    }
}
