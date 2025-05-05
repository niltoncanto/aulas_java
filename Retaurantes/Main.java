import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

// Enum para os tipos de FastFood
enum TipoFastFood {
    HAMBURGUER, PIZZA, SANDUICHE;
}


// Classe abstrata Restaurante
abstract class Restaurante {
    protected String nome;
    protected Map<Date, Integer> avaliacoes = new HashMap<>();

    public Restaurante(String nome) {
        this.nome = nome;
    }

    public void adicionarAvaliacao(Date data, int nota) {
        avaliacoes.put(data, nota);
    }

    public String getNome() {
        return nome;
    }

    // Método abstrato para calcular a média
    public abstract double calcularMedia();
}

// Subclasse FastFood
class FastFood extends Restaurante {
    private TipoFastFood tipo;

    public FastFood(String nome, TipoFastFood tipo) {
        super(nome);
        this.tipo = tipo;
    }

    @Override
    public double calcularMedia() {
        return avaliacoes.values().stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }
}

// Subclasse FineDining
class FineDining extends Restaurante {
    protected int estrelasMichelin;

    public FineDining(String nome, int estrelasMichelin) {
        super(nome);
        this.estrelasMichelin = estrelasMichelin;
    }

    @Override
    public double calcularMedia() {
        double media = avaliacoes.values().stream().mapToInt(Integer::intValue).average().orElse(0.0);
        return media + (0.5 * estrelasMichelin);
    }
}

// Classe para gerenciar arquivos
class GerenciarArquivo {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public void gravar(Restaurante restaurante) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(restaurante.getNome() + ".txt"))) {
            writer.write(restaurante.getNome() + "\n");
            for (Map.Entry<Date, Integer> entry : restaurante.avaliacoes.entrySet()) {
                writer.write(sdf.format(entry.getKey()) + " - " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao gravar arquivo: " + e.getMessage());
        }
    }

    public Restaurante ler(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo + ".txt"))) {
            String nome = reader.readLine();
            Restaurante restaurante = new FastFood(nome, TipoFastFood.HAMBURGUER); // Simplificação para o exemplo
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(" - ");
                Date data = sdf.parse(partes[0]);
                int nota = Integer.parseInt(partes[1]);
                restaurante.adicionarAvaliacao(data, nota);
            }
            return restaurante;
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return null;
        }
    }
}

// ...


// Classe principal para testes
public class Main {
    public static void main(String[] args) {
        // Criando restaurantes
        FastFood mcDonalds = new FastFood("McDonalds", TipoFastFood.HAMBURGUER);
        mcDonalds.adicionarAvaliacao(new Date(), 5);

        FineDining laBelle = new FineDining("La Belle", 3);
        laBelle.adicionarAvaliacao(new Date(), 4);

        // Gravando e lendo do arquivo
        GerenciarArquivo gerenciador = new GerenciarArquivo();
        gerenciador.gravar(mcDonalds);
        Restaurante restauranteLido = gerenciador.ler("McDonalds");

        // Mostrando resultados
        System.out.println("Avaliação média do " + mcDonalds.getNome() + ": " + mcDonalds.calcularMedia());
        System.out.println("Avaliação média do " + laBelle.getNome() + ": " + laBelle.calcularMedia());
        System.out.println("Dados do restaurante lido do arquivo: " + restauranteLido.getNome());
    }
}
