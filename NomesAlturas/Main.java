import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    ArrayList<String> pessoas = new ArrayList<String>();
    Scanner scan = new Scanner(System.in);
    do{
      System.out.print("Informe o nome da pessoa: ");
      nome = scan.nextLine();
      pessoas.add(nome);
      System.out.print("Informe a altura da pessoa: ");
      idade = scan.nextLine();
    }while()

    for(int i=0; i<carro.size(); i++){
      System.out.println(carro.get(i));
    }
    
    System.out.println("***********");
    for(String i: carro){
      System.out.println(i);
    }
  }
}