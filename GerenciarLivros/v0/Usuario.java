package v0;
public class Usuario {
    private String nome;
    private int ID;

    public Usuario(String nome, int ID) {
        this.nome = nome;
        this.ID = ID;
    }

    public String fazerReserva(Livro livro) {
        return livro.reservar();
    }

    // Getters e setters omitidos para brevidade
}

