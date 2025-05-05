import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
public class Evento {
    private String nomeEvento;
    private String localEvento;
    private LocalDateTime dataEvento;
    private int capacidadeEvento;
    ArrayList<Ingresso> inscritosEventos;
    
    // Lista estática para guardar todos os eventos
    public static ArrayList<Evento> eventos = new ArrayList<>();

    // Construtor
    public Evento(String nomeEvento, String localEvento, LocalDateTime dataEvento, int capacidadeEvento) {
        this.nomeEvento = nomeEvento;
        this.localEvento = localEvento;
        this.dataEvento = dataEvento;
        this.capacidadeEvento = capacidadeEvento;
        this.inscritosEventos = new ArrayList<>();
        
        // Adiciona o evento criado na lista de eventos
        eventos.add(this);
    }

    // Método para cancelar o evento
    public void cancelarEvento() {
        for (Ingresso ingresso : inscritosEventos) {
            ingresso.setStatusIngresso(false); // desativar o ingresso
        }
        eventos.remove(this);
    }
    public String getNomeEvento(){
        return this.nomeEvento;
    }

    public LocalDateTime getDataEvento(){
        return this.dataEvento;
    }

    public String getLocalEvento(){
        return this.localEvento;
    }

    public void mostrarIngressosEvento() {
        //DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Ingressos para o evento " + this.nomeEvento + ":");
        for (Ingresso ingresso : this.inscritosEventos) {
            if (ingresso.getStatusIngresso()) { // verifica se o ingresso ainda é válido
                String dataFormatada = ingresso.getDataCompra().format(dataFormato);
                System.out.println("- Participante: " + ingresso.getParticipante().getNome() + ", Email: " + ingresso.getParticipante().getEmail() + ", Data da Compra: " + dataFormatada);
            }
        }
    }
}

