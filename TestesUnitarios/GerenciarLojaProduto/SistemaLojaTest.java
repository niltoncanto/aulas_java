
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SistemaLojaTest {

    @BeforeEach
    void resetarEstatisticas() {
        ProdutoEstatisticas.resetarTotal(); // Garante isolamento dos testes
    }

    @Test
    void testDescricaoProdutoFisico() {
        ProdutoFisico pf = new ProdutoFisico("Monitor", "Periféricos", 899.0, "M123", 3.2);
        String esperado = "Monitor - Periféricos | R$ 899.0 | Código: M123 | Peso: 3.2 kg";
        assertEquals(esperado, pf.getDescricao());
        assertEquals("Monitor-Periféricos", pf.getChave());
    }

    @Test
    void testDescricaoProdutoDigital() {
        ProdutoDigital pd = new ProdutoDigital("Ebook Kotlin", "Livros", 49.9, "E456", 1.5, "EPUB");
        String esperado = "Ebook Kotlin - Livros | R$ 49.9 | Código: E456 | Tamanho: 1.5MB | Formato: EPUB";
        assertEquals(esperado, pd.getDescricao());
        assertEquals("Ebook Kotlin-Livros", pd.getChave());
    }

    @Test
    void testEstatisticaContagemProdutos() {
        new ProdutoFisico("Mouse", "Acessórios", 99.9, "MS1", 0.2);
        new ProdutoDigital("Curso JS", "Cursos", 159.0, "CJ1", 0.8, "MP4");
        assertEquals(2, ProdutoEstatisticas.getTotalProdutos());
    }

    @Test
    void testCadastrarProdutoNaLoja() {
        Loja loja = new Loja();
        Produto p = new ProdutoFisico("Câmera", "Fotografia", 1500.0, "C123", 1.0);
        assertTrue(loja.cadastrarProduto(p));
        assertFalse(loja.cadastrarProduto(p)); // Tentativa de duplicar
        assertEquals(1, loja.getTotalEstoque());
    }

    @Test
    void testRegistrarVendaSucessoEFalha() {
        Loja loja = new Loja();
        Produto p = new ProdutoFisico("Tablet", "Eletrônicos", 1200.0, "T789", 0.7);
        loja.cadastrarProduto(p);

        String msgVenda = loja.registrarVenda("Tablet-Eletrônicos");
        assertTrue(msgVenda.contains("Produto vendido"));
        assertEquals(0, loja.getTotalEstoque());
        assertEquals(1, loja.getTotalVendas());

        // Tentativa de vender novamente o mesmo produto
        String msgFalha = loja.registrarVenda("Tablet-Eletrônicos");
        assertEquals("Produto não encontrado no estoque.", msgFalha);
    }

    @Test
    void testObterEstoqueEVendasFormatados() {
        Loja loja = new Loja();
        assertEquals("Nenhum produto disponível no estoque.", loja.obterEstoque());
        assertEquals("Nenhum produto foi vendido ainda.", loja.obterVendas());

        Produto p1 = new ProdutoFisico("HD", "Armazenamento", 300.0, "HDD1", 0.4);
        Produto p2 = new ProdutoDigital("Aula Python", "Educação", 49.9, "APY1", 1.0, "MP4");
        loja.cadastrarProduto(p1);
        loja.cadastrarProduto(p2);

        String estoque = loja.obterEstoque();
        assertTrue(estoque.contains("HD - Armazenamento"));
        assertTrue(estoque.contains("Aula Python - Educação"));

        loja.registrarVenda("HD-Armazenamento");
        String vendas = loja.obterVendas();
        assertTrue(vendas.contains("HD - Armazenamento"));
    }
}
