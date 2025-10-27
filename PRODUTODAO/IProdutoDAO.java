/**
 * Interface para operações de acesso a dados de produtos
 */
public interface IProdutoDAO {
    // Método para adicionar um novo produto
    boolean adicionar(Produto produto);
    
    // Método para listar todos os produtos
    java.util.List<Produto> listarTodos();
    
    // Método para buscar um produto pelo nome
    Produto buscarPorNome(String nome);
    
    // Método para remover um produto pelo nome
    boolean remover(String nome);
}
