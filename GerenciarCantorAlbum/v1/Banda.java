import java.util.Date;

public class Banda extends Artista {
    private int numeroMembros;

    public Banda(String nome, Date dataNascimento, GeneroMusical generoMusical, int numeroMembros) {
        super(nome, dataNascimento, generoMusical);
        this.numeroMembros = numeroMembros;
    }

    @Override
    public String apresentacao() {
        return "Banda " + nome + " com " + numeroMembros + " membros, do gênero " + generoMusical;
    }
}
