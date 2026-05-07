// Salve este arquivo como: SorveteApp.java
// Implementação didática do padrão Decorator (estilo Refactoring Guru)
// com classe abstrata base para os decoradores.
//
// Estrutura:
// - interface Sorvete
// - componente concreto SorveteSimples
// - classe abstrata SorveteDecorator (guarda referência e delega)
// - decoradores concretos (Chocolate, Caramelo, Granulado, Chantilly)
// - classe SorveteApp (main de demonstração)

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
class SorveteSimples implements Sorvete {
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
// 3) Classe abstrata base do Decorator
//    Guarda a referência ao "wrappee" e delega por padrão
// -----------------------------
abstract class SorveteDecorator implements Sorvete {
    protected final Sorvete sorvete; // objeto sendo decorado (wrappee)

    protected SorveteDecorator(Sorvete sorvete) {
        this.sorvete = sorvete;
    }

    @Override
    public String getDescricao() {
        // delega para o objeto interno
        return sorvete.getDescricao();
    }

    @Override
    public double getPreco() {
        // delega para o objeto interno
        return sorvete.getPreco();
    }
}

// -----------------------------
// 4) Decoradores concretos
// -----------------------------

// Cobertura de chocolate: +descrição e +1.50
class CoberturaChocolate extends SorveteDecorator {
    public CoberturaChocolate(Sorvete sorvete) { super(sorvete); }

    @Override public String getDescricao() {
        return super.getDescricao() + " + cobertura de chocolate";
    }
    @Override public double getPreco() {
        return super.getPreco() + 1.50;
    }
}

// Cobertura de caramelo: +descrição e +1.20
class CoberturaCaramelo extends SorveteDecorator {
    public CoberturaCaramelo(Sorvete sorvete) { super(sorvete); }

    @Override public String getDescricao() {
        return super.getDescricao() + " + cobertura de caramelo";
    }
    @Override public double getPreco() {
        return super.getPreco() + 1.20;
    }
}

// Granulado colorido: +descrição e +0.80
class GranuladoColorido extends SorveteDecorator {
    public GranuladoColorido(Sorvete sorvete) { super(sorvete); }

    @Override public String getDescricao() {
        return super.getDescricao() + " + granulado colorido";
    }
    @Override public double getPreco() {
        return super.getPreco() + 0.80;
    }
}

// Chantilly extra: +descrição e +1.00
class ChantillyExtra extends SorveteDecorator {
    public ChantillyExtra(Sorvete sorvete) { super(sorvete); }

    @Override public String getDescricao() {
        return super.getDescricao() + " + chantilly extra";
    }
    @Override public double getPreco() {
        return super.getPreco() + 1.00;
    }
}

// -----------------------------
// 5) Demonstração (main)
// -----------------------------
public class SorveteApp {
    public static void main(String[] args) {
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
    }
}
