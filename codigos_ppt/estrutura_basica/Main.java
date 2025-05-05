import java.util.Date;

class Pessoa {
    // Atributos 
    public String nome;
    public String cpf;
    public String endereco;
    public Integer idade; b
    public Float salario;
    public Character sexo;
    public Boolean status;

    // Construtor 
    public Pessoa(String nome, String cpf, String endereco, Integer idade, Float salario, Character sexo) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.idade = idade;
        this.salario = salario;
        this.sexo = sexo;
        this.status = true;
    }

    // Método para exibir informações
    public void showPessoa() {
        System.out.println("Nome: " + this.nome);
        System.out.println("CPF: " + this.cpf);
        System.out.println("Endereço: " + this.endereco);
        System.out.println("Idade: " + this.idade);
        System.out.println("Salário: " + this.salario);
        System.out.println("Sexo: " + this.sexo);
        System.out.println("Status: " + this.status);
    }
}

// Classe principal
class Main {
    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa("João", "0009999", "Rua Teste", 30, 10500.50f, 'M');
        pessoa.showPessoa();
    }
}
