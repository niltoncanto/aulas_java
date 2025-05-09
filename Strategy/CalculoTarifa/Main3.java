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
interface CalculoTarifaStrategy{
    double calcularTarifa(double valor);
}
class TarifaPadraoStrategy implements CalculoTarifaStrategy{
    @Override
    public double calcularTarifa(double valor){
        return valor*0.02;
    }
}

class TarifaVipStrategy implements CalculoTarifaStrategy{
    @Override
    public double calcularTarifa(double valor){
        return 5.0;
    }
}

class TarifaEspecialStrategy implements CalculoTarifaStrategy{
    @Override
    public double calcularTarifa(double valor){
        return valor*0.015+2.0;
    }
}

class Pagamento{
    private CalculoTarifaStrategy estrategia;

    public Pagamento(CalculoTarifaStrategy estrategia){//agregação
        this.estrategia = estrategia;
    }

    public double processarPagamento(double valor){
        return estrategia.calcularTarifa(valor);
    }
}

public class Main3{
    public static void main(String[] args){
        Pagamento pag1 = new Pagamento(new TarifaPadraoStrategy());
        Pagamento pag2 = new Pagamento(new TarifaVipStrategy());
        Pagamento pag3 = new Pagamento(new TarifaEspecialStrategy());

        double valor = 200.0;

        System.out.println("Tarifa Padrão: R$" + pag1.processarPagamento(valor));
        System.out.println("Tarifa VIP: R$" + pag2.processarPagamento(valor));
        System.out.println("Tarifa Especial: R$" + pag3.processarPagamento(valor));
    }
}
