class Livro {
    //atributos
    private String autor;
    private String titulo;
    private String isbn;
    private boolean emprestado;

    //construtor da classe
    public Livro(String autor, String titulo, String isbn){
        this.autor = autor;
        this.titulo = titulo;
        this. isbn = isbn;
        this.emprestado = false; //livro é criado disponível
    }
    //metodos getters
    public String getAutor(){
        return autor;
    }
    public String getTitulo(){
        return titulo;
    }
    public String getIsnb(){
        return isbn;
    }

    //métodos setters
    public void setAutor(String autor){
        this.autor = autor;
    }    
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    //método para emprestar
    public boolean emprestar(){
        if(!emprestado){
            emprestado = true;
            return true; //emprestimo realizado
        }else{
            return false; // livro não disponível
        }    
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
