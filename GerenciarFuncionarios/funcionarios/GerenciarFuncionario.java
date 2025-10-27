/*
 * ============================================================
 *  Arquivo: GerenciarFuncionario.java
 *  Objetivo do programa:
 *    Demonstrar conceitos fundamentais de Programação Orientada a Objetos (POO)
 *    em Java por meio de um pequeno sistema de gerenciamento de funcionários
 *    (Funcionário genérico, Gerente e Engenheiro), incluindo:
 *      - Encapsulamento (atributos privados + métodos de acesso/controlados)
 *      - Herança (Gerente e Engenheiro "são um" Funcionário)
 *      - Polimorfismo por sobrescrita (@Override)
 *      - Uso de constantes e níveis de visibilidade (public, private, protected)
 *
 *  Como compilar e executar (no terminal):
 *      javac GerenciarFuncionario.java
 *      java GerenciarFuncionario
 *
 *  Observação didática:
 *    - Este arquivo contém comentários abrangentes para servir de referência
 *      de estudo aos alunos. Cada bloco/linha importante está descrito.
 * ============================================================
 */

/* =========================================================================
 *  CLASSE BASE: Funcionario
 * -------------------------------------------------------------------------
 *  Responsabilidade:
 *    - Modelar um funcionário genérico com nome, id e salário.
 *    - Oferecer um "gancho" (método calcularBonificacao) para que subclasses
 *      especializadas definam suas próprias regras de bonificação.
 * =========================================================================
 */
class Funcionario {
    // -----------------------
    // ATRIBUTOS/ESTADO
    // -----------------------

    // 'private' garante encapsulamento: acesso direto aos atributos é proibido
    // fora da classe; interação deve ocorrer via métodos.
    private String nome;      // Nome completo do funcionário
    private int id;           // Identificador único (código interno)
    private double salario;   // Salário base (valor bruto mensal)

    // -----------------------
    // CONSTRUTOR
    // -----------------------

    /**
     * Constrói um novo Funcionário com nome, id e salário informados.
     * @param nome Nome do funcionário
     * @param id   Identificador único
     * @param salario Salário base
     */
    public Funcionario(String nome, int id, double salario) {
        // 'this' referencia o atributo do objeto atual
        this.nome = nome;
        this.id = id;
        this.salario = salario;
    }

    // -----------------------
    // COMPORTAMENTOS/MÉTODOS
    // -----------------------

    /**
     * Exibe no console os detalhes do funcionário.
     * Observação: método pensado para ser reutilizado/estendido por subclasses.
     */
    public void mostrarDetalhes() {
        // Concatenação simples de strings para exibição
        System.out.println("nome: " + nome);
        System.out.println("id: " + id);
        System.out.println("salario: R$ " + salario);
    }

    /**
     * Fornece acesso controlado ao salário para subclasses.
     * 'protected' → visível dentro do mesmo pacote e em subclasses
     * (mesmo que estejam em pacotes diferentes).
     *
     * Regra didática:
     *   - Mantemos o atributo 'salario' privado para preservar o encapsulamento.
     *   - Oferecemos um método protegido para que subclasses possam ler o valor
     *     e, por exemplo, calcular bonificações.
     */
    protected double getSalario() {
        return salario;
    }

    /**
     * Método "gancho" para cálculo de bonificação.
     * Padrão de projeto simples:
     *   - A implementação base retorna 0.0 (sem bonificação).
     *   - Subclasses especializadas devem sobrescrever (@Override) para aplicar
     *     sua própria regra (percentual, valor fixo, etc.).
     *
     * Observação:
     *   Poderíamos tornar este método 'abstract' e a classe 'abstract' se
     *   quiséssemos OBRIGAR todas as subclasses a fornecer uma implementação.
     */
    public double calcularBonificacao() {
        return 0.0; // valor padrão quando não há regra específica
    }

    // (Opcional) Poderíamos adicionar getters/setters públicos para nome e id,
    // caso precisemos ler/alterar tais valores fora da classe.
}

/* =========================================================================
 *  SUBCLASSE: Gerente
 * -------------------------------------------------------------------------
 *  Responsabilidade:
 *    - Especializar Funcionário com uma regra própria de bonificação.
 *    - Demonstra uso de constante de classe (static final).
 * =========================================================================
 */
class Gerente extends Funcionario {
    // Constante de classe: padrão de bonificação para gerentes (75% do salário)
    public static final double BONIFICACAO = 0.75;

    /**
     * Construtor que encaminha (delegação) os dados comuns para a classe base.
     * Uso de 'super(...)' chama o construtor da superclasse (Funcionario).
     */
    public Gerente(String nome, int id, double salario) {
        super(nome, id, salario);
    }

    /**
     * Sobrescreve o "gancho" de bonificação.
     * Polimorfismo por sobrescrita: a chamada a calcularBonificacao() em um
     * objeto Gerente executa esta versão, não a da classe base.
     */
    @Override
    public double calcularBonificacao() {
        // Usa o getSalario() protegido da superclasse para manter encapsulamento
        return getSalario() * BONIFICACAO;
    }
}

/* =========================================================================
 *  SUBCLASSE: Engenheiro
 * -------------------------------------------------------------------------
 *  Responsabilidade:
 *    - Especializar Funcionário com um atributo adicional (especialidade).
 *    - Sobrescrever mostrarDetalhes para incluir a especialidade.
 *    - Definir sua própria regra de bonificação.
 * =========================================================================
 */
class Engenheiro extends Funcionario {
    // Atributo específico de Engenheiro
    private String especialidade; // Ex.: "software", "hardware", "civil", etc.

    // Constante de classe: bonificação padrão para engenheiros (65% do salário)
    public static final double BONIFICACAO = 0.65;

    /**
     * Construtor: recebe também a especialidade do engenheiro.
     * Encaminha nome/id/salario para a superclasse via 'super(...)'.
     */
    public Engenheiro(String nome, int id, double salario, String especialidade) {
        super(nome, id, salario);
        // Inicializa o campo específico desta subclasse
        this.especialidade = especialidade;
    }

    /**
     * Sobrescrita de método:
     *   - Reaproveita a exibição padrão da superclasse (super.mostrarDetalhes())
     *   - Acrescenta a linha com a especialidade do engenheiro.
     *
     * Benefício didático:
     *   - Mostra como estender um comportamento sem reescrever tudo do zero.
     */
    @Override
    public void mostrarDetalhes() {
        // Chama a versão base para imprimir nome, id e salário
        super.mostrarDetalhes();
        // Adiciona informação específica do Engenheiro
        System.out.println("Especialidade: " + especialidade);
    }

    /**
     * Regra de bonificação do Engenheiro (65% do salário).
     * Demonstra polimorfismo por sobrescrita, assim como em Gerente.
     */
    @Override
    public double calcularBonificacao() {
        return getSalario() * BONIFICACAO;
    }

    // (Opcional) Getters/Setters para 'especialidade' poderiam ser adicionados
    // caso precisemos ler/alterar a especialidade fora da classe.
}

/* =========================================================================
 *  CLASSE DE EXECUÇÃO: GerenciarFuncionario
 * -------------------------------------------------------------------------
 *  Responsabilidade:
 *    - Ponto de entrada do programa (método main).
 *    - Criar instâncias de Gerente e Engenheiro e demonstrar:
 *        * Herança (ambos são Funcionário)
 *        * Polimorfismo (métodos sobrescritos)
 *        * Cálculo de bonificações específicas por tipo
 * =========================================================================
 */
public class GerenciarFuncionario {

    /**
     * Método main: ponto de entrada da aplicação Java.
     * Fluxo didático:
     *   1) Criar um Gerente e um Engenheiro com dados fictícios.
     *   2) Chamar mostrarDetalhes() em cada um:
     *        - Para o Engenheiro, o método é sobrescrito (imprime especialidade).
     *        - Para o Gerente, usa a versão da classe base (Funcionario).
     *   3) Calcular e exibir a bonificação do Gerente (e opcionalmente do Engenheiro).
     */
    public static void main(String[] args) {
        // ------------------------------
        // 1) Instanciação dos objetos
        // ------------------------------

        // Cria um Gerente: nome, id e salário
        Gerente gerente = new Gerente("João Carlos", 1000, 25000.00);

        // Cria um Engenheiro: nome, id, salário e especialidade
        Engenheiro engenheiro = new Engenheiro("Pedro Salgado", 2000, 22000.00, "computação");

        // ------------------------------
        // 2) Exibição de detalhes
        // ------------------------------

        // Chama a versão sobrescrita (Engenheiro → imprime também a especialidade)
        engenheiro.mostrarDetalhes();

        // Chama a versão da classe base (Funcionario) reaproveitada pelo Gerente
        gerente.mostrarDetalhes();

        // ------------------------------
        // 3) Cálculo de bonificações
        // ------------------------------

        // Polimorfismo em ação: para 'gerente', será usada a regra de Gerente (75%)
        double bonusGerente = gerente.calcularBonificacao();
        System.out.println("bonificação gerente: R$ " + bonusGerente);

        // (Opcional, didático) Também podemos exibir a bonificação do engenheiro:
        // double bonusEngenheiro = engenheiro.calcularBonificacao();
        // System.out.println("bonificação engenheiro: R$ " + bonusEngenheiro);

        // Fim do fluxo principal. Em cenários reais, poderíamos armazenar os objetos
        // em coleções (List<>) e iterar, persistir dados, etc.
    }
}
