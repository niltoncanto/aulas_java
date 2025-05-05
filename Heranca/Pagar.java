class Pagamento{
    protected Double valor;

    public Pagamento(Double valor){
        this.valor = valor;
    }

    //método polimorfico que será sobrescrito na classe filha
    public void processarPagamento(){
        System.out.println("processando pagamento genérico...");
    }
}

//subclasse cartão de crédito
class CartaoCredito extends Pagamento{
    private String numeroCartao;

    public CartaoCredito(Double valor, String numeroCartao){
        super(valor);
        this.numeroCartao = numeroCartao;
    }

    @Override
    public void processarPagamento(){
        System.out.println("Pagamento de R$" + valor + "processado com cartão de crédito.");
    }

}

class BoletoBancario extends Pagamento{
    private String codigoBarras;

    public BoletoBancario(Double valor, String codigoBarras){
        super(valor);
        this.codigoBarras = codigoBarras;
    }

    @Override
    public void processarPagamento(){
        System.out.println("Pagamento de R$" + valor + "processado com cartão de crédito.");
    }

}

// Classe principal
public class Pagar {
    public static void main(String[] args) {
        // Criando instâncias polimórficas
        Pagamento pagamentoCartao = new CartaoCredito(150.00, "1234-5678-9012-3456");
        Pagamento pagamentoBoleto = new BoletoBancario(300.00, "98765432100012345678901234567890123456789012");

        // Processando pagamentos de forma polimórfica
        realizarPagamento(pagamentoCartao);
        realizarPagamento(pagamentoBoleto);
    }

    // Método polimórfico que aceita qualquer tipo de pagamento
    public static void realizarPagamento(Pagamento pagamento) {
        pagamento.processarPagamento(); // Chamará o método correto da subclasse
    }


}

/*
Herança → A classe Pagamento é a base para CartaoCredito e BoletoBancario, evitando código duplicado.
Polimorfismo → O método processarPagamento() é sobrescrito em cada subclasse, adaptando o comportamento ao tipo de pagamento.
Abstração do Tipo de Pagamento → O método realizarPagamento() recebe qualquer pagamento e executa dinamicamente a lógica correta.
*/



