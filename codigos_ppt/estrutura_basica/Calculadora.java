public class Calculadora {

    // Método para somar múltiplos inteiros
    public int somar(int... args) {
        int soma = 0;
        for (int num : args) {
            soma += num;
        }
        return soma;
    }

    // Método para somar múltiplos números decimais
    public double somar(double... args) {
        double soma = 0.0;
        for (double num : args) {
            soma += num;
        }
        return soma;
    }

    // Método para subtrair dois inteiros
    public int subtrair(int a, int b) {
        return a - b;
    }

    // Método para subtrair dois números decimais
    public double subtrair(double a, double b) {
        return a - b;
    }

    // Método para multiplicar dois inteiros
    public int multiplicar(int a, int b) {
        return a * b;
    }

    // Método para multiplicar dois números decimais
    public double multiplicar(double a, double b) {
        return a * b;
    }

    // Método para dividir dois inteiros (com validação de divisão por zero)
    public int dividir(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Divisão por zero não é permitida.");
        }
        return a / b;
    }

    // Método para dividir dois números decimais (com validação de divisão por zero)
    public double dividir(double a, double b) {
        if (b == 0.0) {
            throw new ArithmeticException("Divisão por zero não é permitida.");
        }
        return a / b;
    }
}
