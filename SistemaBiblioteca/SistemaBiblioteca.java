import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Classe abstrata Livro
abstract class Livro {
    protected String titulo;
    protected String autor;
    protected int ano;
    protected String isbn;
    protected static int totalLivros = 0;

    public Livro(String titulo, String autor, int ano, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.isbn = isbn;
        totalLivros++;
    }

    public static int getTotalLivros() {
        return totalLivros;
    }

    // Método abstrato que será implementado pelas subclasses
    public abstract String getDescricao();

    @Override
    public String toString() {
        return getDescricao();
    }
}

// Classe LivroFisico que herda de Livro
class LivroFisico extends Livro {
    private int numeroPaginas;

    public LivroFisico(String titulo, String autor, int ano, String isbn, int numeroPaginas) {
        super(titulo, autor, ano, isbn);
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public String getDescricao() {
        return titulo + " - " + autor + " (" + ano + ") | ISBN: " + isbn + " | Páginas: " + numeroPaginas;
    }
}

// Classe LivroDigital que herda de Livro
class LivroDigital extends Livro {
    private double tamanhoArquivoMB;
    private String formato;

    public LivroDigital(String titulo, String autor, int ano, String isbn, double tamanhoArquivoMB, String formato) {
        super(titulo, autor, ano, isbn);
        this.tamanhoArquivoMB = tamanhoArquivoMB;
        this.formato = formato;
    }

    @Override
    public String getDescricao() {
        return titulo + " - " + autor + " (" + ano + ") | ISBN: " + isbn + " | Tamanho: " + tamanhoArquivoMB + "MB | Formato: " + formato;
    }
}

// Classe Biblioteca
class Biblioteca {
    private Map<String, Livro> livros;
    private ArrayList<Livro> emprestimos;

    public Biblioteca() {
        this.livros = new HashMap<>();
        this.emprestimos = new ArrayList<>();
    }

    public void cadastrarLivro(Livro livro) {
        String chave = livro.titulo + "-" + livro.autor + "-" + livro.ano;
        livros.put(chave, livro);
        System.out.println("Livro cadastrado com sucesso!");
    }

    public void registrarEmprestimo(String chave) {
        if (livros.containsKey(chave)) {
            Livro livroEmprestado = livros.remove(chave);
            emprestimos.add(livroEmprestado);
            System.out.println("Livro emprestado: " + livroEmprestado);
        } else {
            System.out.println("Livro não encontrado no acervo.");
        }
    }

    public void exibirAcervo() {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro disponível no acervo.");
        } else {
            System.out.println("Acervo de Livros:");
            for (Livro l : livros.values()) {
                System.out.println(l);
            }
        }
    }

    public void exibirEmprestimos() {
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum livro foi emprestado ainda.");
        } else {
            System.out.println("Livros Emprestados:");
            for (Livro l : emprestimos) {
                System.out.println(l);
            }
        }
    }
}

// Classe principal
public class SistemaBiblioteca {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Adicionando alguns livros para teste
        Livro livro1 = new LivroFisico("1984", "George Orwell", 1949, "1234567890", 328);
        Livro livro2 = new LivroDigital("O Senhor dos Anéis", "J.R.R. Tolkien", 1954, "0987654321", 5.6, "EPUB");

        biblioteca.cadastrarLivro(livro1);
        biblioteca.cadastrarLivro(livro2);

        // Exibir acervo antes do empréstimo
        biblioteca.exibirAcervo();

        // Registrar um empréstimo
        biblioteca.registrarEmprestimo("1984-George Orwell-1949");

        // Exibir acervo e empréstimos após o empréstimo
        biblioteca.exibirAcervo();
        biblioteca.exibirEmprestimos();
    }
}
