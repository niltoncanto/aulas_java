import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GerenciadorTarefas {
    public static void main(String[] args){
        int opcao=0;
        int id_tarefa=1;
        String descricao="";

        Map<Integer, String> tarefas = new HashMap<>();
        System.out.println("\n");
        System.out.println("Gerenciador de Tarefas");
        System.out.println("1 - incluir Tarefas");
        System.out.println("2 - visualizar Tarefas");
        System.out.println("3 - excluir Tarefas");
        System.out.println("4 - sair");
        System.out.println("\n");
        while(true){
            System.out.print("Digite sua opção: ");
            Scanner scr = new Scanner(System.in);
            opcao = scr.nextInt();
            scr.nextLine();
            switch (opcao){
                case 1:
                    //incluir tarefa
                    System.out.print("Digite a descrição da tarefa: ");
                    descricao = scr.nextLine();
                    tarefas.put(id_tarefa,descricao);
                    System.out.println("Tarefa incluída...");
                    System.out.println("\n");
                    id_tarefa +=1;
                    break;
                case 2:
                    //visualiar tarefas
                    System.out.println("Lista de Tarefas");
                    System.out.println("\n");
                    for (Map.Entry<Integer,String> x : tarefas.entrySet()){
                        System.out.println(x.getKey() +" ==> "+ x.getValue());
                    }
                    System.out.println("\n");
                    break;
                case 3:
                    //excluir tarefas
                    System.out.print("Digite id da tarefa: ");
                    int id = scr.nextInt();
                    tarefas.remove(id);
                    System.out.println("Tarefa excluída...");
                    System.out.println("\n");
                    break;
                case 4:
                    //sair
                    return;
            }
        }
    }
}
