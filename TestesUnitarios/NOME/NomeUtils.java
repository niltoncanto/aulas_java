// NomeUtils.java
package NOME;
public class NomeUtils {
    public static String normalizarNome(String nome) {
        if (nome.trim().isEmpty()) return "";
        String[] partes = nome.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : partes) {
            sb.append(Character.toUpperCase(p.charAt(0)))
              .append(p.substring(1))
              .append(" ");
        }
        return sb.toString().trim();
    }
}
