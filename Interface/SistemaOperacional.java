// Interface que define um contrato para drivers de impressora
interface DriverImpressora {
    void conectar();
    void imprimir(String documento);
    void verificarStatus();
}

// Implementação para um driver de impressora HP
class DriverHP implements DriverImpressora {
    @Override
    public void conectar() {
        System.out.println("Conectando a impressora HP...");
    }

    @Override
    public void imprimir(String documento) {
        System.out.println("Imprimindo na impressora HP: " + documento);
    }

    @Override
    public void verificarStatus() {
        System.out.println("Status da HP: Tinta cheia, pronto para imprimir.");
    }
}

// Implementação para um driver de impressora Epson
class DriverEpson implements DriverImpressora {
    @Override
    public void conectar() {
        System.out.println("Conectando a impressora Epson...");
    }

    @Override
    public void imprimir(String documento) {
        System.out.println("Imprimindo na impressora Epson: " + documento);
    }

    @Override
    public void verificarStatus() {
        System.out.println("Status da Epson: Tinta baixa, substituir cartucho.");
    }
}


public class SistemaOperacional {
    public static void imprimirDocumento(DriverImpressora impressora, String documento) {
        impressora.conectar();
        impressora.verificarStatus();
        impressora.imprimir(documento);
    }

    public static void main(String[] args) {
        // Criando diferentes impressoras
        DriverImpressora hp = new DriverHP();
        DriverImpressora epson = new DriverEpson();

        // O Windows pode imprimir sem se preocupar com a marca da impressora
        imprimirDocumento(hp, "Relatório.pdf");
        System.out.println("-----------------");
        imprimirDocumento(epson, "Fatura_123.pdf");
    }
}
