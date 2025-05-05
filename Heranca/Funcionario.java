public // Classe abstrata Funcionario (Superclasse)
abstract class Funcionario {
    protected String nome;
    protected String cpf;
    protected double salarioBase;

    // Construtor
    public Funcionario(String nome, String cpf, double salarioBase) {
        this.nome = nome;
        this.cpf = cpf;
        this.salarioBase = salarioBase;
    }

    // Método abstrato a ser implementado pelas subclasses
    public abstract double calcularSalario();

    // Método para exibir informações do funcionário
    public void exibirInformacoes() {
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Salário: R$ " + calcularSalario());
    }
}

// Classe Administrativo (Herda de Funcionario)
class Administrativo extends Funcionario {
    private String departamento;

    // Construtor
    public Administrativo(String nome, String cpf, double salarioBase, String departamento) {
        super(nome, cpf, salarioBase);
        this.departamento = departamento;
    }

    // Implementação do método calcularSalario()
    @Override
    public double calcularSalario() {
        return salarioBase; // Retorna o salário base sem adicionais
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Departamento: " + departamento);
        System.out.println();
    }
}

// Classe Professor (Herda de Funcionario)
class Professor extends Funcionario {
    private String titulacao;
    private int horasAula;
    private static final double VALOR_HORA_AULA = 50.0; // Valor fixo por hora-aula

    // Construtor
    public Professor(String nome, String cpf, double salarioBase, String titulacao, int horasAula) {
        super(nome, cpf, salarioBase);
        this.titulacao = titulacao;
        this.horasAula = horasAula;
    }

    // Implementação do método calcularSalario()
    @Override
    public double calcularSalario() {
        return salarioBase + (horasAula * VALOR_HORA_AULA);
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Titulação: " + titulacao);
        System.out.println("Horas Aula: " + horasAula);
        System.out.println();
    }
}

// Classe Tecnico (Herda de Funcionario)
class Tecnico extends Funcionario {
    private String cargo;
    private static final double ADICIONAL_TECNICO = 500.0; // Valor adicional fixo para técnicos

    // Construtor
    public Tecnico(String nome, String cpf, double salarioBase, String cargo) {
        super(nome, cpf, salarioBase);
        this.cargo = cargo;
    }

    // Implementação do método calcularSalario()
    @Override
    public double calcularSalario() {
        return salarioBase + ADICIONAL_TECNICO;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Cargo: " + cargo);
        System.out.println();
    }
}

// Classe principal para testar a implementação
public class Funcionario {
    public static void main(String[] args) {
        // Criando objetos de diferentes tipos de funcionários
        Administrativo admin = new Administrativo("Carlos Silva", "111.222.333-44", 3000.0, "Recursos Humanos");
        Professor professor = new Professor("Ana Souza", "555.666.777-88", 4000.0, "Doutora em Computação", 20);
        Tecnico tecnico = new Tecnico("José Pereira", "999.888.777-66", 2500.0, "Manutenção de TI");

        // Exibindo informações dos funcionários
        System.out.println("=== Funcionários ===");
        admin.exibirInformacoes();
        professor.exibirInformacoes();
        tecnico.exibirInformacoes();
    }
}
 {
    
}
