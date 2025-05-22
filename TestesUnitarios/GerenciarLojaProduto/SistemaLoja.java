import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

interface Produto {
    String getDescricao();
    String getChave();
}

class ProdutoEstatisticas {
    private static int totalProdutos = 0;

    public static void incrementarTotal() {
        totalProdutos++;
    }

    public static int getTotalProdutos() {
        return totalProdutos;
    }

    public static void resetarTotal() {
        totalProdutos = 0;
    }
}

class ProdutoFisico implements Produto {
    private String nome;
    private String categoria;
    private double preco;
    private String codigo;
    private double pesoKg;

    public ProdutoFisico(String nome, String categoria, double preco, String codigo, double pesoKg) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.codigo = codigo;
        this.pesoKg = pesoKg;
        ProdutoEstatisticas.incrementarTotal();
    }

    @Override
    public String getDescricao() {
        return nome + " - " + categoria + " | R$ " + preco + " | Código: " + codigo + " | Peso: " + pesoKg + " kg";
    }

    @Override
    public String toString() {
        return getDescricao();
    }

    @Override
    public String getChave() {
        return nome + "-" + categoria;
    }

    // Getters adicionais, se necessário
}

class ProdutoDigital implements Produto {
    private String nome;
    private String categoria;
    private double preco;
    private String codigo;
    private double tamanhoArquivoMB;
    private String formato;

    public ProdutoDigital(String nome, String categoria, double preco, String codigo, double tamanhoArquivoMB, String formato) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.codigo = codigo;
        this.tamanhoArquivoMB = tamanhoArquivoMB;
        this.formato = formato;
        ProdutoEstatisticas.incrementarTotal();
    }

    @Override
    public String getDescricao() {
        return nome + " - " + categoria + " | R$ " + preco + " | Código: " + codigo +
               " | Tamanho: " + tamanhoArquivoMB + "MB | Formato: " + formato;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

    @Override
    public String getChave() {
        return nome + "-" + categoria;
    }

    // Getters adicionais, se necessário
}

// Classe Loja
class Loja {
    private Map<String, Produto> produtos;
    private ArrayList<Produto> vendas;

    public Loja() {
        this.produtos = new HashMap<>();
        this.vendas = new ArrayList<>();
    }

    public boolean cadastrarProduto(Produto produto) {
        String chave = produto.getChave();
        if (produtos.containsKey(chave)) {
            return false; // Produto já cadastrado
        }
        produtos.put(chave, produto);
        return true;
    }

    public String registrarVenda(String chave) {
        if (produtos.containsKey(chave)) {
            Produto produtoVendido = produtos.remove(chave);
            vendas.add(produtoVendido);
            return "Produto vendido: " + produtoVendido;
        } else {
            return "Produto não encontrado no estoque.";
        }
    }

    public String obterEstoque() {
        if (produtos.isEmpty()) {
            return "Nenhum produto disponível no estoque.";
        }
        StringBuilder sb = new StringBuilder("Estoque de Produtos:\n");
        for (Produto p : produtos.values()) {
            sb.append(p).append("\n");
        }
        return sb.toString().trim();
    }

    public String obterVendas() {
        if (vendas.isEmpty()) {
            return "Nenhum produto foi vendido ainda.";
        }
        StringBuilder sb = new StringBuilder("Produtos Vendidos:\n");
        for (Produto p : vendas) {
            sb.append(p).append("\n");
        }
        return sb.toString().trim();
    }

    public boolean estaNoEstoque(String chave) {
        return produtos.containsKey(chave);
    }

    public int getTotalEstoque() {
        return produtos.size();
    }

    public int getTotalVendas() {
        return vendas.size();
    }
}

public class SistemaLoja {

    public static void main(String[] args) {
        // Instancia a loja
        Loja loja = new Loja();

        // Cria produtos
        Produto produto1 = new ProdutoFisico("Notebook", "Eletrônicos", 4500.0, "123456", 2.5);
        Produto produto2 = new ProdutoDigital("Curso de Java", "Educação", 199.99, "654321", 1.2, "PDF");

        // Cadastra os produtos
        boolean cadastrado1 = loja.cadastrarProduto(produto1);
        boolean cadastrado2 = loja.cadastrarProduto(produto2);

        System.out.println(cadastrado1 ? "Produto 1 cadastrado com sucesso!" : "Produto 1 já estava cadastrado.");
        System.out.println(cadastrado2 ? "Produto 2 cadastrado com sucesso!" : "Produto 2 já estava cadastrado.");

        // Exibe estoque antes da venda
        System.out.println("\n" + loja.obterEstoque());

        // Realiza uma venda
        String resultadoVenda = loja.registrarVenda("Notebook-Eletrônicos");
        System.out.println("\n" + resultadoVenda);

        // Exibe estoque e vendas após a venda
        System.out.println("\n" + loja.obterEstoque());
        System.out.println("\n" + loja.obterVendas());
    }
}
