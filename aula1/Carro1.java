// Classe Carro
public class Carro1 {
    // Atributos privados
    private String marca;
    private String modelo;
    private int ano;
    private boolean ligado;

    // Construtor
    public Carro1(String marca, String modelo, int ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.ligado = false; // carro começa desligado
    }

    // Método para ligar o carro
    public void ligar() {
        ligado = true;
        System.out.println("Carro ligado!");
    }

    // Método para desligar o carro
    public void desligar() {
        ligado = false;
        System.out.println("Carro desligado!");
    }

    // Método para mostrar informações do carro
    public void mostrarInfo() {
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Ano: " + ano);
        System.out.println("Está ligado? " + (ligado ? "Sim" : "Não"));
        System.out.println("-------------------------");
    }

    // Programa principal para testar
    public static void main(String[] args) {
        // Instanciação do objeto
        Carro1 carro1 = new Carro1("Ford", "Fiesta", 2020);

        // Exibe informações iniciais
        carro1.mostrarInfo();

        // Liga o carro e mostra novamente
        carro1.ligar();
        carro1.mostrarInfo();

        // Desliga o carro e mostra novamente
        carro1.desligar();
        carro1.mostrarInfo();
    }
}
