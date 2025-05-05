/*
Crie um programa em Java que:
1️⃣ Solicite ao usuário que insira os seguintes valores pelo teclado:
   - Um número inteiro
   - Um número decimal (`double`)
   - Um número grande (`long`)
   - Um valor verdadeiro/falso (`boolean`)
2️⃣ Converta esses valores para suas respectivas classes wrapper (`Integer`, `Double`, `Long`, `Boolean`).
3️⃣ Realize operações com os valores:
   - Multiplique o número inteiro por 2.
   - Some 5.5 ao número decimal.
   - Divida o número Long por 2.
   - Inverta o valor booleano (`true` → `false` e vice-versa).
4️⃣ Imprima os resultados das operações.
*/

import java.util.Scanner;
public class Exemplo1 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entre com um número inteiro:");
        int numInt = scanner.nextInt();
        System.out.print("Entre com um número decimal:");
        double numDouble = scanner.nextDouble();
        System.out.print("Entre com um número inteiro grande:");
        long numLong = scanner.nextLong();
        System.out.print("Entre com um valor booleano True or False:");
        boolean booleanValue = scanner.nextBoolean();
        Integer intWrapper = Integer.valueOf(numInt);
        Double DoubleWrapper = Double.valueOf(numDouble);
        Long LongWrapper = Long.valueOf(numLong); 
        Boolean booleanWrapper = Boolean.valueOf(booleanValue);
        Integer intResult = intWrapper;
        Double doubleResult = DoubleWrapper + 5.5;
        Long longResult = LongWrapper/2;
        Boolean booleanResult = !booleanWrapper; // Inverte o booleano
        System.out.println("\n🔹 Resultados:");
        System.out.println("Número inteiro multiplicado por 2: " + intResult);
        System.out.println("Número decimal somado com 5.5: " + doubleResult);
        System.out.println("Número grande dividido por 2: " + longResult);
        System.out.println("Valor booleano invertido: " + booleanResult);
        scanner.close();

    }
}
