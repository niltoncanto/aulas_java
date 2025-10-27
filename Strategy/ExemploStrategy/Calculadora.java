class Calculadora {

    // Definição de constantes ou um enum para os tipos de operação
    public static final String ADICAO = "adicao";
    public static final String SUBTRACAO = "subtracao";
    public static final String MULTIPLICACAO = "multiplicacao";
    /**
     * Executa a operação matemática baseada no tipo fornecido.
     * Esta é a lógica que se torna inchada e problemática.
     * 1. Violação do Princípio de Responsabilidade Única (SRP)
     * 2. Violação do Princípio Aberto/Fechado (OCP)
     */
    public int executar(String tipo, int a, int b) {
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