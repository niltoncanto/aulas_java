import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Classe responsável por gravar e ler produtos de um arquivo texto
public class ProdutoArquivoDAOImpl implements IProdutoDAO {

    // Nome do arquivo onde os produtos serão armazenados
    private static final String NOME_ARQUIVO = "produtos.txt";

    // Método para adicionar um novo produto ao arquivo
    @Override
    public void adicionarProduto(Produto produto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO, true))) {
            // Concatena os dados do produto separados por ponto e vírgula
            String linha = produto.getNome() + ";" + produto.getPreco() + ";" + produto.getQuantidade();
            
            // Escreve a linha no final do arquivo
            writer.write(linha);
            writer.newLine(); // pula para a próxima linha
        } catch (IOException e) {
            // Trata exceções relacionadas a arquivos
            System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
        }
    }

    // Método para ler todos os produtos do arquivo
    @Override
    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();

        File arquivo = new File(NOME_ARQUIVO);
        // Verifica se o arquivo existe antes de tentar ler
        if (!arquivo.exists()) {
            return produtos; // Retorna lista vazia se o arquivo ainda não foi criado
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;

            // Lê o arquivo linha por linha
            while ((linha = reader.readLine()) != null) {
                // Divide a linha em partes (nome;preco;quantidade)
                String[] partes = linha.split(";");

                if (partes.length == 3) {
                    // Converte os dados e cria um objeto Produto
                    String nome = partes[0];
                    double preco = Double.parseDouble(partes[1]);
                    int quantidade = Integer.parseInt(partes[2]);

                    Produto produto = new Produto(nome, preco, quantidade);
                    produtos.add(produto); // Adiciona à lista
                }
            }
        } catch (IOException e) {
            // Trata falha na leitura do arquivo
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Trata falhas de conversão de texto para número
            System.out.println("Erro ao converter dados do produto: " + e.getMessage());
        }

        return produtos; // Retorna a lista de produtos lida do arquivo
    }
}
