import java.util.Random;
import java.util.Scanner;

/**
 * Jokenpo POO (sem enum)
 * ------------------------------------------------------------
 * Regras:
 * 1 = Pedra, 2 = Papel, 3 = Tesoura
 *
 * Estrutura em um único arquivo:
 * - Main (classe pública)
 * - Jogada
 * - Resultado
 * - RegraJokenpo
 * - JogadorHumano
 * - JogadorComputador
 * - Placar
 * - ConsoleUI
 * - JogoJokenpo
 *
 * Observação didática:
 * - Só usamos: classe, objeto, construtor, visibilidade, get e set.
 * - Evitamos enum e outros tópicos.
 */
public class Main {

    // Ponto de entrada do programa
    public static void main(String[] args) {

        // Cria o jogo (objeto que orquestra tudo)
        JogoJokenpo jogo = new JogoJokenpo();

        // Executa o loop principal
        jogo.executar();
    }
}

/**
 * Representa uma jogada do jogo (Pedra/Papel/Tesoura),
 * usando constantes inteiras para não depender de enum.
 */
class Jogada {

    // Constantes de domínio (valores válidos)
    public static final int PEDRA = 1;
    public static final int PAPEL = 2;
    public static final int TESOURA = 3;

    // Estado do objeto (qual foi a jogada)
    private int valor;

    // Construtor: cria uma jogada com um valor inicial
    public Jogada(int valor) {
        this.valor = valor;
    }

    // Getter: devolve o valor (1, 2 ou 3)
    public int getValor() {
        return valor;
    }

    // Setter: altera o valor da jogada
    public void setValor(int valor) {
        this.valor = valor;
    }

    // Retorna o nome legível da jogada
    public String getNome() {
        if (valor == PEDRA) return "Pedra";
        if (valor == PAPEL) return "Papel";
        return "Tesoura";
    }

    // Valida se o valor está dentro do conjunto permitido
    public boolean ehValida() {
        return valor == PEDRA || valor == PAPEL || valor == TESOURA;
    }
}

/**
 * Representa o resultado de uma rodada.
 * 1 = vitória do usuário, 2 = derrota do usuário, 3 = empate.
 */
class Resultado {

    // Constantes do resultado
    public static final int VITORIA = 1;
    public static final int DERROTA = 2;
    public static final int EMPATE  = 3;

    // Estado do objeto (qual foi o resultado)
    private int valor;

    // Construtor: cria o resultado com um valor inicial
    public Resultado(int valor) {
        this.valor = valor;
    }

    // Getter: devolve o valor do resultado
    public int getValor() {
        return valor;
    }

    // Setter: altera o valor do resultado
    public void setValor(int valor) {
        this.valor = valor;
    }

    // Mensagem pronta para exibir no console
    public String getMensagem() {
        if (valor == VITORIA) return "Você venceu!";
        if (valor == DERROTA) return "Você perdeu!";
        return "Empate!";
    }
}

/**
 * Classe que concentra a regra do jogo.
 * Ela recebe as duas jogadas e devolve um Resultado.
 */
class RegraJokenpo {

    // Avalia a rodada comparando as duas jogadas
    public Resultado avaliar(Jogada jogador, Jogada computador) {

        // Lê os valores das duas jogadas
        int j = jogador.getValor();
        int c = computador.getValor();

        // Caso 1: iguais -> empate
        if (j == c) {
            return new Resultado(Resultado.EMPATE);
        }

        // Caso 2: condições de vitória do usuário
        // Pedra vence Tesoura
        if (j == Jogada.PEDRA && c == Jogada.TESOURA) {
            return new Resultado(Resultado.VITORIA);
        }

        // Tesoura vence Papel
        if (j == Jogada.TESOURA && c == Jogada.PAPEL) {
            return new Resultado(Resultado.VITORIA);
        }

        // Papel vence Pedra
        if (j == Jogada.PAPEL && c == Jogada.PEDRA) {
            return new Resultado(Resultado.VITORIA);
        }

        // Caso 3: se não empatou e não venceu -> perdeu
        return new Resultado(Resultado.DERROTA);
    }
}

/**
 * Jogador humano: aqui guardamos a jogada atual do usuário.
 */
class JogadorHumano {

    // Estado: última jogada escolhida
    private Jogada jogadaAtual;

    // Construtor: inicializa com valor "0" (ainda não escolhido)
    public JogadorHumano() {
        this.jogadaAtual = new Jogada(0);
    }

    // Getter: devolve a jogada atual
    public Jogada getJogadaAtual() {
        return jogadaAtual;
    }

    // Setter: define a jogada atual
    public void setJogadaAtual(Jogada jogadaAtual) {
        this.jogadaAtual = jogadaAtual;
    }
}

/**
 * Jogador computador: sorteia uma jogada.
 */
class JogadorComputador {

    // Estado: jogada atual do computador
    private Jogada jogadaAtual;

    // Gerador de números aleatórios
    private Random random;

    // Construtor: cria objetos necessários
    public JogadorComputador() {
        this.jogadaAtual = new Jogada(0);
        this.random = new Random();
    }

    // Getter: devolve a jogada atual
    public Jogada getJogadaAtual() {
        return jogadaAtual;
    }

    // Sorteia a jogada do computador
    public void sortearJogada() {

        // Gera um inteiro de 1 a 100 (como no enunciado típico)
        int n = random.nextInt(100) + 1;

        // Mapeia faixas para 3 opções
        int valor;
        if (n <= 33) {
            valor = Jogada.PEDRA;
        } else if (n <= 66) {
            valor = Jogada.PAPEL;
        } else {
            valor = Jogada.TESOURA;
        }

        // Atualiza o objeto jogada
        this.jogadaAtual.setValor(valor);
    }
}

/**
 * Placar do jogo: conta vitórias do usuário.
 */
class Placar {

    // Estado: quantidade de vitórias do usuário
    private int vitoriasUsuario;

    // Construtor: inicia zerado
    public Placar() {
        this.vitoriasUsuario = 0;
    }

    // Getter: devolve o número de vitórias
    public int getVitoriasUsuario() {
        return vitoriasUsuario;
    }

    // Registra o resultado da rodada no placar
    public void registrar(Resultado resultado) {

        // Se venceu, incrementa
        if (resultado.getValor() == Resultado.VITORIA) {
            vitoriasUsuario++;
        }
    }
}

/**
 * Interface de console: lê entradas, valida e imprime saídas.
 * Mantém I/O separado da regra do jogo.
 */
class ConsoleUI {

    // Scanner para ler do teclado
    private Scanner scanner;

    // Construtor: cria o scanner
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    // Lê uma jogada válida do usuário
    public Jogada lerJogadaValida() {

        // Loop até obter um valor correto
        while (true) {

            // Mostra instruções
            System.out.println("Escolha: 1-Pedra, 2-Papel, 3-Tesoura");
            System.out.print("Digite sua opção: ");

            // Se o que veio não é inteiro, descarta e tenta de novo
            if (!scanner.hasNextInt()) {
                scanner.nextLine(); // descarta a linha inválida
                System.out.println("Entrada inválida. Digite um número.");
                System.out.println();
                continue;
            }

            // Lê o inteiro
            int valor = scanner.nextInt();

            // Consome o ENTER que sobrou no buffer
            scanner.nextLine();

            // Cria uma jogada com o valor digitado
            Jogada j = new Jogada(valor);

            // Se for válida, devolve
            if (j.ehValida()) {
                return j;
            }

            // Se não for válida, avisa e repete
            System.out.println("Opção inválida. Use 1, 2 ou 3.");
            System.out.println();
        }
    }

    // Pergunta se o usuário quer continuar jogando
    public boolean perguntarSeContinua() {

        // Loop até obter resposta S/N
        while (true) {
            System.out.print("Deseja jogar novamente? (S/N): ");

            // Lê a linha e normaliza
            String s = scanner.nextLine().trim().toUpperCase();

            // Se for S, continua
            if (s.equals("S")) return true;

            // Se for N, encerra
            if (s.equals("N")) return false;

            // Caso contrário, pede novamente
            System.out.println("Resposta inválida. Digite S ou N.");
        }
    }

    // Mostra informações da rodada
    public void mostrarRodada(Jogada usuario, Jogada computador, Resultado resultado) {

        // Exibe as duas jogadas
        System.out.println("Você jogou: " + usuario.getNome());
        System.out.println("Computador jogou: " + computador.getNome());

        // Exibe o resultado
        System.out.println(resultado.getMensagem());

        // Linha em branco para separar rodadas
        System.out.println();
    }

    // Mostra o encerramento e o placar final
    public void mostrarFinal(Placar placar) {
        System.out.println("Fim de jogo.");
        System.out.println("Total de vitórias do usuário: " + placar.getVitoriasUsuario());
    }
}

/**
 * Orquestrador: controla o fluxo do jogo.
 * Ele conecta UI, jogadores, regra e placar.
 */
class JogoJokenpo {

    // Dependências do jogo
    private ConsoleUI ui;
    private JogadorHumano humano;
    private JogadorComputador computador;
    private RegraJokenpo regra;
    private Placar placar;

    // Construtor: cria tudo o que o jogo precisa
    public JogoJokenpo() {
        this.ui = new ConsoleUI();
        this.humano = new JogadorHumano();
        this.computador = new JogadorComputador();
        this.regra = new RegraJokenpo();
        this.placar = new Placar();
    }

    // Executa o loop principal
    public void executar() {

        // Começa jogando
        boolean continuar = true;

        // Enquanto o usuário quiser continuar
        while (continuar) {

            // 1) Lê jogada do usuário (validada)
            Jogada jogadaUsuario = ui.lerJogadaValida();

            // 2) Guarda no jogador humano
            humano.setJogadaAtual(jogadaUsuario);

            // 3) Sorteia jogada do computador
            computador.sortearJogada();

            // 4) Avalia o resultado
            Resultado resultado = regra.avaliar(humano.getJogadaAtual(), computador.getJogadaAtual());

            // 5) Atualiza o placar (conta vitórias)
            placar.registrar(resultado);

            // 6) Mostra a rodada
            ui.mostrarRodada(humano.getJogadaAtual(), computador.getJogadaAtual(), resultado);

            // 7) Pergunta se continua
            continuar = ui.perguntarSeContinua();

            // Linha em branco ao final da rodada
            System.out.println();
        }

        // Ao sair do loop, mostra placar final
        ui.mostrarFinal(placar);
    }
}
