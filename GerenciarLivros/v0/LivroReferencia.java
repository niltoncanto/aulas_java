import Livro;

package v0;
public class LivroReferencia extends Livro {
    private String tema;

    public LivroReferencia(String titulo, String autor, String ISBN, String tema) {
        super(titulo, autor, ISBN);
        this.tema = tema;
    }

    @Override
    public String reservar() {
        return "Livros de referência não podem ser reservados.";
    }

    // Getters e setters omitidos para brevidade
}

