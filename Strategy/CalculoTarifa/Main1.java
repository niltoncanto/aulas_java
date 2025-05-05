interface CalculoTarifaStrategy{
    Double CalculoTarifa(double ValorTransacao);
}

class TarifaPadraoStrategy implements CalculoTarifaStrategy{
    public Double CalculoTarifa(double ValorTransacao){
        return ValorTransacao*0.02;
    }
}

class TarifaVipStrategy implements CalculoTarifaStrategy{
    public Double CalculoTarifa(double ValorTransacao){
        return 5.00;
    }
}

class TarifaEspecialStrategy implements CalculoTarifaStrategy{
    public Double CalculoTarifa(double ValorTransacao){
        return ValorTransacao*0.015 + 1.5; //fórmula especial
    }
}

class Pagamento{
    private CalculoTarifaStrategy estrategia;
    //composicao
    public Pagamento(CalculoTarifaStrategy estrategia){
        this.estrategia = estrategia;
    }

    public double processarPagamento(double valorTransacao){
        return estrategia.CalculoTarifa(valorTransacao);
    }

}

public class Main1{
    public static void main(String[] args){
        Pagamento padrao = new Pagamento(new TarifaPadraoStrategy());
        Pagamento vip = new Pagamento(new TarifaVipStrategy());
        Pagamento especial = new Pagamento(new TarifaEspecialStrategy());

        double valor = 1000.00;
        System.out.println("Tarifa Padrão: R$" + padrao.processarPagamento(valor));
        System.out.println("Tarifa VIP: R$" + vip.processarPagamento(valor));
        System.out.println("Tarifa Especial: R$" + especial.processarPagamento(valor));

    }
}