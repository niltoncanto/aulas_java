import java.util.Scanner;
public class PrimitivosWrappers{
    public static void main(String[] args){
        System.out.println("Variáveis Primitivas e Wrappers");
        Scanner scanner = new Scanner(System.in);
        System.out.println("*******************");
        System.out.print("Entre com um número Inteiro:");
        int numeroInteiro = scanner.nextInt();
        System.out.println("Numero Inteiro:"+numeroInteiro);
        System.out.print("Entre com um número Decimal:");
        double numeroDouble = scanner.nextDouble();
        System.out.println("Numero Double:"+numeroDouble);
        scanner.close();
        Integer intWrapper = Integer.valueOf(numeroInteiro);
        Double doubleWrapper = Double.valueOf(numeroDouble);
        System.out.println("hash int = " + intWrapper.byteValue());
    }
}