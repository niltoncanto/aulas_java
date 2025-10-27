
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
