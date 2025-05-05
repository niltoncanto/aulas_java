import java.util.ArrayList;

public class Participante {
    private String nome;
    private String email;
    public ArrayList<Ingresso> ingressosComprados;

    // Construtor
    public Participante(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.ingressosComprados = new ArrayList<>();
    }

    public String getNome(){
        return this.nome;
    }

    public String getEmail(){
        return this.email;
    }

    public void mostrarIngressos() {
        System.out.println("Ingressos do participante " + this.nome + ":");
        for (Ingresso ingresso : this.ingressosComprados) {
            if (ingresso.getStatusIngresso()) { // verifica se o ingresso ainda é válido
                System.out.println("- Evento: " + ingresso.getEvento().getNomeEvento() + ", Data: " + ingresso.getEvento().getDataEvento() + ", Local: " + ingresso.getEvento().getLocalEvento());
            }
        }
    }

}

