import java.util.Scanner;

public class ConversaoTipo2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1️⃣ Solicita entrada do usuário
        System.out.print("Digite um número inteiro: ");
        int numInt = scanner.nextInt();

        System.out.print("Digite um número decimal (float): ");
        float numFloat = scanner.nextFloat();

        System.out.print("Digite um número grande (long): ");
        long numLong = scanner.nextLong();

        System.out.print("Digite um caractere: ");
        char caractere = scanner.next().charAt(0);

        // 2️⃣ Converte para classes Wrapper
        Integer intWrapper = Integer.valueOf(numInt);
        Float floatWrapper = Float.valueOf(numFloat);
        Long longWrapper = Long.valueOf(numLong);
        Character charWrapper = Character.valueOf(caractere);

        // 3️⃣ Realiza operações
        Integer intResult = intWrapper + 10;
        Float floatResult = floatWrapper * 2.5F;
        Long longResult = longWrapper - 1000;
        Boolean isLetter = Character.isLetter(charWrapper);

        // 4️⃣ Exibe os resultados
        System.out.println("\n🔹 Resultados:");
        System.out.println("Número inteiro + 10: " + intResult);
        System.out.println("Número decimal * 2.5: " + floatResult);
        System.out.println("Número grande - 1000: " + longResult);
        System.out.println("O caractere '" + charWrapper + "' é uma letra? " + isLetter);

        scanner.close();
    }
}
