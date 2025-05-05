import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe principal que gerencia as tarefas
 */
public class GerenciadorTarefas {
    private static ArrayList<Tarefa> tarefas = new ArrayList<>(); // Lista de tarefas
    private static int proximoId = 1; // ID único para cada tarefa
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            // Exibe o menu para o usuário
            System.out.println("\n===== Gerenciador de Tarefas =====");
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Visualizar Tarefas");
            System.out.println("3. Remover Tarefa");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            // Captura a entrada do usuário
            while (!scanner.hasNextInt()) { // Valida se a entrada é um número válido
                System.out.println("Por favor, insira um número válido.");
                scanner.next(); // Descarta entrada inválida
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha

            // Executa a ação correspondente
            switch (opcao) {
                case 1:
                    adicionarTarefa();
                    break;
                case 2:
                    visualizarTarefas();
                    break;
                case 3:
                    removerTarefa();
                    break;
                case 4:
                    System.out.println("Encerrando o programa. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 4); // Continua até que o usuário escolha "Sair"

        // Fecha o scanner para evitar vazamento de recursos
        scanner.close();
    }

    /**
     * Método para adicionar uma nova tarefa.
     */
    private static void adicionarTarefa() {
        System.out.print("\nDigite a descrição da nova tarefa: ");
        String descricao = scanner.nextLine();

        // Criando uma nova tarefa e adicionando à lista
        Tarefa novaTarefa = new Tarefa(proximoId, descricao);
        tarefas.add(novaTarefa);

        System.out.println("Tarefa adicionada com sucesso! ID: " + proximoId);
        proximoId++; // Incrementa o identificador único para a próxima tarefa
    }

    /**
     * Método para visualizar todas as tarefas cadastradas.
     */
    private static void visualizarTarefas() {
        System.out.println("\n===== Lista de Tarefas =====");

        if (tarefas.isEmpty()) { // Verifica se há tarefas na lista
            System.out.println("Nenhuma tarefa cadastrada.");
        } else {
            // Percorre a lista e exibe cada tarefa
            for (Tarefa tarefa : tarefas) {
                System.out.println(tarefa);
            }
        }
    }

    /**
     * Método para remover uma tarefa pelo ID.
     */
    private static void removerTarefa() {
        System.out.print("\nDigite o ID da tarefa a ser removida: ");

        // Valida se a entrada é um número válido
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, insira um número válido.");
            scanner.next(); // Descarta entrada inválida
        }
        int id = scanner.nextInt();
        scanner.nextLine(); // Consome a quebra de linha

        // Procura a tarefa pelo ID e remove, se existir
        boolean tarefaRemovida = false;
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getId() == id) {
                tarefas.remove(tarefa);
                System.out.println("Tarefa rem

