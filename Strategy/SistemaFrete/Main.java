// Interface que define o contrato para as estratégias de frete
interface FreteStrategy {
    double calcularFrete(double peso, double valorTotal);
}

// Estratégia SEDEX: custo proporcional ao peso
class SedexFrete implements FreteStrategy {
    public double calcularFrete(double peso, double valorTotal) {
        return peso * 1.45;
    }
}

// Estratégia PAC: custo proporcional ao peso, porém mais barato
class PacFrete implements FreteStrategy {
    public double calcularFrete(double peso, double valorTotal) {
        return peso * 1.10;
    }
}

// Estratégia Frete Grátis: se o valor da compra for acima de R$200, o frete é grátis
class FreteGratis implements FreteStrategy {
    public double calcularFrete(double peso, double valorTotal) {
        if (valorTotal > 200.0) {
            return 0.0; // frete grátis
        } else {
            return peso * 1.25; // custo padrão quando não se qualifica para gratuidade
        }
    }
}

// Classe de contexto que utiliza a estratégia definida dinamicamente
class CalculadoraFrete {
    private FreteStrategy estrategia;

    // Permite mudar a estratégia durante a execução
    public void setEstrategia(FreteStrategy estrategia) {
        this.estrategia = estrategia;
    }

    // Executa o cálculo de frete usando a estratégia atual
    public double calcular(double peso, double valorTotal) {
        return estrategia.calcularFrete(peso, valorTotal);
    }
}

// Classe principal para testar o sistema
public class Main {
    public static void main(String[] args) {
        CalculadoraFrete calculadora = new CalculadoraFrete();

        // Cenário 1: SEDEX
        calculadora.setEstrategia(new SedexFrete());
        System.out.println("Frete SEDEX: R$" + calculadora.calcular(10, 100));

        // Cenário 2: PAC
        calculadora.setEstrategia(new PacFrete());
        System.out.println("Frete PAC: R$" + calculadora.calcular(10, 100));

        // Cenário 3: Frete Grátis (compra acima de R$200)
        calculadora.setEstrategia(new FreteGratis());
        System.out.println("Frete Grátis (compra de R$250): R$" + calculadora.calcular(10, 250));

        // Cenário 4: Frete Grátis (compra abaixo do limite)
        System.out.println("Frete Grátis (compra de R$150): R$" + calculadora.calcular(10, 150));
    }
}
