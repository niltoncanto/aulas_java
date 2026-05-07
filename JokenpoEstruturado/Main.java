import java.util.Random;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Integer jogador;
        Integer computador;
        Integer vjogador=0;
        Integer vcomputador=0;
        Integer empates=0;
        Scanner scanner = new Scanner(System.in);
        Random  random = new Random();
        while(true){
            System.out.println("Opções");
            System.out.println("Digite 1 para pedra");
            System.out.println("Digite 2 para papel");
            System.out.println("Digite 3 para tesoura");
            System.out.println("Digite 0 para sair");

            System.out.print("Jogador:");
            jogador = scanner.nextInt();

            if (jogador==0){
                break;
            }
            computador = random.nextInt(3) + 1;
            System.out.println("computador:"+computador);
            if (jogador == computador){
                empates+=1;
                System.out.println("Empatou!");
            }else if (jogador==1 && computador==3){
                vjogador+=1;
                System.out.println("Vitória do Jogador!");
            }else if (jogador==2 && computador==1){
                vjogador+=1;
            }else if (jogador==3 && computador==2){
                vjogador+=1;
                System.out.println("Vitória do Jogador!");
            }else{
                vcomputador+=1;
                System.out.println("Vitória do Computador!");
            }
            System.out.println("*************");
            System.out.println("empates:"+empates);
            System.out.println("Vitórias Jogador:"+vjogador);
            System.out.println("Vitórias Computador:"+vcomputador);
            System.out.println("*************");
        }

    }
}