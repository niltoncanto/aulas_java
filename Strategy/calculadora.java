public class Main {

    public static final String ADICAO = "adicao";
    public static final String SUBTRACAO = "subtracao";
    public static final String MULTIPLICACAO = "multiplicacao";
     
    public static void main(String[] args){
        // A constante ADICAO é definida em minúsculas (linha 5), mas aqui está em maiúsculas.
        // O valor correto da constante é "adicao", vamos usar o valor direto para testar.
        int resultado = executar(ADICAO, 10, 20);
        System.out.println("Resultado: " + resultado);
    }
    
    // CORREÇÃO: Adicionando 'static' ao método executar
    public static int executar(String tipo, int a, int b) {
        if (tipo.equals(ADICAO)) {
            return a + b; 
        } else if (tipo.equals(SUBTRACAO)) {
            return a - b;
        } else if (tipo.equals(MULTIPLICACAO)) {
            return a * b;
        } else {
            throw new IllegalArgumentException("Operação Desconhecida: " + tipo);
        }
    }
}