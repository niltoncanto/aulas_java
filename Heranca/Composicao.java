

class Motor {
    String tipo; // Atributo para armazenar o tipo do motor
    public Motor(String tipo) {
        this.tipo = tipo;
    }
}
class Carro { // Classe Carro (Composição)
    String marca; // Atributo para armazenar a marca do carro
    Motor motor;  // Composição: um carro sempre tem um motor
    public Carro(String marca, String tipoMotor) {
        this.marca = marca;
        this.motor = new Motor(tipoMotor); // O motor é criado dentro do carro
    }
}
public class Main {
    public static void main(String[] args) {
        // Criando um objeto Carro, que internamente cria um Motor
        Carro c = new Carro("Ford", "V8");
        // Imprimindo os atributos
        System.out.println(c.marca);        // Output: Ford
        System.out.println(c.motor.tipo);   // Output: V8
    }
}





