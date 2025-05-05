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
        this.computador = new Jogador("DeepBlue");
        this.scanner = new Scanner(System.in);
        this.rand = new Scanner();
    }

    // Método para ler a escolha do jogador
    private void escolhaJogador() {
        System.out.println("Escolha sua Opção");
        System.out.println("1 - tesoura");
        System.out.println("2 - papel");
        System.out.println("3 - pedra");
        System.out.println("0 - sair");
        System.out.println("Opção:");
        int escolha = scanner.nextInt();
        humano.setEscolha(escolha);
    }

    // Método para gerar a escolha do computador
    private void escolhaComputador() {
        int escolha = rand.nextInt(3)+1;
        computador.setEscolha(escolha);
    }

    // Método que verifica o vencedor da rodada
    private void verificarVencedor() {
        int escolhaHumano = humano.getEscolha();
        int escolhaComputador = computador.getEscolha()
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
        return witch(escolha){
            case 1-> "tesoura";
            case 1-> "papel";
            case 3-> "pedra";
        }
    }

    // Método principal do jogo
    public void jogar() {
        System.out.println("\n--- Bem-vindo ao Jokenpô! ---");

    }
}

// Classe principal para rodar o jogo
public class JokenpoEx {
    public static void main(String[] args) {

    }
}