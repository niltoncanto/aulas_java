import java.util.Scanner;
import java.util.Random;

// Classe principal do jogo Jokenpô
public class Jokenpo {
    public static void main(String[] args){
        // Declaração das variáveis do jogo
        Integer jogador; // Armazena a escolha do jogador
        Integer computador; // Armazena a escolha do computador (aleatória)
        Integer vJogador=0; // Contador de vitórias do jogador
        Integer vComputador=0; // Contador de vitórias do computador
        Integer empates = 0; // Contador de empates

        // Scanner para entrada de dados do jogador
        Scanner scanner = new Scanner(System.in);

        // Objeto Random para gerar jogadas aleatórias do computador
        Random random = new Random(); 

        // Loop infinito para manter o jogo rodando até o jogador optar por sair
        while (true){
            // Exibição do menu de opções
            System.out.println("Menu de Opções");
            System.out.println("1 - papel");
            System.out.println("2 - pedra");
            System.out.println("3 - tesoura");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");
            
            // Captura a opção escolhida pelo jogador
            jogador = scanner.nextInt();

            // Computador escolhe aleatoriamente entre 1 e 3 (1 = papel, 2 = pedra, 3 = tesoura)
            computador = random.nextInt(3) + 1;

            // Exibição das escolhas
            System.out.println("#######");
            System.out.println("Jogador: " + jogador);
            System.out.println("Computador: " + computador);
            System.out.println("#######");

            // Verifica se o jogador optou por sair
            if (jogador == 4){
                break; // Sai do loop e encerra o jogo
            } 
            // Caso de empate
            else if (jogador == computador){
                empates += 1;
            } 
            // Verifica as condições de vitória do jogador
            else if (jogador == 1 && computador == 2){ // Papel ganha da pedra
                vJogador += 1;
            } else if (jogador == 2 && computador == 3){ // Pedra perde para a tesoura
                vJogador += 1;
            } else if (jogador == 3 && computador == 1){ // Tesoura ganha do papel
                vJogador += 1;
            } 
            // Caso contrário, o computador vence
            else {
                vComputador += 1;
            }

            // Exibição do placar atualizado
            System.out.println("PLACAR");
            System.out.println("Vitórias jogador: " + vJogador);
            System.out.println("Vitórias computador: " + vComputador);
            System.out.println("Empates: " + empates);
        }

        // Fecha o scanner para liberar recursos
        scanner.close();
    }
}
