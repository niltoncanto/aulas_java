import java.util.Scanner;
import java.util.Scanner;
class Main {
  public static void main(String[] args) {
    Scanner scr= new Scanner(System.in);
    System.out.println("Entre com um número inteiro: ");
    int n = scr.nextInt();
    int n1 = n;
    int soma=0;
    int soma1 = soma;
  
    while(n>0){
       soma = soma + n%10;
       n= n/10;
    }
    
    int resultado = SomaAlgarismos(n1,soma1);
    System.out.println(soma);
    System.out.println(resultado);
  }
  public static int SomaAlgarismos(int n, int soma){
    if(n<1){
      return soma;
    }
    soma = soma + n%10;
    n = n/10;
    return SomaAlgarismos(n,soma);
  }
}