/*Manipulação de Texto com String e StringBuilder
Crie um programa em Java que permita ao usuário inserir
uma frase e realizar operações de manipulação de texto
usando tanto String quanto StringBuilder.
O programa deve:
􀵝 Solicitar uma frase ao usuário (entrada como String).
􀵠 Exibir o tamanho da frase (número de caracteres).
􀶱 Converter a frase para maiúsculas usando String.
􀵣 Reverter a frase usando StringBuilder.
􀵦 Substituir todas as vogais por * usando StringBuilder. 
*/
public class Exercicio3a {
   public static void main(String[] args){
    //criação do objeto scanner
    java.util.Scanner scanner = new java.util.Scanner(System.in);
    //solicitar uma frase ao usuário
    System.out.print("Digite uma frase: ");
    String frase = scanner.nextLine();
    //exibir o tamanho da frase
    System.out.println("Tamanho da frase: " + frase.length());
    //converter a frase para maiúsculas
      String fraseMaiuscula = frase.toUpperCase();
      System.out.println("Frase em maiúsculas: " + fraseMaiuscula);
      //reverter a frase usando StringBuilder
      StringBuilder sb = new StringBuilder(frase);
      String fraseRevertida = sb.reverse().toString();
      System.out.println("Frase revertida: " + fraseRevertida);
      //substituir todas as vogais por * usando StringBuilder
      for (int i = 0; i < sb.length(); i++) {         
          char c = sb.charAt(i);
          if ("aeiouAEIOU".indexOf(c) != -1) {
              sb.setCharAt(i, '*');
          }
      }
      String fraseSubstituida = sb.toString();
      System.out.println("Frase com vogais substituídas: " + fraseSubstituida);
      //fechar o scanner         
      scanner.close();    
   }
}