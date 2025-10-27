import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConversorDeTexto {
    /**
     * Retorna o texto com a primeira letra de cada palavra em maiúscula.
     * Exemplo: "java é divertido" → "Java É Divertido"
     */
    public String capitalizarPalavras(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Texto não pode ser nulo ou vazio");
        }

        String[] palavras = texto.trim().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (String p : palavras) {
            resultado.append(Character.toUpperCase(p.charAt(0)))
                     .append(p.substring(1).toLowerCase())
                     .append(" ");
        }

        return resultado.toString().trim();
    }

    /**
     * Inverte a ordem das palavras em uma frase.
     * Exemplo: "olá mundo" → "mundo olá"
     */
    public String inverterPalavras(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Texto não pode ser nulo ou vazio");
        }

        String[] palavras = texto.trim().split("\\s+");
        List<String> lista = new ArrayList<>(List.of(palavras));
        Collections.reverse(lista);
        return String.join(" ", lista);
    }

    /**
     * Conta quantas vogais existem em um texto.
     * Exemplo: "banana" → 3
     */
    public int contarVogais(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("Texto não pode ser nulo");
        }

        int contador = 0;
        String vogais = "aeiouAEIOU";

        for (char c : texto.toCharArray()) {
            if (vogais.indexOf(c) != -1) {
                contador++;
            }
        }
        return contador;
    }

    /****
     * Verifica se um texto é um palíndromo.
     * Exemplo: "arara" → true, "java" → false
     */
    public boolean ehPalindromo(String texto) {
        if (texto == null || texto.isBlank()) {
            return false;
        }

        String limpo = texto.replaceAll("\\s+", "").toLowerCase();
        String invertido = new StringBuilder(limpo).reverse().toString();
        return limpo.equals(invertido);
    }

    /**
     * Remove todas as palavras menores que o tamanho mínimo informado.
     * Exemplo: ("hoje é um lindo dia", 3) → "hoje lindo dia"
     */
    public String removerPalavrasCurtas(String texto, int tamanhoMinimo) {
        if (texto == null) {
            throw new IllegalArgumentException("Texto não pode ser nulo");
        }

        String[] palavras = texto.trim().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (String p : palavras) {
            if (p.length() >= tamanhoMinimo) {
                resultado.append(p).append(" ");
            }
        }

        return resultado.toString().trim();
    }

    /**
     * Substitui todas as ocorrências de uma palavra por outra.
     * Exemplo: ("java é legal", "java", "python") → "python é legal"
     */
    public String substituirPalavra(String texto, String alvo, String nova) {
        if (texto == null || alvo == null || nova == null) {
            throw new IllegalArgumentException("Parâmetros não podem ser nulos");
        }
        return texto.replaceAll("\\b" + alvo + "\\b", nova);
    }

    /**
     * Retorna o número total de palavras em um texto.
     * Exemplo: "Java é muito legal" → 4
     */
    public int contarPalavras(String texto) {
        if (texto == null || texto.isBlank()) {
            return 0;
        }

        String[] palavras = texto.trim().split("\\s+");
        return palavras.length;
    }

    /**
     * Inverte as letras de cada palavra, mantendo a ordem original.
     * Exemplo: "java é legal" → "avaj é lageL"
     */
    public String inverterLetrasPorPalavra(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Texto não pode ser nulo ou vazio");
        }

        String[] palavras = texto.trim().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (String p : palavras) {
            resultado.append(new StringBuilder(p).reverse().toString())
                     .append(" ");
        }

        return resultado.toString().trim();
    }
}
