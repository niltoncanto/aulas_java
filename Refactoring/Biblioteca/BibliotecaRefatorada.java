// BibliotecaRefatorada.java

import java.util.ArrayList;

class Livro {
    private String titulo;

    public Livro(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }
}

class Biblioteca {
    private ArrayList<Livro> livros = new ArrayList<>();

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public void listarLivros() {
        for (Livro livro : livros) {
            System.out.println(livro);
            System.out.println("----------");
        }
    }

    public void buscarLivro(String termo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().contains(termo)) {
                System.out.println("Encontrado: " + livro.getTitulo());
            }
        }
    }
}
