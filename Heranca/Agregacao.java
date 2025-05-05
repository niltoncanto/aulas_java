
class Motor {
    String tipo;
    public Motor(String tipo) {
        this.tipo = tipo;
    }
}
class Carro {
    String marca; // Atributo para armazenar a marca do carro
    Motor motor;  // Composição: um carro possui um motor
    // Construtor da classe Carro
    public Carro(String marca, Motor motor) {
        this.marca = marca;
        this.motor = motor;
    }
}
public class Main {
    public static void main(String[] args) {
        // Criando um objeto Motor
        Motor m = new Motor("V8");
        // Criando um objeto Carro e associando ao motor criado
        Carro c = new Carro("Ford", m);
        System.out.println(c.marca);        // Output: Ford
        System.out.println(c.motor.tipo);   // Output: V8
    }
}


