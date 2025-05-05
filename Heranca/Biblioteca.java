import java.util.ArrayList;
import java.util.List;

// Classe Livro
class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;

    // Construtor
    public Livro(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        setAnoPublicacao(anoPublicacao);
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        if (anoPublicacao > 0) {
            this.anoPublicacao = anoPublicacao;
        } else {
            System.out.println("O ano de publicação deve ser maior que zero.");
        }
    }

    // Método para exibir informações do livro
    public void mostrarInfo() {
        System.out.println("Título: " + titulo + ", Autor: " + autor + ", Ano: " + anoPublicacao);
    }
}

// Classe Biblioteca
class Biblioteca {
    private List<Livro> livros;
    private int capacidade;

    // Construtor
    public Biblioteca(int capacidade) {
        this.capacidade = capacidade > 0 ? capacidade : 10; // Garantindo que a capacidade seja positiva
        this.livros = new ArrayList<>();
    }

    // Getter e Setter para capacidade
    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        if (capacidade > 0) {
            this.capacidade = capacidade;
        } else {
            System.out.println("A capacidade deve ser um valor positivo.");
        }
    }

    // Método para adicionar livro
    public void adicionarLivro(Livro livro) {
        if (livros.size() < capacidade) {
            livros.add(livro);
            System.out.println("Livro adicionado: " + livro.getTitulo());
        } else {
            System.out.println("A biblioteca está cheia! Não é possível adicionar mais livros.");
        }
    }

    // Método para remover livro
    public void removerLivro(Livro livro) {
        if (livros.contains(livro)) {
            livros.remove(livro);
            System.out.println("Livro removido: " + livro.getTitulo());
        } else {
            System.out.println("Livro não encontrado na biblioteca.");
        }
    }

    // Método para exibir os livros cadastrados
    public void mostrarLivros() {
        if (livros.isEmpty()) {
            System.out.println("A biblioteca está vazia.");
        } else {
            System.out.println("Livros na biblioteca:");
            for (Livro livro : livros) {
                livro.mostrarInfo();
            }
        }
    }
}

// Classe principal para testar o sistema
public class Biblioteca {
    public static void main(String[] args) {
        // Criando biblioteca com capacidade 3
        Biblioteca biblioteca = new Biblioteca(3);

        // Criando livros
        Livro livro1 = new Livro("Dom Casmurro", "Machado de Assis", 1899);
        Livro livro2 = new Livro("1984", "George Orwell", 1949);
        Livro livro3 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954);
        Livro livro4 = new Livro("A Revolução dos Bichos", "George Orwell", 1945);

        // Adicionando livros à biblioteca
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        biblioteca.adicionarLivro(livro3);
        biblioteca.adicionarLivro(livro4); // Deve exibir mensagem de biblioteca cheia

        // Exibindo os livros cadastrados
        biblioteca.mostrarLivros();

        // Removendo um livro e exibindo a biblioteca novamente
        biblioteca.removerLivro(livro2);
        biblioteca.mostrarLivros();
    }
}

