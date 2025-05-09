/* 
FormaPagamentoStrategy: Interface da estratégia.
CartaoCreditoStrategy, PayPalStrategy, TransferenciaBancariaStrategy: Estratégias concretas.
LojaOnline: Classe que usa a estratégia para finalizar a compra.
Main: Simula compras com diferentes formas de pagamento. 
*/
// Interface que define a estratégia de pagamento
interface FormaPagamentoStrategy {
    String processarPagamento(double valor);
}

// Estratégia concreta: pagamento com cartão de crédito
class CartaoCreditoStrategy implements FormaPagamentoStrategy {
    @Override
    public String processarPagamento(double valor) {
        return "Pagamento de R$" + valor + " realizado com Cartão de Crédito (taxa de 2,5%).";
    }
}

// Estratégia concreta: pagamento com PayPal
class PayPalStrategy implements FormaPagamentoStrategy {
    @Override
    public String processarPagamento(double valor) {
        return "Pagamento de R$" + valor + " realizado via PayPal (taxa de 3%).";
    }
}

// Estratégia concreta: pagamento com transferência bancária
class TransferenciaBancariaStrategy implements FormaPagamentoStrategy {
    @Override
    public String processarPagamento(double valor) {
        return "Pagamento de R$" + valor + " realizado por Transferência Bancária (sem taxa).";
    }
}

// Classe LojaOnline que usa uma estratégia de pagamento
class LojaOnline {
    private FormaPagamentoStrategy formaPagamento;

    // A estratégia é passada no momento da criação da loja
    public LojaOnline(FormaPagamentoStrategy formaPagamento) { //agregaçao
        this.formaPagamento = formaPagamento;
    }

    // Método que finaliza a compra usando a estratégia fornecida
    public void finalizarCompra(double valor) {
        String confirmacao = formaPagamento.processarPagamento(valor);
        System.out.println(confirmacao);
    }
}

// Classe principal para simular o sistema
public class Main1 {
    public static void main(String[] args) {
        // Cliente escolhe pagar com cartão de crédito
        LojaOnline loja1 = new LojaOnline(new CartaoCreditoStrategy());
        loja1.finalizarCompra(250.00);

        // Cliente escolhe pagar com PayPal
        LojaOnline loja2 = new LojaOnline(new PayPalStrategy());
        loja2.finalizarCompra(150.00);

        // Cliente escolhe transferência bancária
        LojaOnline loja3 = new LojaOnline(new TransferenciaBancariaStrategy());
        loja3.finalizarCompra(300.00);
    }
}
