import java.util.ArrayList;
import java.util.List;

abstract class Animal {
    public abstract void emitirSom();
    public void dormir() {
        System.out.println("Zzzz...");
    }
}

class Cachorro extends Animal {
    @Override
    public void emitirSom() {
        System.out.println("Au au!");
    }
    public void abanarRabo() {
        System.out.println("Abanando o rabo...");
    }
}

class Gato extends Animal {
    @Override
    public void emitirSom() {
        System.out.println("miau!");
    }
    public void abanarRabo() {
        System.out.println("miau miau...");
    }
}

public class MainAnimal{
    public static void main(String[] args){
        Animal a = new Cachorro();
        a.emitirSom(); // imprime "Au au!"
        a.dormir();    // OK, existe em Animal
        // a.abanarRabo(); // ERRO: Animal não define esse método

        Cachorro c = new Cachorro();
        c.emitirSom();   // OK
        c.dormir();      // OK
        c.abanarRabo();  // OK (só disponível em Cachorro)

        List<Animal> animais = new ArrayList<>();
        animais.add(new Cachorro());
        animais.add(new Gato());

        for (Animal x : animais) {
            x.emitirSom(); // chama cada um conforme sua implementação
        }
    }
}