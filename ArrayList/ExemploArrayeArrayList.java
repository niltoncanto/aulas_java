/*
Comparação: Array vs. ArrayList

Característica	        Array	                    ArrayList
Tamanho	                Fixo	                    Dinâmico
Inserção e Remoção	    Difícil                     Fácil (add(), remove())
Tipos Aceitos	        primitivos e objetos	    Apenas objetos
Desempenho	            Rápido	                    Mais flexível, mas ligeiramente mais lento
*/

import java.util.ArrayList;
import java.util.Arrays;
public class ExemploArrayeArrayList {
    public static void main(String[] args){
        ArrayList<String> carros = new ArrayList<>(); //instanciação do objeto ArrayList
        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("João", "Pedro")); //instanciação e inicialização do objeto ArrayList
        String[] frutas = {"Maçã", "Banana", "Laranja", "Uva"}; //declarando um array e atribuindo valores
        float[]  valores = new float[4];                        //declarando um array de floats sem atribuição
        
        //adicionando elementos ao ArrayList carros
        carros.add("Fiat 147");
        carros.add("Monza");
        carros.add("Chevet");

        valores[0] = 1.2f;
        valores[1] = 2.2f;

        System.out.println(carros); //impressão do objeto ArrayList
        System.out.println(nomes); //impressão do objeto ArrayList

        for(String carro : carros ){   //iterando sobre o objeto ArrayList carros
            System.out.println(carro);
        }

        for(String nome : nomes ){   // iterando sobre o objeto ArrayList nomes
            System.out.println(nome);
        }

        for(String fruta : frutas ){   // iterando sobre o array frutas
            System.out.println(fruta);
        }

        for(float valor : valores ){   // iterando sobre o array valores
            System.out.println(valor);
        }

    }
    
}
