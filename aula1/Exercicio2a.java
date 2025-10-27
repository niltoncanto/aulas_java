/*
Crie um programa em Java que:
􀵝 Solicite ao usuário que insira os seguintes valores:
- Um número inteiro (`int`)
- Um número decimal (`float`)
- Um número grande (`long`)
- Um caractere (`char`)
􀵠 Converta esses valores para suas respectivas classes wrapper (`Integer`,
`Float`, `Long`, `Character`).
􀶱 Realize as seguintes operações:
- Some 10 ao número inteiro.
- Multiplique o número decimal por 2.5.
- Subtraia 1000 do número Long.
- Verifique se o caractere inserido é uma letra (`A-Z` ou `a-z`).
􀵣 Exiba os resultados na tela.
Requisitos
Utilizar Scanner para entrada de dados
Converter os valores primitivos para classes Wrapper usando `.valueOf()`
Realizar operações matemáticas e lógicas com os valores Wrapper
Exibir os resultados das operações na tela
*/
public class Exercicio2a {
    public static void main(String[] args){
        //criar objeto scanner
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        //solicitar e ler numero inteiro
        System.out.print("Digite um número inteiro: ");
        int numeroInteiroPrimitivo = scanner.nextInt();
        //solicitar e ler numero decimal (float)
        System.out.print("Digite um número decimal (float): ");
        float numeroDecimalPrimitivo = scanner.nextFloat();
        //solicitar e ler numero grande (long)
        System.out.print("Digite um número grande (long): ");
        long numeroLongPrimitivo = scanner.nextLong();
        //solicitar e ler caractere (char)
        System.out.print("Digite um caractere (char): ");
        char caracterePrimitivo = scanner.next().charAt(0);
        //fechar scanner
        scanner.close();
        //converter valores primitivos para classes wrapper
        //usando valueOf() para converter primitivos para wrappers
        Integer numeroInteiroWrapper = Integer.valueOf(numeroInteiroPrimitivo);
        Float numeroDecimalWrapper = Float.valueOf(numeroDecimalPrimitivo);
        Long numeroLongWrapper = Long.valueOf(numeroLongPrimitivo);
        Character caractereWrapper = Character.valueOf(caracterePrimitivo);
        //realizar operações com os valores
        Integer resultadoInteiro = numeroInteiroWrapper + 10;
        Float resultadoDecimal = numeroDecimalWrapper * 2.5f;
        Long resultadoLong = numeroLongWrapper - 1000;
        Boolean resultadoCaractere = Character.isLetter(caractereWrapper);
        //imprimir resultados das operações
        System.out.println("\n--- Resultados das Operações ---");
        System.out.println("Número inteiro somado com 10: " + resultadoInteiro);
        System.out.println("Número decimal multiplicado por 2.5: " + resultadoDecimal);
        System.out.println("Número Long subtraído por 1000: " + resultadoLong);
        System.out.println("O caractere inserido é uma letra? " + resultadoCaractere);

    }
}
