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

