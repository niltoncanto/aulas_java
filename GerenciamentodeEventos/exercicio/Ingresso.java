import java.time.LocalDateTime;

public class Ingresso {
    private Participante participante;
    private Evento evento;
    private LocalDateTime dataCompra;
    private boolean statusIngresso;

    // Construtor
    public Ingresso(Participante participante, Evento evento) {
        this.participante = participante;
        this.evento = evento;
        this.dataCompra = LocalDateTime.now();
        this.statusIngresso = true;

        // Adiciona o ingresso à lista de inscritos do evento
        evento.incluiInscritoEvento(this);
        // Adiciona o ingresso à lista de ingressos do participante
        participante.ingressosComprados.add(this);
    }

    public void setStatusIngresso(boolean status) {
        this.statusIngresso = status;
    }

    public boolean getStatusIngresso() {
        return this.statusIngresso;
    }

    public Evento getEvento(){
        return this.evento;
    }

    public LocalDateTime getDataCompra(){
        return this.dataCompra;
    }

    public Participante getParticipante(){
        return this.participante;
    }
}
