/**
 * Classe utilitária com métodos matemáticos básicos para demonstração de testes unitários.
 */
package CALCULADORA;
public class CalculadoraUtil {

    // Soma dois inteiros
    public int somar(int a, int b) {
        return a + b;
    }

    // Subtrai dois inteiros
    public int subtrair(int a, int b) {
        return a - b;
    }

    // Multiplica dois inteiros
    public int multiplicar(int a, int b) {
        return a * b;
    }

    // Divide dois inteiros com tratamento de divisão por zero
    public int dividir(int a, int b) {
        if (b == 0) throw new ArithmeticException("Divisão por zero");
        return a / b;
    }

    // Verifica se um número é par
    public boolean ehPar(int numero) {
        return numero % 2 == 0;
    }

    // Calcula o fatorial de um número inteiro não negativo
    public int fatorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Número negativo");
        int resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    // Retorna o maior entre dois números
    public int maior(int a, int b) {
        return Math.max(a, b);
    }

    // Retorna o menor entre dois números
    public int menor(int a, int b) {
        return Math.min(a, b);
    }

    // Verifica se um número é primo
    public boolean ehPrimo(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // Calcula a média de um array de inteiros
    public double media(int[] numeros) {
        if (numeros == null || numeros.length == 0)
            throw new IllegalArgumentException("Array vazio");
        int soma = 0;
        for (int n : numeros) {
            soma += n;
        }
        return (double) soma / numeros.length;
    }
}
