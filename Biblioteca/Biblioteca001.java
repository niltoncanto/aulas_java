
import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa a entidade Livro.
 * Demonstra conceitos de Encapsulamento e Estado de Objeto.
 */
class Livro {
    // Atributos privados: só podem ser acessados/alterados por métodos da própria classe
    private String titulo;
    private String autor;
    private String isbn;
    private boolean emprestado; // Define o estado atual do livro (disponível ou não)

    // Construtor: Inicializa o objeto quando damos um 'new Livro(...)'
    public Livro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.emprestado = false; // Todo livro novo começa como disponível
    }

    // Método de Regra de Negócio: Tenta mudar o estado para emprestado
    public boolean emprestar() {
        if (!emprestado) { // Se NÃO estiver emprestado...
            this.emprestado = true; // ...muda o estado para ocupado
            return true; // Sucesso
        } else {
            return false; // Falha: livro já está com alguém
        }
    }

    // Método de Regra de Negócio: Retorna o livro para a biblioteca
    public boolean devolver() {
        if (emprestado) { // Só posso devolver o que foi emprestado
            this.emprestado = false;
            return true;
        } else {
            return false;
        }
    }

    // Método Getter: Permite que outros vejam o estado sem alterar o atributo diretamente
    public boolean isEmprestado() {
        return emprestado;
    }

    // Sobrescrita do toString: Define como o livro aparece ao ser impresso no console
    @Override
    public String toString() {
        return "Autor: " + autor + " | Título: " + titulo + " | ISBN: " + isbn;
    }
}

/**
 * Classe Usuario: Demonstra Composição (um usuário possui uma lista de objetos Livro)
 */
class Usuario {
    private String nome;
    private int id;
    private List<Livro> livrosEmprestados; // Coleção de objetos do tipo Livro

    public Usuario(String nome, int id) {
        this.nome = nome;
        this.id = id;
        // Inicializa a lista como um ArrayList vazio para evitar NullPointerException
        this.livrosEmprestados = new ArrayList<>();
    }

    // Interação entre Objetos: O usuário recebe um objeto Livro como parâmetro
    public boolean emprestarLivro(Livro livro) {
        // Delegação: O usuário pede ao objeto livro para mudar seu próprio estado
        if (livro.emprestar()) {
            livrosEmprestados.add(livro); // Se o livro aceitar, adiciona na lista do usuário
            System.out.println("Livro [" + livro + "] emprestado com sucesso!");
            return true;
        } else {
            System.out.println("O livro já está ocupado!");
            return false;
        }
    }

    public boolean devolverLivro(Livro livro) {
        // Verificação de segurança: o livro está na lista DESTE usuário?
        if (livrosEmprestados.contains(livro)) {
            livro.devolver(); // Avisa o objeto livro que ele está livre agora
            livrosEmprestados.remove(livro); // Remove da lista do usuário
            return true;
        } else {
            System.out.println("Este usuário não está com este livro!");
            return false;
        }
    }

    public void listarLivrosEmprestados() {
        if (livrosEmprestados.isEmpty()) {
            System.out.println("O usuário " + nome + " não possui livros.");
        } else {
            System.out.println("Livros com " + nome + ":");
            // Loop For-Each: percorre cada objeto Livro dentro da lista
            for (Livro livro : livrosEmprestados) {
                System.out.println(" -> " + livro);
            }
        }
    }
}

/**
 * Classe Principal: Onde o sistema ganha vida (Ponto de entrada)
 */
public class Biblioteca {
    public static void main(String[] args) {
        System.out.println("*** Sistema de Biblioteca Ativado ***\n");

        // 1. Criando instâncias (Objetos)
        Livro livro1 = new Livro("Dom Casmurro", "Machado de Assis", "12345");
        Livro livro2 = new Livro("1984", "George Orwell", "55555");
        Usuario usuario1 = new Usuario("Eduardo", 1000);

        // 2. Testando fluxo de sucesso
        usuario1.emprestarLivro(livro1);
        usuario1.emprestarLivro(livro2);

        // 3. Exibindo resultados
        usuario1.listarLivrosEmprestados();

        // 4. Testando devolução
        usuario1.devolverLivro(livro1);
        System.out.println("\n--- Após devolução ---");
        usuario1.listarLivrosEmprestados();
    }
}