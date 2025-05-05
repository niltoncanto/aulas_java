import java.util.Date;

public class Cantor extends Artista {
    private String alcunha;

    public Cantor(String nome, Date dataNascimento, GeneroMusical generoMusical, String alcunha) {
        super(nome, dataNascimento, generoMusical);
        this.alcunha = alcunha;
    }

    @Override
    public String apresentacao() {
        return "Cantor " + nome + ", também conhecido como " + alcunha + ", do gênero " + generoMusical;
    }
}
