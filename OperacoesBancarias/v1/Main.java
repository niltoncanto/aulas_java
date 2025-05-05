
public class Main {
    public static void main(String[] args) {
        ContaCorrente cc = new ContaCorrente();
        ContaPoupanca cp = new ContaPoupanca();

        cc.depositar(1000);
        cc.mostrarSaldo(cc.getSaldo());
        cc.sacar(500);
        cc.mostrarSaldo(cc.getSaldo());

        cp.depositar(2000);
        cp.mostrarSaldo(cp.getSaldo());
        cp.sacar(1000);
        cp.mostrarSaldo(cp.getSaldo());

        cc.transferir(200, cp);
        cc.mostrarSaldo(cc.getSaldo());
        cp.mostrarSaldo(cp.getSaldo());

        System.out.println("Extrato da Conta Corrente: \n" + cc.consultarExtrato());
        System.out.println("Extrato da Conta Poupança: \n" + cp.consultarExtrato());

        /*
         * StringBuilder sb = new StringBuilder();
         * 
         * // Adicionando texto
         * sb.append("Olá, ");
         * sb.append("mundo!");
         * 
         * // Inserindo texto
         * sb.insert(5, "querido ");
         * 
         * // Deletando texto
         * sb.delete(5, 13);
         * 
         * // Invertendo a string
         * sb.reverse();
         * 
         * System.out.println(sb.toString());
         */

    }
}
