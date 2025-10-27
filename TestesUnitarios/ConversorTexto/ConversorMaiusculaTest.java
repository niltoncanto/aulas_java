import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConversorMaiusculaTest {

    private ConversorMaiuscula conversor;

    @BeforeEach
    void setup() {
        // Executado antes de cada teste
        conversor = new ConversorMaiuscula();
    }

    @Test
    void ConverterTextoParaMaiusculas() {
        String entrada = "Java é incrível";
        String resultado = conversor.converter(entrada);
        assertEquals("JAVA É INCRÍVEL", resultado, "O texto não foi convertido para maiúsculas");
    }

    @Test
    void deveLancarExcecaoQuandoTextoForNulo() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> conversor.converter(null),
            "Texto não pode ser nulo");
    }
}

