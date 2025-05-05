import java.util.ArrayList;
import java.util.List;

// Classe que representa um Livro na biblioteca
class Livro {
    private String titulo;
    private String autor;
    private String isbn;
    private boolean emprestado; // true = emprestado, false = disponível

    // Construtor
    public Livro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.emprestado = false; // Livro começa disponível
    }

    // Método para emprestar o livro
    public boolean emprestar() {
        if (!emprestado) {
            emprestado = true;
            return true; // Empréstimo bem-sucedido
        }
        return false; // Livro já está emprestado
    }

    // Método para devolver o livro
    public void devolver() {
        emprestado = false;
    }

    // Método para verificar o status do livro
    public boolean isEmprestado() {
        return emprestado;
    }

    // Método para exibir informações do livro
    public String toString() {
        return titulo + " - " + autor + " (ISBN: " + isbn + ") | " + (emprestado ? "Emprestado" : "Disponível");
    }
}

// Classe que representa um Usuário da biblioteca
class Usuario {
    private String nome;
    private int id;
    private List<Livro> livrosEmprestados;

    // Construtor
    public Usuario(String nome, int id) {
        this.nome = nome;
        this.id = id;
        this.livrosEmprestados = new ArrayList<>();
    }

    // Método para emprestar um livro ao usuário
    public boolean emprestarLivro(Livro livro) {
        if (livro.emprestar()) { // Verifica se o livro está disponível
            livrosEmprestados.add(livro);
            System.out.println(nome + " emprestou o livro: " + livro);
            return true;
        } else {
            System.out.println("O livro " + livro + " já está emprestado.");
            return false;
        }
    }

    // Método para devolver um livro
    public boolean devolverLivro(Livro livro) {
        if (livrosEmprestados.contains(livro)) {
            livro.devolver();
            livrosEmprestados.remove(livro);
            System.out.println(nome + " devolveu o livro: " + livro);
            return true;
        } else {
            System.out.println(nome + " não tem este livro emprestado.");
            return false;
        }
    }

    // Método para listar os livros emprestados
    public void listarLivrosEmprestados() {
        if (livrosEmprestados.isEmpty()) {
            System.out.println(nome + " não possui livros emprestados.");
        } else {
            System.out.println("Livros emprestados por " + nome + ":");
            for (Livro livro : livrosEmprestados) {
                System.out.println("- " + livro);
            }
        }
    }
}

// Classe principal para testar o sistema
public class Biblioteca {
    public static void main(String[] args) {
        // Criando alguns livros
        Livro livro1 = new Livro("1984", "George Orwell", "978-0451524935");
        Livro livro2 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "978-0007525546");
        Livro livro3 = new Livro("Dom Casmurro", "Machado de Assis", "978-8508153767");

        // Criando usuários
        Usuario usuario1 = new Usuario("Alice", 1);
        Usuario usuario2 = new Usuario("Bruno", 2);

        // Testando empréstimos
        usuario1.emprestarLivro(livro1); // Alice empresta "1984"
        usuario2.emprestarLivro(livro2); // Bruno empresta "O Senhor dos Anéis"
        usuario1.emprestarLivro(livro2); // Alice tenta emprestar um livro já emprestado

        // Listando livros emprestados
        usuario1.listarLivrosEmprestados();
        usuario2.listarLivrosEmprestados();

        // Testando devolução de livro
        usuario2.devolverLivro(livro2); // Bruno devolve "O Senhor dos Anéis"
        usuario1.emprestarLivro(livro2); // Agora Alice consegue pegar "O Senhor dos Anéis"

        // Listando novamente após a devolução
        usuario1.listarLivrosEmprestados();
        usuario2.listarLivrosEmprestados();
    }
}

