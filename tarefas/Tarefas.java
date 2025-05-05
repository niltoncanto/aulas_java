// Importa a classe HashMap para criação de um mapa de tarefas.
import java.util.HashMap;

// Importa a interface Map para trabalhar com a estrutura do mapa.
import java.util.Map;

// Importa a classe Scanner para captura de entrada do usuário.
import java.util.Scanner;

// Declaração da classe principal GerenciadorTarefas.
public class Tarefas {

    // Método principal que será executado quando o programa for iniciado.
    public static void main(String[] args){
        // Declaração e inicialização da variável de opção do menu.
        int opcao=0;

        // Declaração e inicialização da variável que representa o ID da tarefa.
        int id_tarefa=1;

        // Declaração e inicialização da variável que armazena a descrição da tarefa.
        String descricao="";

        // Criação de um mapa para armazenar as tarefas. A chave é o ID e o valor é a descrição da tarefa.
        Map<Integer, String> tarefas = new HashMap<>();

        // Impressões iniciais do programa para orientação do usuário.
        System.out.println("\n");
        System.out.println("*************************");
        System.out.println("Gerenciador de Tarefas");
        System.out.println("1 - incluir Tarefas");
        System.out.println("2 - visualizar Tarefas");
        System.out.println("3 - excluir Tarefas");
        System.out.println("4 - sair");
        System.out.println("*************************");
        System.out.println("\n");

        // Loop infinito para manter o programa em execução até que o usuário decida sair.
        while(true){
            // Solicita ao usuário que insira a opção desejada.
            System.out.print("Digite sua opção: ");
            Scanner scr = new Scanner(System.in); // Criação de um objeto Scanner para ler a entrada do usuário.
            opcao = scr.nextInt(); // Lê a opção do usuário e armazena na variável 'opcao'.
            scr.nextLine(); // Limpa o buffer de entrada.

            // Estrutura switch para tratar as opções escolhidas pelo usuário.
            switch (opcao){
                case 1:
                    // Opção para incluir uma nova tarefa.
                    System.out.print("Digite a descrição da tarefa: "); // Solicita ao usuário a descrição da tarefa.
                    descricao = scr.nextLine(); // Lê a descrição inserida.
                    tarefas.put(id_tarefa, descricao); // Adiciona a tarefa ao mapa.
                    System.out.println("Tarefa incluída..."); // Informa ao usuário que a tarefa foi adicionada.
                    System.out.println("\n");
                    id_tarefa +=1; // Incrementa o ID da tarefa para a próxima adição.
                    break;
                case 2:
                    // Opção para visualizar todas as tarefas.
                    System.out.println("Lista de Tarefas");
                    System.out.println("\n");
                    // Loop para percorrer todas as tarefas armazenadas e exibi-las para o usuário.
                    for (Map.Entry<Integer,String> x : tarefas.entrySet()){
                        System.out.println(x.getKey() +" ==> "+ x.getValue());
                    }
                    System.out.println("\n");
                    break;
                case 3:
                    // Opção para excluir uma tarefa.
                    System.out.print("Digite id da tarefa: "); // Solicita ao usuário o ID da tarefa que deseja excluir.
                    int id = scr.nextInt(); // Lê o ID fornecido.
                    tarefas.remove(id); // Remove a tarefa com o ID fornecido do mapa.
                    System.out.println("Tarefa excluída..."); // Informa ao usuário que a tarefa foi excluída.
                    System.out.println("\n");
                    break;
                case 4:
                    // Opção para encerrar o programa.
                    return; // Encerra a execução do método main e, consequentemente, do programa.
            }
        }
    }
}

