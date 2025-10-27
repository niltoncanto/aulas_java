class Animal {
    public void emitirSom() {
        System.out.println("Animal emitindo som genérico...");
    }
}
class Cachorro extends Animal {
    @Override
    public void emitirSom() {
        System.out.println("O cachorro late: Au Au!");
    }
}
class Gato extends Animal {
    @Override
    public void emitirSom() {
        System.out.println("O gato mia: Miau!");
    }
}
public class PolimorfismoSobrescrita {
    public static void main(String[] args) {
        Animal[] animais = new Animal[3];
        animais[0] = new Cachorro();
        animais[1] = new Gato();

        for (Animal a : animais) {
            a.emitirSom();
        }
    }
}
