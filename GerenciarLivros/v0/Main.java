package v0;
public class Main {
    public static void main(String[] args) {
        LivroPadrao livro1 = new LivroPadrao("O Senhor dos Anéis", "J.R.R. Tolkien", "123456");
        LivroReferencia livro2 = new LivroReferencia("Enciclopédia", "Vários Autores", "789012", "Ciência");

        Usuario usuario1 = new Usuario("João", 1);

        System.out.println(usuario1.fazerReserva(livro1));
        System.out.println(usuario1.fazerReserva(livro2));
    }
}
