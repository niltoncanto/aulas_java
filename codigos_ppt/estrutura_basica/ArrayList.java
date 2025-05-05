import java.util.ArrayList;
public class Arraylist {
    public static void main(String[] args){
        ArrayList<String> carros = new ArrayList<>();

        //adicionando elementos
        carros.add("Fiat 147");
        carros.add("Monza");
        carros.add("Chevet");

        System.out.println(carros);

        for(String carro : carros ){
            System.out.println(carro);
        }

    }
    
}
