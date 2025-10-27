import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConversorDeTextoTest {

    private ConversorDeTexto conversor;

    @BeforeEach
    void setup() {
        // Instancia a classe sob teste antes de cada caso
        conversor = new ConversorDeTexto();
    }

    // -------------------------
    // capitalizarPalavras
    // -------------------------

    @Test
    void capitalizarPalavras_deveCapitalizarCadaPalavra() {
        // Arrange
        String entrada = "java é divertido";
        // Act
        String resultado = conversor.capitalizarPalavras(entrada);
        // Assert
        assertEquals("Java É Divertido", resultado);
    }

    @Test
    void capitalizarPalavras_deveLancarExcecaoQuandoNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> conversor.capitalizarPalavras(null));
        assertThrows(IllegalArgumentException.class, () -> conversor.capitalizarPalavras("   "));
    }

    // -------------------------
    // inverterPalavras
    // -------------------------

    @Test
    void inverterPalavras_deveInverterAOrdemDasPalavras() {
        // Arrange
        String entrada = "ola mundo bonito";
        // Act
        String resultado = conversor.inverterPalavras(entrada);
        // Assert
        assertEquals("bonito mundo ola", resultado);
    }

    @Test
    void inverterPalavras_deveLancarExcecaoQuandoNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> conversor.inverterPalavras(null));
        assertThrows(IllegalArgumentException.class, () -> conversor.inverterPalavras("   "));
    }

    // -------------------------
    // contarVogais
    // -------------------------

    @Test
    void contarVogais_deveContarCorretamenteMaiusculasEMinusculas() {
        // Arrange
        String entrada = "BanAna";
        // Act
        int qtd = conversor.contarVogais(entrada);
        // Assert
        assertEquals(3, qtd);
    }

    @Test
    void contarVogais_deveRetornarZeroQuandoNaoHouverVogais() {
        assertEquals(0, conversor.contarVogais("rhythms"));
    }

    @Test
    void contarVogais_deveLancarExcecaoQuandoNulo() {
        assertThrows(IllegalArgumentException.class, () -> conversor.contarVogais(null));
    }

    // -------------------------
    // ehPalindromo
    // -------------------------

    @Test
    void ehPalindromo_deveRetornarTrueParaFrasePalindromaComEspacos() {
        // Clássico em PT-BR (ignorando espaços e caixa)
        String entrada = "Socorram me subi no onibus em Marrocos";
        assertTrue(conversor.ehPalindromo(entrada));
    }

    @Test
    void ehPalindromo_deveRetornarFalseParaNaoPalindromoOuVazio() {
        assertFalse(conversor.ehPalindromo("java"));
        assertFalse(conversor.ehPalindromo(""));
        assertFalse(conversor.ehPalindromo("   "));
        assertFalse(conversor.ehPalindromo(null));
    }

    // -------------------------
    // removerPalavrasCurtas
    // -------------------------

    @Test
    void removerPalavrasCurtas_deveRemoverMenoresQueTamanhoMinimo() {
        // Arrange
        String entrada = "hoje é um lindo dia";
        // Act
        String resultado = conversor.removerPalavrasCurtas(entrada, 3);
        // Assert
        assertEquals("hoje lindo dia", resultado);
    }

    @Test
    void removerPalavrasCurtas_deveLancarExcecaoQuandoTextoNulo() {
        assertThrows(IllegalArgumentException.class, () -> conversor.removerPalavrasCurtas(null, 3));
    }

    // -------------------------
    // substituirPalavra **
    // -------------------------

    @Test
    void substituirPalavra_deveSubstituirSomentePalavraExata() {
        // Arrange
        String entrada = "java é legal; javascript também";
        // Act
        String resultado = conversor.substituirPalavra(entrada, "java", "python");
        // Assert: "java" -> "python", mas "javascript" permanece
        assertEquals("python é legal; javascript também", resultado);
    }

    @Test
    void substituirPalavra_deveLancarExcecaoQuandoAlgumParametroNulo() {
        assertThrows(IllegalArgumentException.class, () -> conversor.substituirPalavra(null, "a", "b"));
        assertThrows(IllegalArgumentException.class, () -> conversor.substituirPalavra("x", null, "b"));
        assertThrows(IllegalArgumentException.class, () -> conversor.substituirPalavra("x", "a", null));
    }

    // -------------------------
    // contarPalavras
    // -------------------------

    @Test
    void contarPalavras_deveContarCorretamente() {
        assertEquals(4, conversor.contarPalavras("Java é muito legal"));
        assertEquals(1, conversor.contarPalavras("Unico"));
        assertEquals(0, conversor.contarPalavras("   "));
        assertEquals(0, conversor.contarPalavras(""));
    }

    // -------------------------
    // inverterLetrasPorPalavra
    // -------------------------

    @Test
    void inverterLetrasPorPalavra_deveInverterCadaPalavraMantendoOrdem() {
        // Arrange
        String entrada = "Java é legal";
        // Act
        String resultado = conversor.inverterLetrasPorPalavra(entrada);
        // Assert
        assertEquals("avaJ é lageL", resultado);
    }

    @Test
    void inverterLetrasPorPalavra_deveLancarExcecaoQuandoNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> conversor.inverterLetrasPorPalavra(null));
        assertThrows(IllegalArgumentException.class, () -> conversor.inverterLetrasPorPalavra("   "));
    }
}