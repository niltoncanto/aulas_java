import java.util.Scanner;
import java.util.Random;
public class Jokenpoo {
    public static void main(String[] args) {
        Integer computador = 0;
        String scomputador;
        Integer humano = 0;
        Integer vcomputador = 0;
        Integer vhumano = 0;
        Integer empates = 0;
        Scanner scanner = new Scanner(System.in);
        Random  random  = new Random();
        String[] opcoes = {"papel", "tesoura", "pedra"};
        System.out.println("JOKENPOO");
        System.out.println("Opções:");
        System.out.println("1 - papel");
        System.out.println("2 - tesoura");
        System.out.println("3 - pedra");
        System.out.print("Humano: ");
        humano = scanner.nextInt();
        computador = random.nextInt(3)+1;
        scomputador=opcoes[computador-1];
        System.out.println("computador: "+computador+scomputador);

    }
}
