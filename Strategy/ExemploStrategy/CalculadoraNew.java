interface ICalculadoraStrategy {
    public int executar(int a, int b);    
}

//classes concretas para cada estratégia
class Adicao implements ICalculadoraStrategy{
    public int executar(int a, int b){
        return a+b;
    }
}

class Subtracao implements ICalculadoraStrategy{
    public int executar(int a, int b){
        return a-b;
    }
}

class Multiplicacao implements ICalculadoraStrategy{
    public int executar(int a, int b){
        return a*b;
    }
}

class CalculadoraContext{
    private ICalculadoraStrategy estrategia;
    //construtor defina a estratégia inicial
    public CalculadoraContext(ICalculadoraStrategy estrategia){
        this.estrategia = estrategia;
    }
    //setter permite o cliente alterar a estratégia a qualquer momento
    public void setEstrategia(ICalculadoraStrategy estrategia){
        this.estrategia = estrategia;
    }
    //contexto delega a execução para o objeto estrategia
    public int executarEstrategia(int a, int b){
        if(estrategia==null){
            throw new IllegalArgumentException("Estratégia inválida!");
        }
        return estrategia.executar(a, b);
    }
}

//cliente de teste
public class CalculadoraNew {
    public static void main(String[] args){
        CalculadoraContext  context = new CalculadoraContext(null);
        int num1=10;
        int num2=50;
        int res;
        // 2. Cliente define a estratégia de Adição
        System.out.println("Definindo estratégia de Adição.");
        context.setEstrategia(new Adicao());
        res = context.executarEstrategia(num1, num2);
        System.out.println("Resultado da Adição (" + num1 + " + " + num2 + "): " + res); // Saída: 15

        // 3. Cliente troca a estratégia para Multiplicação em tempo de execução
        System.out.println("\nTrocando para estratégia de Multiplicação.");
        context.setEstrategia(new Multiplicacao());
        res = context.executarEstrategia(num1, num2);
        System.out.println("Resultado da Multiplicação (" + num1 + " * " + num2 + "): " + res); // Saída: 50

        // 4. Cliente troca a estratégia para Subtração
        System.out.println("\nTrocando para estratégia de Subtração.");
        context.setEstrategia(new Subtracao());
        res = context.executarEstrategia(num1, num2);
        System.out.println("Resultado da Subtração (" + num1 + " - " + num2 + "): " + res); // Saída: 5
    }   
}
