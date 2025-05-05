// Interface comum para todas as estratégias
interface CalculoTarifaStrategy {
    double calcularTarifa(double valorTransacao);
}

// Estratégia 1: tarifa como 2% do valor da transação
class TarifaPadraoStrategy implements CalculoTarifaStrategy {
    public double calcularTarifa(double valorTransacao) {
        return valorTransacao * 0.02;
    }
}

// Estratégia 2: tarifa fixa de R$5,00 por transação
class TarifaVIPStrategy implements CalculoTarifaStrategy {
    public double calcularTarifa(double valorTransacao) {
        return 5.00;
    }
}

// Estratégia 3: fórmula especial considerando múltiplos fatores (simplificado)
class TarifaEspecialStrategy implements CalculoTarifaStrategy {
    public double calcularTarifa(double valorTransacao) {
        return (valorTransacao * 0.015) + 1.50; // Exemplo de fórmula complexa
    }
}

// Classe principal que usa uma estratégia para calcular a tarifa
class Pagamento {
    private CalculoTarifaStrategy estrategia;

    public Pagamento(CalculoTarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    // Processa o pagamento com base na estratégia e valor informado
    public double processarPagamento(double valorTransacao) {
        return estrategia.calcularTarifa(valorTransacao);
    }
}

// Classe de demonstração
public class Main {
    public static void main(String[] args) {
        Pagamento padrao = new Pagamento(new TarifaPadraoStrategy());
        Pagamento vip = new Pagamento(new TarifaVIPStrategy());
        Pagamento especial = new Pagamento(new TarifaEspecialStrategy());

        double valor = 200.0;

        System.out.println("Tarifa Padrão: R$" + padrao.processarPagamento(valor));
        System.out.println("Tarifa VIP: R$" + vip.processarPagamento(valor));
        System.out.println("Tarifa Especial: R$" + especial.processarPagamento(valor));
    }
}
