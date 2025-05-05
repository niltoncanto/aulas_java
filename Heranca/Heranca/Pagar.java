class Pagamento{
    protected Double valor;
    public Pagamento(Double valor){
        this.valor = valor;
    }

    public void processarPagamento(){
        System.out.println("Processando pagamento genérico...");
    }
}

class CartaoCredito extends Pagamento{
    protected String numeroCartao;
    public CartaoCredito(Double valor, String numeroCartao){
        super(valor);
        this.numeroCartao = numeroCartao;
    }
    
    @Override
    public void processarPagamento(){
        System.out.println("Pagamento efetuado no valor de R$ "+ valor + "com cartão final número:" + numeroCartao.substring(numeroCartao.length()-4));
    }
}

class BoletoBancario extends Pagamento{
    protected String codigoBarras;
    public BoletoBancario(Double valor, String codigoBarras){
        super(valor);
        this.codigoBarras = codigoBarras;
    }
    @Override
    public void processarPagamento(){
        System.out.println("Pagamento efetuado no valor de R$ "+ valor + "código de barras" + codigoBarras.substring(codigoBarras.length()-4));
    }
}
public class Pagar {
    public static void main(String[] args){
        Pagamento cartao = new CartaoCredito(100.2, "12345678999999");
        Pagamento boleto = new BoletoBancario(200.2, "12122112121212121212121221213223122332");
        realizarPagamento(cartao);
        realizarPagamento(boleto);
    }
    public static void realizarPagamento(Pagamento pagamento){
        pagamento.processarPagamento();
    }
}
