import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Artista {
    protected String nome;
    protected Date dataNascimento;
    protected GeneroMusical generoMusical;
    private Map<String, Album> albuns = new HashMap<>();

    public Artista(String nome, Date dataNascimento, GeneroMusical generoMusical) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.generoMusical = generoMusical;
    }

    public void adicionarAlbum(Album album) {
        albuns.put(album.getNome(), album);
    }

    public Album obterAlbum(String nome) {
        return albuns.get(nome);
    }

    public abstract String apresentacao();
}
