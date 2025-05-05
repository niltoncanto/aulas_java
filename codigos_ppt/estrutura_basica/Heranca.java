// Superclasse Veiculo
class Veiculo {
    protected String cor;
    protected String marca;
    // Construtor da superclasse
    public Veiculo(String cor, String marca) {
        this.cor = cor;
        this.marca = marca;
    }
    // Método descrever
    public String descrever() {
        return "Veículo da marca " + marca + " de cor " + cor;
    }
}
// Subclasse Carro que herda de Veiculo
class Carro extends Veiculo {
    private int numeroDePortas;
    // Construtor da subclasse que chama o construtor da superclasse
    public Carro(String cor, String marca, int numeroDePortas) {
        super(cor, marca); // Chamando o construtor da superclasse
        this.numeroDePortas = numeroDePortas;
    }
    // Sobrescrevendo o método descrever
    @Override
    public String descrever() {
        String descricaoBase = super.descrever(); // Chama o método da superclasse
        return descricaoBase + " com " + numeroDePortas + " portas";
    }
}
// Classe principal para testar o código
public class Main {
    public static void main(String[] args) {
        Carro carro = new Carro("Vermelho", "Toyota", 4);
        // Exibir descrição do carro
        System.out.println(carro.descrever());
    }
}
