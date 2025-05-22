// ValidadorCpf.java
package CPF;
public class ValidadorCpf {
    public static boolean validarCpf(String cpf) {
        if (!cpf.matches("\\d{11}")) {
            return false;
        }

        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int dig1 = (soma * 10 % 11) % 10;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int dig2 = (soma * 10 % 11) % 10;

        return cpf.endsWith(String.valueOf(dig1) + String.valueOf(dig2));
    }
}
