interface Strategy{
    public int executar(int a, int b);
}

class Adicao implements Strategy{
    public int executar(int a, int b){
        return a+b;
    }
}

class subtracao implements Strategy{
    public int executar(int a, int b){
        return a-b;
    }
}

class Multiplicacao implements Strategy{
    public int executar(int a, int b){
        return a*b;
    }
}

class Context{
    private Strategy estrategia;
    public void Context(Strategy estrategia){
        this.estrategia = estrategia;
    }
    public void setStrategy(Strategy estrategia){
        this.estrategia = estrategia;
    }
    public int executarStrategy(int a, int b){
        return estrategia.executar(a,b);
    }
}

public class CalculadoraStrategy{
    public static void main(String[] args){
        Context contexto = new Context();
        int a=10;
        int b=20;
        int res =0;
        //definindo estrategia
        contexto.setStrategy(new Adicao());
        res = contexto.executarStrategy(a,b);
        System.out.println("a+b="+res);
        
    }
}