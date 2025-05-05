import java.util.Random;
import java.util.Scanner;

// Classe que representa um jogador
class Jogador {
    private String nome;
    private int escolha; // 1 - Pedra, 2 - Papel, 3 - Tesoura

    public Jogador(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getEscolha() {
        return escolha;
    }

    public void setEscolha(int escolha) {
        this.escolha = escolha;
    }
}

// Classe principal que controla o jogo
class Jokenpo {
    private Jogador humano;
    private Jogador computador;
    private int vitoriasHumano = 0;
    private int vitoriasComputador = 0;
    private int empates = 0;
    private Scanner scanner;
    private Random rand;

    public Jokenpo(String nomeJogador) {
        this.humano = new Jogador(nomeJogador);
        this.computador = new Jogador("Computador");
        this.scanner = new Scanner(System.in);
        this.rand = new Random();
    }

    // Método para ler a escolha do jogador
    private void escolhaJogador() {
        System.out.println("\nEscolha uma opção:");
        System.out.println("1 - Pedra");
        System.out.println("2 - Papel");
        System.out.println("3 - Tesoura");
        int escolha;
        
        while (true) {
            System.out.print("Digite sua escolha: ");
            escolha = scanner.nextInt();
            if (escolha >= 0 && escolha <= 3) {
                break;
            } else {
                System.out.println("Escolha inválida. Tente novamente!");
            }
        }

        humano.setEscolha(escolha);
    }

    // Método para gerar a escolha do computador
    private void escolhaComputador() {
        int escolha = rand.nextInt(3) + 1; // Gera número entre 1 e 3
        computador.setEscolha(escolha);
    }

    // Método que verifica o vencedor da rodada
    private void verificarVencedor() {
        int escolhaJogador = humano.getEscolha();
        int escolhaComputador = computador.getEscolha();

        System.out.println("\n" + humano.getNome() + " escolheu: " + traduzirEscolha(escolhaJogador));
        System.out.println(computador.getNome() + " escolheu: " + traduzirEscolha(escolhaComputador));

        if (escolhaJogador == escolhaComputador) {
            System.out.println("Empate!");
            empates++;
        } else if ((escolhaJogador == 1 && escolhaComputador == 3) || 
                   (escolhaJogador == 2 && escolhaComputador == 1) || 
                   (escolhaJogador == 3 && escolhaComputador == 2)) {
            System.out.println(humano.getNome() + " venceu!");
            vitoriasHumano++;
        } else {
            System.out.println(computador.getNome() + " venceu!");
            vitoriasComputador++;
        }
    }

    // Método auxiliar para traduzir a escolha numérica para string
    private String traduzirEscolha(int escolha) {
        return switch (escolha) {
            case 1 -> "Pedra";
            case 2 -> "Papel";
            case 3 -> "Tesoura";
            default -> "Desconhecido";
        };
    }

    // Método principal do jogo
    public void jogar() {
        System.out.println("\n--- Bem-vindo ao Jokenpô! ---");

        while (true) {
            escolhaJogador();
            escolhaComputador();
            verificarVencedor();

            System.out.println("\nPlacar Atual:");
            System.out.println(humano.getNome() + ": " + vitoriasHumano);
            System.out.println(computador.getNome() + ": " + vitoriasComputador);
            System.out.println("Empates: " + empates);

            System.out.print("\nDeseja jogar novamente? (s/n): ");
            char resposta = scanner.next().toLowerCase().charAt(0);
            if (resposta != 's') {
                System.out.println("Obrigado por jogar! Até mais!");
                break;
            }
        }
    }
}

// Classe principal para rodar o jogo
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();

        Jokenpo jogo = new Jokenpo(nome);
        jogo.jogar();

        scanner.close();
    }
}

