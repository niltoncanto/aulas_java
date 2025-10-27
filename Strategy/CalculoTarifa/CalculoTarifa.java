/*
Descrição do Problema:
Imagine que você está desenvolvendo um sistema de processamento de pagamentos para uma empresa financeira. 
Este sistema deve ser capaz de calcular o valor a ser pago pelos clientes com base em diferentes estratégias de cálculo de tarifas.
A empresa oferece três estratégias diferentes de cálculo de tarifas:
1. Tarifa Padrão: Para clientes regulares, a tarifa é calculada como uma porcentagem fixa do valor da transação, por exemplo, 2% do valor total.
2. Tarifa VIP: Para clientes VIP, a tarifa é uma taxa fixa por transação, independentemente do valor da transação, por exemplo, R$ 5,00 por transação.
3. Tarifa Especial: Para clientes que se qualificam para uma tarifa especial, a tarifa é calculada com base em uma fórmula complexa que leva em consideração vários fatores, como o valor da transação, o histórico do cliente, etc.
*/
/*
Instruções Parte I:
Seu objetivo é implementar o sistema de processamento de pagamentos usando o padrão de projeto Strategy. 
Crie uma classe principal chamada `Pagamento` que aceita um objeto de estratégia de cálculo de tarifa e um valor de transação como entrada.
Você deve criar três classes de estratégia:
1. `TarifaPadraoStrategy`: Implemente essa classe para calcular a tarifa com base na porcentagem fixa.
2. `TarifaVIPStrategy`: Implemente essa classe para calcular a tarifa como uma taxa fixa.
3. `TarifaEspecialStrategy`: Implemente essa classe para calcular a tarifa com base na fórmula complexa.
 */
/*
Instruções Parte II:
Cada classe de estratégia deve implementar um método `calcularTarifa` que aceita o valor da transação como entrada e retorna o valor da tarifa calculado de acordo com a estratégia correspondente.
Na classe principal `Pagamento`, você deve ter um método chamado `processarPagamento` que aceita o valor da transação e a estratégia de cálculo de tarifa como entrada e retorna o valor total a ser pago pelo cliente.
Por fim, crie um programa de demonstração que permite ao usuário escolher a estratégia de cálculo de tarifa e calcular a tarifa com base no valor da transação inserido.
Dica: Use a classe `interface` para definir um contrato comum para todas as estratégias de cálculo de tarifas. Cada classe de estratégia implementará essa interface com sua própria lógica de cálculo.
 */

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
