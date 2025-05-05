class Calculadora{
    public static int soma(int a, int b){
        return a+b;
    }

    public static float soma(float a, float b){
        return a+b;
    }

    public float multiplicacao(float a , float b){
        return a*b;
    }
}

public class CalculadoraMain{
    public static void main(String[] args){
       int soma_int = Calculadora.soma(1,5);
       float soma_float = Calculadora.soma(2.6f, 8.9f);
       //float multiplicacao = Calculadora.multiplicacao(5f,6f);
       Calculadora cal = new Calculadora();
       float mult = cal.multiplicacao(5.2f,6);
       System.out.println("Soma de inteiros:" + soma_int);
       System.out.println("Soma de floats:" + soma_float);
       System.out.println("Multiplicacao:" + mult);
    }
}