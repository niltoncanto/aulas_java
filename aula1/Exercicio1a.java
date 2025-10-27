/* Crie um programa em Java que:
􀵝 Solicite ao usuário que insira os seguintes valores pelo teclado:
- Um número inteiro
- Um número decimal (`double`)
- Um número grande (`long`)
- Um valor verdadeiro/falso (`boolean`)
􀵠 Converta esses valores para suas respectivas classes wrapper
(`Integer`, `Double`, `Long`, `Boolean`).
􀶱 Realize operações com os valores:
- Multiplique o número inteiro por 2.
- Some 5.5 ao número decimal.
- Divida o número Long por 2.
- Inverta o valor booleano (`true` → `false` e vice-versa).
􀵣 Imprima os resultados das operações. */


public class Exercicio1a {
    public static void main(String[] args) {
        // Criando um objeto Scanner para ler a entrada do usuário
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        // Solicitando e lendo o número inteiro
        System.out.print("Digite um número inteiro: ");
        int numeroInteiroPrimitivo = scanner.nextInt();

        // Solicitando e lendo o número decimal (double)
        System.out.print("Digite um número decimal (double): ");
        double numeroDecimalPrimitivo = scanner.nextDouble();

        // Solicitando e lendo o número grande (long)
        System.out.print("Digite um número grande (long): ");
        long numeroLongPrimitivo = scanner.nextLong();

        // Solicitando e lendo o valor booleano
        System.out.print("Digite um valor verdadeiro/falso (true/false): ");
        boolean valorBooleanoPrimitivo = scanner.nextBoolean();

        // Fechando o scanner
        scanner.close();

        // Convertendo valores primitivos para suas classes wrapper
        // Usando autoboxing para converter primitivos para wrappers
        // Não é necessário usar valueOf() aqui, pois o autoboxing faz isso automaticamente
        Integer numeroInteiroWrapper = numeroInteiroPrimitivo;
        Double numeroDecimalWrapper = numeroDecimalPrimitivo;
        Long numeroLongWrapper = numeroLongPrimitivo;
        Boolean valorBooleanoWrapper = valorBooleanoPrimitivo;

        // Realizando operações com os valores
        Integer resultadoInteiro = numeroInteiroWrapper * 2;
        Double resultadoDecimal = numeroDecimalWrapper + 5.5;
        Long resultadoLong = numeroLongWrapper / 2;
        Boolean resultadoBooleano = !valorBooleanoWrapper;

        // Imprimindo os resultados das operações
        System.out.println("\n--- Resultados das Operações ---");
        System.out.println("Número inteiro multiplicado por 2: " + resultadoInteiro);
        System.out.println("Número decimal somado com 5.5: " + resultadoDecimal);
        System.out.println("Número Long dividido por 2: " + resultadoLong);
        System.out.println("Valor booleano invertido: " + resultadoBooleano);
    }
}