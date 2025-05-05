// Importa classes utilitárias para listas e mapas
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Classe abstrata Veiculo, que servirá como base para outros tipos de veículos
abstract class Veiculo {
    // Atributos comuns a todos os veículos
    protected String marca;
    protected String modelo;
    protected Integer ano;
    protected Double preco;

    // Atributo de classe que conta o total de veículos criados
    protected static Integer totalVeiculos = 0;

    // Construtor da classe base, que inicializa os atributos e incrementa o contador
    public Veiculo(String marca, String modelo, Integer ano, Double preco) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        totalVeiculos++;
    }

    // Método estático que retorna o total de veículos instanciados
    public static Integer getTotalVeiculos() {
        return totalVeiculos;
    }

    // Método abstrato que será implementado pelas subclasses para fornecer uma descrição personalizada
    public abstract String getDescricao();

    // Sobrescrita do método toString para retornar a descrição do veículo
    @Override
    public String toString() {
        return getDescricao();
    }
}

// Subclasse de Veiculo representando veículos novos
class VeiculoNovo extends Veiculo {
    // Atributo específico de veículos novos: garantia
    private Integer garantiaAnos;

    // Construtor que chama o construtor da superclasse e inicializa a garantia
    public VeiculoNovo(String marca, String modelo, Integer ano, Double preco, Integer garantiaAnos) {
        super(marca, modelo, ano, preco);
        this.garantiaAnos = garantiaAnos;
    }

    // Implementação do método abstrato com informações específicas de veículos novos
    @Override
    public String getDescricao() {
        return marca + " " + modelo + " (" + ano + ") - R$ " + preco + " | Garantia: " + garantiaAnos + " anos";
    }
}

// Subclasse de Veiculo representando veículos usados
class VeiculoUsado extends Veiculo {
    // Atributos específicos de veículos usados
    private Integer quilometragem;
    private boolean unicoDono;

    // Construtor que inicializa atributos da superclasse e os específicos
    public VeiculoUsado(String marca, String modelo, Integer ano, Double preco, Integer quilometragem, boolean unicoDono) {
        super(marca, modelo, ano, preco);
        this.quilometragem = quilometragem;
        this.unicoDono = unicoDono;
    }

    // Implementação do método abstrato com informações específicas de veículos usados
    @Override
    public String getDescricao() {
        return marca + " " + 
               modelo + " (" + ano + ") - R$ " + 
               preco + " | Km: " + 
               quilometragem + " | Único dono: " + 
               (unicoDono ? "Sim" : "Não");
    }
}

// Classe que representa a Concessionária, com controle de estoque e vendas
class Concessionaria {
    // Mapa para armazenar veículos no estoque com uma chave única
    private Map<String, Veiculo> veiculos;

    // Lista para armazenar veículos que já foram vendidos
    private ArrayList<Veiculo> vendas;

    // Construtor que inicializa o mapa de veículos e a lista de vendas
    public Concessionaria() {
        this.veiculos = new HashMap<>();
        this.vendas = new ArrayList<>();
    }

    // Método para cadastrar um novo veículo no estoque
    public void cadastrarVeiculo(Veiculo veiculo) {
        // Cria uma chave única a partir da marca, modelo e ano
        String chave = (veiculo.marca + "-" + veiculo.modelo + "-" + veiculo.ano).toLowerCase();
        veiculos.put(chave, veiculo); // Adiciona ao mapa de estoque
        System.out.println("Veículo cadastrado com sucesso!");
    }

    // Método para registrar uma venda a partir da chave do veículo
    public void registrarVenda(String chave) {
        if (veiculos.containsKey(chave)) {
            // Remove do estoque e adiciona à lista de vendas
            Veiculo veiculoVendido = veiculos.remove(chave);
            vendas.add(veiculoVendido);
            System.out.println("Veículo vendido: " + veiculoVendido);
        } else {
            System.out.println("Veículo não encontrado no estoque.");
        }
    }

    // Método para exibir todos os veículos disponíveis no estoque
    public void exibirEstoque() {
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo disponível no estoque.");
        } else {
            System.out.println("Estoque de Veículos:");
            for (Veiculo v : veiculos.values()) {
                System.out.println(v);
            }
        }
    }

    // Método para exibir a lista de veículos vendidos
    public void exibirVendas() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhum veículo foi vendido ainda.");
        } else {
            System.out.println("Veículos Vendidos:");
            for (Veiculo v : vendas) {
                System.out.println(v);
            }
        }
    }
}

// Classe principal com o método main para testar o sistema
public class SistemaConcessionaria {
    public static void main(String[] args) {
        // Cria uma nova concessionária
        Concessionaria concessionaria = new Concessionaria();

        // Cria dois veículos de teste (um novo e um usado)
        Veiculo carro1 = new VeiculoNovo("Toyota", "Corolla", 2023, 150000.0, 3);
        Veiculo carro2 = new VeiculoUsado("Honda", "Civic", 2018, 90000.0, 75000, true);

        // Cadastra os veículos no estoque da concessionária
        concessionaria.cadastrarVeiculo(carro1);
        concessionaria.cadastrarVeiculo(carro2);

        // Exibe o estoque antes da venda
        concessionaria.exibirEstoque();

        // Registra a venda de um dos veículos (com base na chave)
        concessionaria.registrarVenda("toyota-corolla-2023");

        // Exibe o estoque e as vendas após a venda
        concessionaria.exibirEstoque();
        concessionaria.exibirVendas();
    }
}
