/*
Classes:
1️⃣ Sala - Representa uma sala de reunião com nome e status.

2️⃣ GerenciadorReservas - Responsável por gerenciar a reserva e o cancelamento das salas.

3️⃣ SistemaReservas (classe principal) - Controla a interação com o usuário.

*/
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Classe Sala: representa uma sala de reunião
class Sala {
    private String nome;
    private boolean reservada;

    public Sala(String nome) {
        this.nome = nome;
        this.reservada = false;
    }

    public String getNome() {
        return nome;
    }

    public boolean isReservada() {
        return reservada;
    }

    public void reservar() {
        this.reservada = true;
    }

    public void cancelarReserva() {
        this.reservada = false;
    }

    @Override //sobrescrita do método
    public String toString() {
        return nome + " - " + (reservada ? "Reservada" : "Disponível");
    }
}

// Classe GerenciadorReservas: gerencia a reserva e o cancelamento das salas
class GerenciadorReservas {
    //não aconselhavel
    //private HashMap<String, Sala> salas = new HashMap<>(); 

    //boa pratica
    //Map<String, Integer> sala → Declaração usando a interface Map.
    //new HashMap<>() → Instancia um HashMap, que é uma implementação de Map.
    //permite trocar a implementação facilmente (HashMap, TreeMap, LinkedHashMap etc.).
    private Map<String, Sala> salas = new HashMap<>(); 

    public GerenciadorReservas() {
        inicializarSalas();
    }

    private void inicializarSalas() {
        salas.put("Sala A", new Sala("Sala A"));
        salas.put("Sala B", new Sala("Sala B"));
        salas.put("Sala C", new Sala("Sala C"));
        salas.put("Sala D", new Sala("Sala D"));
        salas.put("Sala E", new Sala("Sala E"));
    }

    public void listarSalas() {
        System.out.println("\n===== Salas de Reunião =====");
        for (Sala sala : salas.values()) {
            System.out.println(sala);
        }
    }

    public boolean reservarSala(String nome) {
        if (salas.containsKey(nome)) {
            Sala sala = salas.get(nome);
            if (!sala.isReservada()) {
                sala.reservar();
                return true;
            }
        }
        return false;
    }

    public boolean cancelarReserva(String nome) {
        if (salas.containsKey(nome)) {
            Sala sala = salas.get(nome);
            if (sala.isReservada()) {
                sala.cancelarReserva();
                return true;
            }
        }
        return false;
    }
}

// Classe principal que controla o sistema e interação com o usuário
public class SistemaReservasPoo {
    private static Scanner scanner = new Scanner(System.in);
    private static GerenciadorReservas gerenciador = new GerenciadorReservas();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = capturarOpcao();

            switch (opcao) {
                case 1 -> gerenciador.listarSalas();
                case 2 -> reservarSala();
                case 3 -> cancelarReserva();
                case 4 -> System.out.println("Saindo do sistema. Até logo!");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 4);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===== Sistema de Reservas =====");
        System.out.println("1. Listar salas disponíveis");
        System.out.println("2. Reservar uma sala");
        System.out.println("3. Cancelar reserva de uma sala");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int capturarOpcao() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, insira um número válido.");
            scanner.next();
        }
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    private static void reservarSala() {
        System.out.print("\nDigite o nome da sala que deseja reservar: ");
        String nomeSala = scanner.nextLine();
        if (gerenciador.reservarSala(nomeSala)) {
            System.out.println("Reserva confirmada para a " + nomeSala + "!");
        } else {
            System.out.println("Erro: Sala não encontrada ou já reservada!");
        }
    }

    private static void cancelarReserva() {
        System.out.print("\nDigite o nome da sala que deseja cancelar a reserva: ");
        String nomeSala = scanner.nextLine();
        if (gerenciador.cancelarReserva(nomeSala)) {
            System.out.println("Reserva da " + nomeSala + " cancelada com sucesso.");
        } else {
            System.out.println("Erro: Sala não encontrada ou não estava reservada!");
        }
    }
}

