import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GGTarefas {
    public static void main(String[] args){
        //String tarefas[] = new String[1000]; //array tem tamanho fixo!!!
        //ArrayList<String> tarefas = new ArrayList<>(); //tamanho variável
        //Mapas (chave1=valor1, chave2=valor2, chave3=valor3) 
        Map<Integer,String> tarefas = new HashMap<>(); //declaração da estrutura Map
        tarefas.put(1,"Criar formulario");
        tarefas.put(2,"Corrigir Bug");
        tarefas.put(3,"Enviar relatorio");

        tarefas.remove(1); //remove o item com a chave = 1

        //itera sobre uma estrutura Map
        for (Map.Entry<Integer,String> t: tarefas.entrySet()) {
            System.out.println(t.getKey()+ " = " +t.getValue());
        }

        
    }
}
