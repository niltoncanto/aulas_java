import java.util.ArrayList;
import java.util.List;

class Produto {
    private String nome;
    private double preco;
    private int quantidadeEmEstoque;

    public Produto(String nome, double preco, int quantidadeEmEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public void comprar(int quantidade) {
        if (quantidadeEmEstoque >= quantidade) {
            quantidadeEmEstoque -= quantidade;
        } else {
            System.out.println("Quantidade insuficiente em estoque.");
        }
    }

    public void reporEstoque(int quantidade) {
        this.quantidadeEmEstoque += quantidade;
    }

    public void mostrarInfo() {
        System.out.println("Nome: " + nome);
        System.out.println("Preço: " + preco);
        System.out.println("Quantidade em Estoque: " + quantidadeEmEstoque);
    }
}

class Loja {
    private List<Produto> listaDeProdutos = new ArrayList<>();

    public void adicionarProduto(Produto produto) {
        listaDeProdutos.add(produto);
    }

    public void mostrarProdutos() {
        for (Produto produto : listaDeProdutos) {
            produto.mostrarInfo();
            System.out.println("----------");
        }
    }
}

public class MainLoja {
    public static void main(String[] args) {
        Produto p1 = new Produto("Celular", 1500.50, 10);
        Produto p2 = new Produto("Notebook", 3200.00, 5);

        Loja minhaLoja = new Loja();
        minhaLoja.adicionarProduto(p1);
        minhaLoja.adicionarProduto(p2);

        minhaLoja.mostrarProdutos();

        p1.comprar(2);
        p2.reporEstoque(3);

        minhaLoja.mostrarProdutos();
    }
}

