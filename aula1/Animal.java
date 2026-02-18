// Classe Animal
public class AnimalEx1 {
    // Atributos
    private String nome;   // Nome do animal (ex: Rex)
    private String tipo;   // Tipo do animal (ex: Mamífero, Réptil)
    private String som;    // Som característico (ex: Latido, Miado)

    // Construtor
    public Animal(String nome, String tipo, String som) {
        this.nome = nome;
        this.tipo = tipo;
        this.som = som;
    }

    // Método setSom - altera o som do animal
    public void setSom(String som) {
        this.som = som;
    }

    // Método fazerSom - imprime o som característico
    public void fazerSom() {
        System.out.println(nome + " está fazendo som: " + som);
    }

    // Método alimentar - imprime a ação de alimentar
    public void alimentar() {
        System.out.println(nome + " está se alimentando.");
    }

    // Método dormir - imprime a ação de dormir
    public void dormir() {
        System.out.println(nome + " está dormindo.");
    }

    // Método mostrarInfo - imprime as informações do animal
    public void mostrarInfo() {
        System.out.println("Nome: " + nome);
        System.out.println("Tipo: " + tipo);
        System.out.println("Som: " + som);
    }
}

public class Main {
    public static void main(String[] args) {
        // Criando um animal
        Animal cachorro = new Animal("Rex", "Mamífero", "Latido");

        // Mostrando informações
        cachorro.mostrarInfo();

        // Executando ações
        cachorro.fazerSom();
        cachorro.alimentar();
        cachorro.dormir();

        // Alterando o som do animal
        cachorro.setSom("Uivo");
        cachorro.fazerSom();
    }
}
