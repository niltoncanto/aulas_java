// Classe abstrata que serve como modelo para diferentes tipos de funcionários
abstract class Funcionario {
    protected String nome;
    protected double salarioBase;

    // Construtor
    public Funcionario(String nome, double salarioBase) {
        this.nome = nome;
        this.salarioBase = salarioBase;
    }

    // Método abstrato - Cada tipo de funcionário implementará de forma diferente
    public abstract double calcularSalario();

    // Método comum para exibir informações
    public void exibirInformacoes() {
        System.out.println("Nome: " + nome);
        System.out.println("Salário: R$" + calcularSalario());
    }
}

// Subclasse para funcionário CLT (com salário fixo)
class FuncionarioCLT extends Funcionario {
    public FuncionarioCLT(String nome, double salarioBase) {
        super(nome, salarioBase);
    }

    // Implementação do método abstrato
    @Override
    public double calcularSalario() {
        return salarioBase;  // Salário fixo sem alterações
    }
}

// Subclasse para funcionário PJ (freelancer) que ganha por hora
class FuncionarioPJ extends Funcionario {
    private int horasTrabalhadas;
    private double valorHora;

    public FuncionarioPJ(String nome, double valorHora, int horasTrabalhadas) {
        super(nome, 0);  // PJ não tem salário base fixo
        this.horasTrabalhadas = horasTrabalhadas;
        this.valorHora = valorHora;
    }

    @Override
    public double calcularSalario() {
        return horasTrabalhadas * valorHora;
    }
}

// Classe Principal para testar
public class FuncionarioMain {
    public static void main(String[] args) {
        // Criando um funcionário CLT (salário fixo)
        Funcionario clt = new FuncionarioCLT("Alice", 5000);
        
        // Criando um funcionário PJ (ganha por hora)
        Funcionario pj = new FuncionarioPJ("Bruno", 50, 160); // 50 reais/hora, 160 horas/mês

        // Exibindo os salários
        System.out.println("Dados dos funcionários:");
        clt.exibirInformacoes();
        System.out.println("-------------------");
        pj.exibirInformacoes();
    }
}
