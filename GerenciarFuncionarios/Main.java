public class Main {
    public static void main(String[] args){
        Gerente gerente = new Gerente("João Carlos",1000,25000.00);
        Engenheiro engenheiro = new Engenheiro("Pedro Salgado",2000,22000.00,"computação");
        engenheiro.mostrarDetalhes();
        gerente.mostrarDetalhes();
    }
}
