import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Classe abstrata Produto
abstract class Produto {
    protected String nome;
    protected String categoria;
    protected double preco;
    protected String codigo;
    protected static int totalProdutos = 0;

    public Produto(String nome, String categoria, double preco, String codigo) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.codigo = codigo;
        totalProdutos++;
    }

    public static int getTotalProdutos() {
        return totalProdutos;
    }

    // Método abstrato que será implementado pelas subclasses
    public abstract String getDescricao();

    @Override
    public String toString() {
        return getDescricao();
    }
}

// Classe ProdutoFisico que herda de Produto
class ProdutoFisico extends Produto {
    private double pesoKg;

    public ProdutoFisico(String nome, String categoria, double preco, String codigo, double pesoKg) {
        super(nome, categoria, preco, codigo);
        this.pesoKg = pesoKg;
    }

    @Override
    public String getDescricao() {
        return nome + " - " + categoria + " | R$ " + preco + " | Código: " + codigo + " | Peso: " + pesoKg + " kg";
    }
}

// Classe ProdutoDigital que herda de Produto
class ProdutoDigital extends Produto {
    private double tamanhoArquivoMB;
    private String formato;

    public ProdutoDigital(String nome, String categoria, double preco, String codigo, double tamanhoArquivoMB, String formato) {
        super(nome, categoria, preco, codigo);
        this.tamanhoArquivoMB = tamanhoArquivoMB;
        this.formato = formato;
    }

    @Override
    public String getDescricao() {
        return nome + " - " + categoria + " | R$ " + preco + " | Código: " + codigo + " | Tamanho: " + tamanhoArquivoMB + "MB | Formato: " + formato;
    }
}

// Classe Loja
class Loja {
    private Map<String, Produto> produtos;
    private ArrayList<Produto> vendas;

    public Loja() {
        this.produtos = new HashMap<>();
        this.vendas = new ArrayList<>();
    }

    public void cadastrarProduto(Produto produto) {
        String chave = produto.nome + "-" + produto.categoria;
        produtos.put(chave, produto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    public void registrarVenda(String chave) {
        if (produtos.containsKey(chave)) {
            Produto produtoVendido = produtos.remove(chave);
            vendas.add(produtoVendido);
            System.out.println("Produto vendido: " + produtoVendido);
        } else {
            System.out.println("Produto não encontrado no estoque.");
        }
    }

    public void exibirEstoque() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto disponível no estoque.");
        } else {
            System.out.println("Estoque de Produtos:");
            for (Produto p : produtos.values()) {
                System.out.println(p);
            }
        }
    }

    public void exibirVendas() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhum produto foi vendido ainda.");
        } else {
            System.out.println("Produtos Vendidos:");
            for (Produto p : vendas) {
                System.out.println(p);
            }
        }
    }
}

// Classe principal
public class SistemaLoja {
    public static void main(String[] args) {
        Loja loja = new Loja();

        // Adicionando alguns produtos para teste
        Produto produto1 = new ProdutoFisico("Notebook", "Eletrônicos", 4500.0, "123456", 2.5);
        Produto produto2 = new ProdutoDigital("Curso de Java", "Educação", 199.99, "654321", 1.2, "PDF");

        loja.cadastrarProduto(produto1);
        loja.cadastrarProduto(produto2);

        // Exibir estoque antes da venda
        loja.exibirEstoque();

        // Registrar uma venda
        loja.registrarVenda("Notebook-Eletrônicos");

        // Exibir estoque e vendas após a venda
        loja.exibirEstoque();
        loja.exibirVendas();
    }
}
