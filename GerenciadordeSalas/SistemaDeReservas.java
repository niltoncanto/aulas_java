import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

class Sala{
    private String nome;
    private Boolean reservada;

    public Sala(String nome){
        this.nome = nome;
        this.reservada = false;
    }

    public String getNome(){
        return this.nome;
    }

    public Boolean isReservada(){
        return this.reservada;
    }

    public void reservar(){
        this.reservada = true;
    }

    public void cancelarReserva(){
        this.reservada = false;
    }

    @Override //sobrescrita do método
    public String toString(){
        return "Sala:"+this.nome+" Reservada:"+(reservada ? "reservada" : "disponível");
    }
}

class GerenciadorDeReservas{
    private Map<String,Sala> salas = new LinkedHashMap<>();

    public GerenciadorDeReservas(){
        this.salas.put("Sala A", new Sala("Sala A"));
        this.salas.put("Sala B", new Sala("Sala B"));
        this.salas.put("Sala C", new Sala("Sala C"));
        this.salas.put("Sala D", new Sala("Sala D"));
        this.salas.put("Sala E", new Sala("Sala E"));
    }

    public void listarSala(){
        for(Sala sala: salas.values()){
            System.out.println(sala);
        }
    }

    public Boolean reservarSala(String nome){
        Sala sala = salas.get(nome);

        if(sala!=null && sala.isReservada()==false){
            sala.reservar();
            return true;
        }
        return false;

    }

    public Boolean cancelarReserva(String nome){
        Sala sala = salas.get(nome);

        if(sala!=null && sala.isReservada()){
            sala.cancelarReserva();
            return true;
        }
        return false;

    }
}


public class SistemaDeReservas {
    public static void main(String[] args){
        GerenciadorDeReservas gerenciador = new GerenciadorDeReservas();
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do{
            System.out.println("Menu de Opções");
            System.out.println("1 - Listar salas");
            System.out.println("2 - Reservar");
            System.out.println("3 - Cancelar");
            System.out.println("4 - Finalizar");
            System.out.print("opção: ");
            opcao = scanner.nextInt();
            String nomeSala;
            scanner.nextLine(); // Limpa buffer
            switch (opcao) {
                case 1: 
                    gerenciador.listarSala();
                    break;
                case 2: 
                    System.out.print("Entre com o nome da sala: ");
                    nomeSala = scanner.nextLine();
                    System.out.println(gerenciador.reservarSala(nomeSala)?"Sala reservada!":"Sala não encontrada ou já reservada");
                    break;
                case 3: 
                    System.out.print("Entre com o nome da sala: ");
                    nomeSala = scanner.nextLine();
                    System.out.println(gerenciador.cancelarReserva(nomeSala)?"Reserva cancelada!":"Sala não encontrada ou sem reserva");
                    break;
            }       
        }while(opcao!=4);
    }
}