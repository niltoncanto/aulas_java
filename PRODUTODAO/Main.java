/**
 * Classe de teste para verificar se todas as classes compilam corretamente
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Testando compilação das classes...");
        
        // Testa criação de produto
        Produto produto = new Produto("Teste", 10.50, 5);
        System.out.println("Produto criado: " + produto.toString());
        
        // Testa DAO
        IProdutoDAO dao = new ProdutoArquivoDAOImpl();
        System.out.println("DAO criado com sucesso!");
        
        // Testa conversão para formato de arquivo
        String formatoArquivo = produto.toFileFormat();
        System.out.println("Formato arquivo: " + formatoArquivo);
        
        // Testa criação a partir do formato de arquivo
        Produto produto2 = Produto.fromFileFormat(formatoArquivo);
        System.out.println("Produto recriado: " + produto2.toString());
        
        System.out.println("Todas as classes compilaram com sucesso!");
    }
}
