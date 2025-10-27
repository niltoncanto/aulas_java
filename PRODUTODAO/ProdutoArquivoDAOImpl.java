import java.io.*;
import java.util.*;

/**
 * Implementação da interface IProdutoDAO que utiliza arquivo de texto para persistência
 */
public class ProdutoArquivoDAOImpl implements IProdutoDAO {
    private static final String ARQUIVO_PRODUTOS = "produtos.txt";
    
    // Método para adicionar um novo produto ao arquivo
    @Override
    public boolean adicionar(Produto produto) {
        try {
            // Verifica se o produto já existe
            if (buscarPorNome(produto.getNome()) != null) {
                System.out.println("Produto com nome '" + produto.getNome() + "' já existe!");
                return false;
            }
            
            // Abre o arquivo em modo append para adicionar no final
            FileWriter writer = new FileWriter(ARQUIVO_PRODUTOS, true);
            PrintWriter printWriter = new PrintWriter(writer);
            
            // Escreve o produto no formato: nome;preco;quantidade
            printWriter.println(produto.toFileFormat());
            
            printWriter.close();
            writer.close();
            System.out.println("Produto '" + produto.getNome() + "' adicionado com sucesso!");
            return true;
            
        } catch (IOException e) {
            System.err.println("Erro ao adicionar produto: " + e.getMessage());
            return false;
        }
    }
    
    // Método para listar todos os produtos do arquivo
    @Override
    public java.util.List<Produto> listarTodos() {
        java.util.List<Produto> produtos = new java.util.ArrayList<Produto>();
        
        try {
            File arquivo = new File(ARQUIVO_PRODUTOS);
            
            // Se o arquivo não existe, retorna lista vazia
            if (!arquivo.exists()) {
                return produtos;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String linha;
            
            // Lê linha por linha do arquivo
            while ((linha = reader.readLine()) != null && !linha.trim().isEmpty()) {
                Produto produto = Produto.fromFileFormat(linha);
                if (produto != null) {
                    produtos.add(produto);
                }
            }
            
            reader.close();
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de produtos: " + e.getMessage());
        }
        
        return produtos;
    }
    
    // Método para buscar um produto pelo nome
    @Override
    public Produto buscarPorNome(String nome) {
        java.util.List<Produto> produtos = listarTodos();
        
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return produto;
            }
        }
        
        return null;
    }
    
    // Método para remover um produto pelo nome
    @Override
    public boolean remover(String nome) {
        java.util.List<Produto> produtos = listarTodos();
        boolean removido = false;
        
        // Remove o produto da lista se encontrado
        java.util.Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto produto = iterator.next();
            if (produto.getNome().equalsIgnoreCase(nome)) {
                iterator.remove();
                removido = true;
                break;
            }
        }
        
        if (removido) {
            // Reescreve o arquivo com os produtos restantes
            try {
                FileWriter writer = new FileWriter(ARQUIVO_PRODUTOS);
                PrintWriter printWriter = new PrintWriter(writer);
                
                for (Produto produto : produtos) {
                    printWriter.println(produto.toFileFormat());
                }
                
                printWriter.close();
                writer.close();
                
                System.out.println("Produto '" + nome + "' removido com sucesso!");
                return true;
                
            } catch (IOException e) {
                System.err.println("Erro ao remover produto: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Produto '" + nome + "' não encontrado!");
            return false;
        }
    }
}
