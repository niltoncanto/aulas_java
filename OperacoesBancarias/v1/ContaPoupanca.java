
public class ContaPoupanca implements OperacoesBancarias {
    private double saldo;
    private String numeroConta;
    private StringBuilder extrato = new StringBuilder();

    // Métodos concretos
    public void depositar(double valor) {
        saldo += valor;
        extrato.append("Depósito de " + valor + "\n");
    }

    public void sacar(double valor) {
        try {
            if (saldo < valor) {
                throw new Exception("Saldo insuficiente");
            }
            saldo -= valor;
            extrato.append("Saque de " + valor + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void transferir(double valor, OperacoesBancarias destino) {
        try {
            if (saldo < valor) {
                throw new Exception("Saldo insuficiente");
            }
            sacar(valor);
            destino.depositar(valor);
            extrato.append("Transferência de " + valor + " para " + destino + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String consultarExtrato() {
        return extrato.toString();
    }

    // Método para retornar o saldo (para uso no método main e mostrarSaldo)
    public double getSaldo() {
        return this.saldo;
    }
}
