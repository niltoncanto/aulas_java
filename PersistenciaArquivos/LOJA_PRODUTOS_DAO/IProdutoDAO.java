import java.util.List;
// Interface que define as operações básicas de acesso aos dados dos produtos
public interface IProdutoDAO {
    // Método para adicionar um novo produto
    void adicionarProduto(Produto produto);

    // Método para retornar a lista de todos os produtos cadastrados
    List<Produto> listarProdutos();
}

