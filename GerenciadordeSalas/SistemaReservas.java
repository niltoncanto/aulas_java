import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Sistema de Reservas de Salas de Reunião
 */
public class SistemaReservas {
    private static final Map<String, Boolean> salas = new HashMap<>(); // Armazena as salas e seu status (true = reservada, false = disponível)
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarSalas(); // Inicializa as salas como disponíveis

        int opcao;
        do {
            exibirMenu(); // Exibe o menu de opções
            opcao = capturarOpcao(); // Captura e valida a opção do usuário

            switch (opcao) {
                case 1:
                    listarSalas();
                    break;
                case 2:
                    reservarSala();
                    break;
                case 3:
                    cancelarReserva();
                    break;
                case 4:
                    System.out.println("Saindo do sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 4); // O loop continua até o usuário escolher "Sair"

        scanner.close(); // Fecha o Scanner ao finalizar o programa
    }

    /**
     * Inicializa as salas como disponíveis.
     */
    private static void inicializarSalas() {
        salas.put("Sala A", false);
        salas.put("Sala B", false);
        salas.put("Sala C", false);
        salas.put("Sala D", false);
        salas.put("Sala E", false);
    }

    /**
     * Exibe o menu de opções para o usuário.
     */
    private static void exibirMenu() {
        System.out.println("\n===== Sistema de Reservas =====");
        System.out.println("1. Listar salas disponíveis");
        System.out.println("2. Reservar uma sala");
        System.out.println("3. Cancelar reserva de uma sala");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Captura e valida a opção do usuário.
     * @return Opção escolhida.
     */
    private static int capturarOpcao() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, insira um número válido.");
            scanner.next(); // Descarta entrada inválida
        }
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consome a quebra de linha
        return opcao;
    }

    /**
     * Lista todas as salas e seus status (Disponível ou Reservada).
     */
    private static void listarSalas() {
        System.out.println("\n===== Salas de Reunião =====");
        for (Map.Entry<String, Boolean> sala : salas.entrySet()) {
            String status = sala.getValue() ? "Reservada" : "Disponível";
            System.out.println(sala.getKey() + " - " + status);
        }
    }

    /**
     * Permite reservar uma sala disponível.
     */
    private static void reservarSala() {
        System.out.print("\nDigite o nome da sala que deseja reservar: ");
        String salaEscolhida = scanner.nextLine();

        if (salas.containsKey(salaEscolhida)) {
            if (salas.get(salaEscolhida)) {
                System.out.println("Erro: A " + salaEscolhida + " já está reservada!");
            } else {
                salas.put(salaEscolhida, true);
                System.out.println("Reserva confirmada para a " + salaEscolhida + "!");
            }
        } else {
            System.out.println("Erro: Sala não encontrada!");
        }
    }

    /**
     * Permite cancelar a reserva de uma sala reservada.
     */
    private static void cancelarReserva() {
        System.out.print("\nDigite o nome da sala que deseja cancelar a reserva: ");
        String salaEscolhida = scanner.nextLine();

        if (salas.containsKey(salaEscolhida)) {
            if (!salas.get(salaEscolhida)) {
                System.out.println("Erro: A " + salaEscolhida + " não está reservada!");
            } else {
                salas.put(salaEscolhida, false);
                System.out.println("Reserva da " + salaEscolhida + " cancelada com sucesso.");
            }
        } else {
            System.out.println("Erro: Sala não encontrada!");
        }
    }
}
