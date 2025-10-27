// ==============================================
// DemoPOO.java
// Exemplo didático dos 4 pilares da POO em Java:
// - Encapsulamento: atributos privados + getters/setters controlados
// - Herança: Gerente e Engenheiro estendem Funcionario
// - Composição: Funcionario "tem um" Departamento (relação has-a)
// - Polimorfismo: calcularBonificacao() implementado de formas distintas
// ==============================================

import java.util.*; // List/Arrays apenas para o demo

// ---------------------------
// Componente de COMPOSIÇÃO
// ---------------------------
class Departamento {
    // Atributos privados (ENCAPSULAMENTO)
    private String nome;

    // Construtor simples
    public Departamento(String nome) {
        this.nome = nome;
    }

    // Getter controlado (ENCAPSULAMENTO)
    public String getNome() {
        return nome;
    }

    // toString para facilitar impressão
    @Override
    public String toString() {
        return "Departamento{" + "nome='" + nome + '\'' + '}';
    }
}

// -----------------------------------------------------
// Classe base ABSTRATA (ABSTRAÇÃO + ponto comum da HIERARQUIA)
// -----------------------------------------------------
abstract class Funcionario {
    // Atributos privados (ENCAPSULAMENTO)
    private String nome;
    private int id;

    // Salário fica protegido para ser usado pelas subclasses sem expor demais
    protected double salario;

    // COMPOSIÇÃO: um Funcionario "tem um" Departamento
    private Departamento departamento;

    public Funcionario(String nome, int id, double salario, Departamento departamento) {
        this.nome = nome;
        this.id = id;
        this.salario = salario;
        this.departamento = departamento;
    }

    // Getters/setters (ENCAPSULAMENTO) — expõem só o necessário
    public String getNome() { return nome; }
    public int getId() { return id; }
    public double getSalario() { return salario; }
    public Departamento getDepartamento() { return departamento; }

    // Setters opcionais — poderiam validar regras de negócio
    public void setSalario(double novoSalario) { this.salario = novoSalario; }

    // Método comum para exibir detalhes (pode ser sobrescrito nas subclasses)
    public void mostrarDetalhes() {
        System.out.println("nome: " + nome);
        System.out.println("id: " + id);
        System.out.println("salario: R$ " + salario);
        System.out.println("departamento: " + departamento.getNome());
    }

    // POLIMORFISMO por sobrescrita:
    // - Cada subclasse é obrigada a definir sua regra de bonificação.
    public abstract double calcularBonificacao();
}

// ---------------------------------------
// Subclasse 1 — HERANÇA + POLIMORFISMO
// ---------------------------------------
class Gerente extends Funcionario {
    // Constante de classe (convencional: CAIXA_ALTA)
    public static final double BONIFICACAO = 0.75;

    // COMPOSIÇÃO adicional (ex.: gerente "tem" uma equipe)
    private List<Funcionario> equipe = new ArrayList<>();

    public Gerente(String nome, int id, double salario, Departamento departamento) {
        super(nome, id, salario, departamento);
    }

    // Sobrescrita (POLIMORFISMO): regra própria de bonificação
    @Override
    public double calcularBonificacao() {
        return getSalario() * BONIFICACAO;
    }

    // Encapsula a equipe (ENCAPSULAMENTO)
    public void adicionarNaEquipe(Funcionario f) { equipe.add(f); }
    public List<Funcionario> getEquipe() { return Collections.unmodifiableList(equipe); }

    // Opcional: enriquecer detalhes do gerente
    @Override
    public void mostrarDetalhes() {
        super.mostrarDetalhes();
        System.out.println("equipe (qtd): " + equipe.size());
    }
}

// ---------------------------------------
// Subclasse 2 — HERANÇA + POLIMORFISMO
// ---------------------------------------
class Engenheiro extends Funcionario {
    public static final double BONIFICACAO = 0.65;

    // Campo específico do Engenheiro (diferencia a subclasse)
    private String especialidade;

    public Engenheiro(String nome, int id, double salario,
                      Departamento departamento, String especialidade) {
        super(nome, id, salario, departamento);
        this.especialidade = especialidade;
    }

    // Sobrescrita (POLIMORFISMO): regra própria de bonificação
    @Override
    public double calcularBonificacao() {
        return getSalario() * BONIFICACAO;
    }

    // Acrescenta detalhes da especialidade ao output
    @Override
    public void mostrarDetalhes() {
        super.mostrarDetalhes();
        System.out.println("especialidade: " + especialidade);
    }
}

// ---------------------------------------
// Classe "executável" com o método main
// ---------------------------------------
public class DemoPOO {
    public static void main(String[] args) {
        // Cria alguns departamentos (COMPOSIÇÃO será usada pelos funcionários)
        Departamento depTI = new Departamento("Tecnologia da Informação");
        Departamento depEng = new Departamento("Engenharia de Produto");

        // Cria um gerente e um engenheiro (HERANÇA — ambos são Funcionarios)
        Gerente gerente = new Gerente("João Carlos", 1000, 25_000.00, depTI);
        Engenheiro engenheiro = new Engenheiro("Pedro Salgado", 2000, 22_000.00, depEng, "Computação");

        // Exemplo de composição adicional: gerente "tem" uma equipe
        gerente.adicionarNaEquipe(engenheiro);

        // Lista polimórfica: referência do tipo base para objetos de subclasses
        List<Funcionario> equipe = Arrays.asList(gerente, engenheiro);

        // Demonstra POLIMORFISMO: a mesma chamada invoca implementações diferentes
        for (Funcionario f : equipe) {
            System.out.println("\n--- Detalhes do funcionário ---");
            f.mostrarDetalhes();
            System.out.println("bonificação: R$ " + f.calcularBonificacao());
        }

        // ENCAPSULAMENTO em ação: acessamos dados via getters controlados
        System.out.println("\nResumo rápido:");
        System.out.println(gerente.getNome() + " - Dept: " + gerente.getDepartamento().getNome());
        System.out.println(engenheiro.getNome() + " - Dept: " + engenheiro.getDepartamento().getNome());
    }
}
