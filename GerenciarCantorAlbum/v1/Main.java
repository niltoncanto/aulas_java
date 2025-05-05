import java.util.Date;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        Cantor cantor = new Cantor("Paulo", new GregorianCalendar(1990, 2, 15).getTime(), GeneroMusical.ROCK, "Paulinho");
        Banda banda = new Banda("Os Rockeiros", new GregorianCalendar(1985, 5, 20).getTime(), GeneroMusical.ROCK, 4);

        Album albumCantor = new Album("Meus Hits", 2020, 10);
        Album albumBanda = new Album("Rock Total", 2019, 12);

        cantor.adicionarAlbum(albumCantor);
        banda.adicionarAlbum(albumBanda);

        System.out.println(cantor.apresentacao());
        System.out.println(banda.apresentacao());

        GerenciarArquivo gerenciador = new GerenciarArquivo();
        gerenciador.gravar(cantor, "cantor.dat");
        gerenciador.gravar(banda, "banda.dat");

        Cantor cantorLido = (Cantor) gerenciador.ler("cantor.dat");
        Banda bandaLida = (Banda) gerenciador.ler("banda.dat");

        System.out.println(cantorLido.apresentacao());
        System.out.println(bandaLida.apresentacao());
    }
}
