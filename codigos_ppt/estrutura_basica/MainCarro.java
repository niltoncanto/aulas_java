class Motor{
    public Boolean ligado=true;

    public void ligar() {
        ligado = true;
        System.out.println("Motor ligado.");
    }

    public boolean estaLigado() {
        return ligado;
    }
}

class Carro{
    public Motor motor;
    //contrutor
    public Carro(){
        this.motor = new Motor();
    }

    public void ligarCarro() {
        System.out.println("Tentando ligar o carro...");
        motor.ligar(); // O carro envia uma mensagem para o motor ligar
    }

    public void dirigir() {
        if (motor.estaLigado()) { // O carro verifica o estado do motor
            System.out.println("O carro está em movimento!");
        } else {
            System.out.println("O carro não pode se mover, pois o motor está desligado.");
        }
    }
}

    // Classe Main para testar a comunicação entre objetos
public class MainCarro {
        public static void main(String[] args) {
            Carro meuCarro = new Carro();
            meuCarro.dirigir();  // ❌ O carro não pode se mover, motor está desligado

            meuCarro.ligarCarro(); // ✅ Envia uma mensagem ao Motor para ligar
            meuCarro.dirigir();    // ✅ Agora o carro pode se mover
        }
}

