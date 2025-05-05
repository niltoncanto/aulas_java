
class Animal {
    protected String nome;
    public Animal(String nome) { 
        this.nome = nome;
    }
    public String fazerSom() {
        return "Som do animal";
    }
}
class Cachorro extends Animal {
    public Cachorro(String nome) { 
        super(nome);
    }
    @Override
    public String fazerSom() { 
        return "Latido";
    }
}
public class Main {
    public static void main(String[] args) {
        Cachorro c = new Cachorro("Rex");
        System.out.println(c.nome);         
        System.out.println(c.fazerSom());   
    }
}






