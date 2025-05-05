import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Gtarefas {
    public static void main(String[] args){
        //variáveis
        //String tarefas[] = new String[1000]; //array de strings
        //ArrayList<String> tarefas = new ArrayList<>(); //lista tamanho flexível
        
        //Mapas lista chave=>valor (id1=valor1, id2=valor2, id3=valor3)
        Map<Integer, String> tarefas = new HashMap<>();
        
        //Incluir tarefas
        tarefas.put(1,"Criar formulário");
        tarefas.put(2,"criar relatorio");
        tarefas.put(3,"resolver bug");
        System.out.println(tarefas);
        System.out.println(tarefas.get(2));

        //remover tarefas
        tarefas.remove(1);
        System.out.println(tarefas);

        //posso utilizar para listar as tarefas.
        for (Map.Entry<Integer, String> x : tarefas.entrySet()) {
            System.out.println(x.getKey() + " = " + x.getValue());
        }
        
        while(true){
            
        }


    }
}
