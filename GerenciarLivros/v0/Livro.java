package v0;

public abstract class Livro {
    protected String titulo;
    protected String autor;
    protected String ISBN;
    public Status status;

    public enum Status {
        DISPONIVEL, RESERVADO
    }

    public Livro(String titulo, String autor, String ISBN) {
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
        this.status = Status.DISPONIVEL;
    }

    public abstract String reservar();

    public void devolver() {
        this.status = Status.DISPONIVEL;
    }

}
