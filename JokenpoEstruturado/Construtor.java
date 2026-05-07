class Carro {
    String marca;  // Atributo da marca do carro
    int ano;       // Atributo do ano de fabricação

    // Construtor da classe Carro
    public Carro(String marca, int ano) {
        this.marca = marca;  // Inicializa o atributo marca
        this.ano = ano;      // Inicializa o atributo ano
    }

    // Método para exibir informações do carro
    public void mostrarInfo() {
        System.out.println("Carro: " + marca + ", Ano: " + ano);
    }
}

public class Main {
    public static void main(String[] args) {
        // Criando um objeto da classe Carro usando o construtor
        Carro meuCarro = new Carro("Toyota", 2022);

        // Chamando o método para exibir as informações do carro
        meuCarro.mostrarInfo();
    }
}

