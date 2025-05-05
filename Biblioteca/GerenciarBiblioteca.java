import java.util.ArrayList;
import java.util.List;

// Classe Livro
class Livro {
    // Atributos privados (Encapsulamento)
    private String titulo;
    private String autor;
    private int anoPublicacao;

    // Construtor da classe Livro
    public Livro(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        setAnoPublicacao(anoPublicacao); // Usa o setter para validar o ano
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
        if (anoPublicacao > 0) { // Valida se o ano é válido
            this.anoPublicacao = anoPublicacao;
        } else {
            System.out.println("Ano de publicação inválido. Mantendo valor anterior.");
        }
    }

    // Método para exibir informações do livro
    public void mostrarInfo() {
        System.out.println("Título: " + titulo + ", Autor: " + autor + ", Ano: " + anoPublicacao);
    }
}

// Classe Biblioteca
class Biblioteca {
    // Atributos privados
    private List<Livro> livros;
    private int capacidade;

    // Construtor da classe Biblioteca
    public Biblioteca(int capacidade) {
        this.capacidade = capacidade;
        this.livros = new ArrayList<>();
    }

    // Getter e Setter para capacidade
    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        if (capacidade > 0) { // Garante que a capacidade seja positiva
            this.capacidade = capacidade;
        } else {
            System.out.println("Capacidade inválida.");
        }
    }

    // Método para adicionar um livro na biblioteca
    public void adicionarLivro(Livro livro) {
        if (livros.size() < capacidade) {
            livros.add(livro);
            System.out.println("Livro adicionado: " + livro.getTitulo());
        } else {
            System.out.println("A biblioteca atingiu sua capacidade máxima!");
        }
    }

    // Método para remover um livro pelo título
    public void removerLivro(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livros.remove(livro);
                System.out.println("Livro removido: " + titulo);
                return;
            }
        }
        System.out.println("Livro não encontrado: " + titulo);
    }

    // Método para exibir todos os livros cadastrados
    public void mostrarLivros() {
        if (livros.isEmpty()) {
            System.out.println("A biblioteca está vazia.");
        } else {
            System.out.println("Livros na Biblioteca:");
            for (Livro livro : livros) {
                livro.mostrarInfo();
            }
        }
    }
}

// Classe Principal (Main)
public class GerenciarBiblioteca {
    public static void main(String[] args) {
        // Criando uma biblioteca com capacidade para 3 livros
        Biblioteca biblioteca = new Biblioteca(3);

        // Criando alguns livros
        Livro livro1 = new Livro("Dom Casmurro", "Machado de Assis", 1899);
        Livro livro2 = new Livro("1984", "George Orwell", 1949);
        Livro livro3 = new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 1943);
        Livro livro4 = new Livro("A Revolução dos Bichos", "George Orwell", 1945); // Excedendo a capacidade

        // Adicionando os livros à biblioteca
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        biblioteca.adicionarLivro(livro3);
        biblioteca.adicionarLivro(livro4); // Esse não será adicionado

        // Exibindo todos os livros cadastrados
        biblioteca.mostrarLivros();

        // Removendo um livro e mostrando a biblioteca novamente
        biblioteca.removerLivro("1984");
        biblioteca.mostrarLivros();
    }
}

