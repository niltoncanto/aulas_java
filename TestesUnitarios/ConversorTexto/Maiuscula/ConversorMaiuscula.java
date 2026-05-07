public class ConversorMaiuscula {
    /**
     * Converte o texto recebido para letras maiúsculas.
     * Exemplo: "Java é legal" → "JAVA É LEGAL"
     */
    public String converter(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("Texto não pode ser nulo");
        }
        return texto.toUpperCase();
    }
}

