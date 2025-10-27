/* 
 Objetivo: Desenvolver um sistema para gerenciar uma empresa com diferentes tipos de funcionários, incluindo salários, cargos e bonificações.

 Parte 1: Definir Classes
1. Classe Funcionário: 
   - Atributos: nome, ID, salário.
   - Métodos: mostrar_detalhes, calcular_bonificacao.
2. Classe Gerente (herda de Funcionário):

3. Classe Engenheiro (herda de Funcionário):
   - Atributo adicionais: especialidade (por exemplo, software, hardware).
   - Método  mostrar_detalhes (sobrescrito)

 */
class Funcionario{
    private String nome;
    private int id;
    private double salario;

    public Funcionario(String nome, int id, double salario){
        this.nome = nome;
        this.id = id;
        this.salario = salario;
    }

    public void mostrarDetalhes(){
        System.out.println("nome:"+nome);
        System.out.println("id:"+id);
        System.out.println("salario: R$" + salario);
    }
    public double calcularBonificacao(double percentual){
        return salario*percentual;
    }
}