// Classe que representa um Produto da loja
public class Produto {
    private String nome;        // Atributo que representa o nome do produto
    private double preco;       // Atributo que representa o preço do produto
    private int quantidade;    // Atributo que representa a quantidade disponível do produto

    // Construtor que inicializa os atributos com os valores fornecidos
    public Produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Método getter para o nome do produto
    public String getNome() {
        return nome;
    }

    // Método setter para o nome do produto
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método getter para o preço do produto
    public double getPreco() {
        return preco;
    }

    // Método setter para o preço do produto
    public void setPreco(double preco) {
        this.preco = preco;
    }

    // Método getter para a quantidade do produto
    public int getQuantidade() {
        return quantidade;
    }

    // Método setter para a quantidade do produto
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    @Override  // Método sobrescrito para retornar os dados do produto em forma de string
    public String toString() {
        return "Produto: " + nome +
               " | Preço: R$ " + preco +
               " | Quantidade: " + quantidade;
    }
}
