import java.util.List;
import java.util.ArrayList;
interface Component{  
    void draw();
}

class Circle implements Component{
    private String nome;
    public Circle(String nome){
        this.nome = nome;
    }
    @Override
    public void draw(){
        System.out.println("Desenhando círculo: " + nome);
    }
}

class Rectangle implements Component{
    private String nome;
    public Rectangle(String nome){
         this.nome = nome;
    }
    @Override
    public void draw(){
        System.out.println("Desenhando círculo: " + nome);
    }
}

class Composite implements Component{
    List<Component> componentes = new ArrayList<>(); 

    public void add(Component componente){
        componentes.add(componente);
    }

    public void remove(Component componente){
        componentes.remove(componente);
    }

    @Override
    public void draw() {
        for (Component componente : componentes) {
            componente.draw();
        }
    }
}

class Exemplo{
    public static void main(String[] args){
        Component circulo1 = new Circle("circ1");
        Component circulo2 = new Circle("circ2");
        Component circulo3 = new Circle("circ3");
        Component retangulo1 = new Circle("retangulo1");
        Component retangulo2 = new Circle("retangulo2");

        Composite grupo = new Composite();
        grupo.add(circulo1);
        grupo.add(circulo2);
        grupo.add(retangulo1);
        grupo.add(retangulo2);

        Composite root = new Composite();
        root.add(grupo);
        root.add(circulo3);

        // Chama o método draw() de forma uniforme
        root.draw();

    }
}
