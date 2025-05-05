public interface OperacoesBancarias {
    void depositar(double valor);

    void sacar(double valor);

    void transferir(double valor, OperacoesBancarias destino);

    String consultarExtrato();

    default void mostrarSaldo(double saldo) {
        System.out.println("Saldo atual: " + saldo);
    }
}
