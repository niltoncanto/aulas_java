import java.util.Scanner;
public class Exercicio3b {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String  nome = "João";
        StringBuilder  nomeSB = new StringBuilder(nome);
        System.out.println(nome.toUpperCase());
        System.out.println(nomeSB.reverse());
        String nomeString = nomeSB.toString();
        System.out.println(nomeString.toUpperCase());
        scanner.close();
    }
}

//java -cp . Exercicio3b