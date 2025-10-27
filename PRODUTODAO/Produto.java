/**
 * Classe que representa um produto
 */
public class Produto {
    private String nome;
    private double preco;
    private int quantidade;
    
    public Produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }
    
    //Getter para o nome
    public String getNome() {
        return nome;
    }
    // Setter para o nome
    public void setNome(String nome) {
        this.nome = nome;
    }
    //Getter para o preço
    public double getPreco() {
        return preco;
    }
    //Setter para o preço
    public void setPreco(double preco) {
        this.preco = preco;
    }
    // Getter quantidade
    public int getQuantidade() {
        return quantidade;
    }
    //Setter para a quantidade
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    //Método toString sobrescrito para exibir os dados de forma amigável
    @Override
    public String toString() {
        return String.format("Produto: %s | Preço: R$ %.2f | Quantidade: %d", 
                           nome, preco, quantidade);
    }
    //Método para converter o produto em formato de linha para arquivo
    public String toFileFormat() {
        return String.format("%s;%.2f;%d", nome, preco, quantidade);
    }
    
    // Método estático para criar um produto a partir de uma linha do arquivo
    // Retorna um produto criado a partir da linha do arquivo
    public static Produto fromFileFormat(String linha) {
        String[] campos = linha.split(";");
        if (campos.length == 3) {
            String nome = campos[0];
            double preco = Double.parseDouble(campos[1]);
            int quantidade = Integer.parseInt(campos[2]);
            return new Produto(nome, preco, quantidade);
        }
        return null;
    }
}
