import java.util.Scanner; // Importa Scanner para entrada de dados

public class ConversaoTipo1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1️⃣ Solicita entrada do usuário
        System.out.print("Digite um número inteiro: ");
        int numInt = scanner.nextInt();
        
        System.out.print("Digite um número decimal (double): ");
        double numDouble = scanner.nextDouble();
        
        System.out.print("Digite um número grande (long): ");
        long numLong = scanner.nextLong();
        
        System.out.print("Digite um valor booleano (true/false): ");
        boolean boolValue = scanner.nextBoolean();

        // 2️⃣ Converte para classes Wrapper
        Integer intWrapper = Integer.valueOf(numInt);
        Double doubleWrapper = Double.valueOf(numDouble);
        Long longWrapper = Long.valueOf(numLong);
        Boolean booleanWrapper = Boolean.valueOf(boolValue);

        // 3️⃣ Realiza operações com os valores
        Integer intResult = intWrapper * 2;
        Double doubleResult = doubleWrapper + 5.5;
        Long longResult = longWrapper / 2;
        Boolean booleanResult = !booleanWrapper; // Inverte o booleano

        // 4️⃣ Exibe os resultados
        System.out.println("\n🔹 Resultados:");
        System.out.println("Número inteiro multiplicado por 2: " + intResult);
        System.out.println("Número decimal somado com 5.5: " + doubleResult);
        System.out.println("Número grande dividido por 2: " + longResult);
        System.out.println("Valor booleano invertido: " + booleanResult);

        scanner.close();
    }
}
