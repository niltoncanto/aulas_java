public class Engenheiro extends Funcionario{
    private String especialidade;
    public Engenheiro(String nome, int id, double salario, String especialidade){
        super(nome,id,salario);
        this.especialidade = especialidade;
    }

    //método sobrescrito
    @Override
    public void mostrarDetalhes(){
        super.mostrarDetalhes();
        System.out.println("Especialidade:"+especialidade);
    }
}
