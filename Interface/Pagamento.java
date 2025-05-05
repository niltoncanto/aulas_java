// Interface define um contrato para meios de pagamento
interface Pagamento {
    void pagar(double valor);  // Método obrigatório para todas as classes que implementam a interface
}

// Implementação para pagamento via Cartão de Crédito
class CartaoCredito implements Pagamento {
    private String numeroCartao;

    public CartaoCredito(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    @Override
    public void pagar(double valor) {
        System.out.println("Pagamento de R$" + valor + " realizado com Cartão de Crédito: " + numeroCartao);
    }
}

// Implementação para pagamento via Pix
class Pix implements Pagamento {
    private String chavePix;

    public Pix(String chavePix) {
        this.chavePix = chavePix;
    }

    @Override
    public void pagar(double valor) {
        System.out.println("Pagamento de R$" + valor + " realizado via Pix para a chave: " + chavePix);
    }
}

public class Pagamento {
    public static void main(String[] args) {
        // Criando formas de pagamento
        Pagamento cartao = new CartaoCredito("1234-5678-9876-5432");
        Pagamento pix = new Pix("email@exemplo.com");

        // Realizando pagamentos
        cartao.pagar(100.00);
        pix.pagar(50.00);
    }
}
