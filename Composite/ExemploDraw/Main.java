import java.util.ArrayList;
import java.util.List;

// Interface comum para todos os objetos da composição
interface Graphic {
    void draw();
}

// Implementação de um objeto "folha"
class Circle implements Graphic {
    private String name;

    public Circle(String name) {
        this.name = name;
    }

    @Override
    public void draw() {
        System.out.println("Desenhando círculo: " + name);
    }
}

// Implementação de um objeto "composto"

class CompositeGraphic implements Graphic {
    private List<Graphic> children = new ArrayList<>();

    public void add(Graphic graphic) {
        children.add(graphic);
    }

    public void remove(Graphic graphic) {
        children.remove(graphic);
    }

    @Override
    public void draw() {
        for (Graphic graphic : children) {
            graphic.draw();
        }
    }
}

// Exemplo de uso
public class Main {
    public static void main(String[] args) {
        // Cria objetos simples (folhas)
        Circle c1 = new Circle("A");
        Circle c2 = new Circle("B");
        Circle c3 = new Circle("C");

        // Cria o grupo (objeto composto)
        CompositeGraphic group = new CompositeGraphic();
        group.add(c1);
        group.add(c2);

        // Cria outro grupo que contém o anterior e outro círculo
        CompositeGraphic root = new CompositeGraphic();
        root.add(group);
        root.add(c3);

        // Chama o método draw() de forma uniforme
        root.draw();
    }
}

