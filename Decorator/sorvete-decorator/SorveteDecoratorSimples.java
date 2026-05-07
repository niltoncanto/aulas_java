public class SorveteDecoratorSimples {

    // -----------------------------
    // 1) Interface comum
    // -----------------------------
    interface Sorvete {
        String getDescricao();
        double getPreco();
    }

    // -----------------------------
    // 2) Componente concreto (sorvete base)
    // -----------------------------
    static class SorveteSimples implements Sorvete {
        @Override
        public String getDescricao() {
            return "Sorvete de baunilha";
        }

        @Override
        public double getPreco() {
            return 5.00;
        }
    }

    // -----------------------------
    // 3) Decoradores concretos
    //    (cada um implementa Sorvete e mantém um Sorvete interno)
    // -----------------------------

    // Cobertura de chocolate: +descrição e +1.50
    static class CoberturaChocolate implements Sorvete {
        private final Sorvete base;
        public CoberturaChocolate(Sorvete base) { this.base = base; }
        @Override public String getDescricao() { return base.getDescricao() + " + cobertura de chocolate"; }
        @Override public double getPreco() { return base.getPreco() + 1.50; }
    }

    // Cobertura de caramelo: +descrição e +1.20
    static class CoberturaCaramelo implements Sorvete {
        private final Sorvete base;
        public CoberturaCaramelo(Sorvete base) { this.base = base; }
        @Override public String getDescricao() { return base.getDescricao() + " + cobertura de caramelo"; }
        @Override public double getPreco() { return base.getPreco() + 1.20; }
    }

    // Granulado colorido: +descrição e +0.80
    static class GranuladoColorido implements Sorvete {
        private final Sorvete base;
        public GranuladoColorido(Sorvete base) { this.base = base; }
        @Override public String getDescricao() { return base.getDescricao() + " + granulado colorido"; }
        @Override public double getPreco() { return base.getPreco() + 0.80; }
    }

    // Chantilly extra: +descrição e +1.00
    static class ChantillyExtra implements Sorvete {
        private final Sorvete base;
        public ChantillyExtra(Sorvete base) { this.base = base; }
        @Override public String getDescricao() { return base.getDescricao() + " + chantilly extra"; }
        @Override public double getPreco() { return base.getPreco() + 1.00; }
    }

    // -----------------------------
    // 4) Demonstração (main)
    // -----------------------------
    public static void main(String[] args) {
        // Formatação de preço com 2 casas (simples)
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");

        // Combinação 1: baunilha + chocolate + granulado + chantilly
        Sorvete pedido1 = new SorveteSimples();
        pedido1 = new CoberturaChocolate(pedido1);
        pedido1 = new GranuladoColorido(pedido1);
        pedido1 = new ChantillyExtra(pedido1);
        System.out.println(pedido1.getDescricao() + " - R$" + df.format(pedido1.getPreco()));

        // Combinação 2: baunilha + caramelo + chocolate
        Sorvete pedido2 = new SorveteSimples();
        pedido2 = new CoberturaCaramelo(pedido2);
        pedido2 = new CoberturaChocolate(pedido2);
        System.out.println(pedido2.getDescricao() + " - R$" + df.format(pedido2.getPreco()));

        // Observações didáticas:
        // - A ordem das coberturas pode ser alterada livremente.
        // - Todos os decoradores implementam Sorvete e recebem um Sorvete no construtor.
        // - Não há classe abstrata; os decoradores são concretos e triviais.
    }
}
