
class Pessoa{
    //atributos de instancia, cada pessoa tem seu nome e idade
    private String nome;
    private int idade;
    
    //atributo da classe (compartilhado pela classe inteira)
    private static int contador=0;

    public Pessoa(String nome, int idade){
        this.nome = nome;
        this.idade = idade;
        contador++; // pode acessar o atributo estático dentro do contrutor
    }
    //médodo de instância
    public void apresentar(){
        System.out.println("oi eu sou "+ nome + " e tenho " + idade + "anos.");
    }

    //método estático (pertence a classe)
    public static void mostrarContador(){
        //pode acessar membros static da classe
        System.out.println("numero de pessoas = " + contador);
        //nao pode acessar métodos e atributos de instância
        // System.out.println("nome ": nome); //erro
        //apresentar(); //erro
        Pessoa p = new Pessoa("joao",30);
        System.out.println("nome: " + p.nome);
        p.apresentar();

    }
}
public class MainPessoa{

    public static void main(String[] args){
        Pessoa.mostrarContador();
        System.out.println("PI:"+ Math.PI);
        System.out.println("raiz de 3:"+ Math.sqrt(3));

    }
}