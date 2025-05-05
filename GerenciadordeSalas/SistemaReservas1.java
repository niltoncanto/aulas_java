import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Classe Sala: Representa uma sala de reunião
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
        reservada = true;
    }

    public void cancelarReserva() {
        reservada = false;
    }

    @Override
    public String toString() {
        return nome + " - " + (reservada ? "Reservada" : "Disponível");
    }
}

// Classe GerenciadorReservas: Gerencia as reservas de salas
class GerenciadorReservas {
    private Map<String, Sala> salas = new HashMap<>();

    public GerenciadorReservas() {
        // Inicializa as salas no construtor
        salas.put("Sala A", new Sala("Sala A"));
        salas.put("Sala B", new Sala("Sala B"));
        salas.put("Sala C", new Sala("Sala C"));
        salas.put("Sala D", new Sala("Sala D"));
        salas.put("Sala E", new Sala("Sala E"));
    }

    public void listarSalas() {
        for (Sala sala : salas.values()) {
            System.out.println(sala);
        }
    }

    public boolean reservarSala(String nome) {
        Sala sala = salas.get(nome);
        if (sala != null && !sala.isReservada()) {
            sala.reservar();
            return true;
        }
        return false;
    }

    public boolean cancelarReserva(String nome) {
        Sala sala = salas.get(nome);
        if (sala != null && sala.isReservada()) {
            sala.cancelarReserva();
            return true;
        }
        return false;
    }
}

// Classe principal para interação com o usuário
public class SistemaReservas1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorReservas gerenciador = new GerenciadorReservas();
        int opcao;

        do {
            System.out.println("\n1. Listar salas\n2. Reservar\n3. Cancelar reserva\n4. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            'scanner.nextLine(); // Limpa buffer'

            if (opcao == 1) {
                gerenciador.listarSalas();
            } else if (opcao == 2) {
                System.out.print("Nome da sala para reservar: ");
                String nome = scanner.nextLine();
                System.out.println(gerenciador.reservarSala(nome) ? "Reservada!" : "Erro: Sala não encontrada ou já reservada.");
            } else if (opcao == 3) {
                System.out.print("Nome da sala para cancelar: ");
                String nome = scanner.nextLine();
                System.out.println(gerenciador.cancelarReserva(nome) ? "Reserva cancelada!" : "Erro: Sala não encontrada ou não reservada.");
            }
        } while (opcao != 4);

        System.out.println("Sistema encerrado.");
        scanner.close();
    }
}
