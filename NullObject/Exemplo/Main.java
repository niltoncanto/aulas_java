// Classe abstrata representando um Cliente
abstract class Customer {
    abstract boolean isNull();
    abstract Plan getPlan();
}

// Classe concreta para um cliente real
class RealCustomer extends Customer {
    private Plan plan;

    public RealCustomer(Plan plan) {
        this.plan = plan;
    }

    @Override
    boolean isNull() {
        return false;
    }

    @Override
    Plan getPlan() {
        return this.plan;
    }
}

// Classe Null Object que substitui o uso de null
class NullCustomer extends Customer {
    @Override
    boolean isNull() {
        return true;
    }

    @Override
    Plan getPlan() {
        return new NullPlan(); // retorna um plano padrão
    }
}

// Interface ou classe representando um plano
interface Plan {
    void executar();
}

// Implementação nula do plano
class NullPlan implements Plan {
    @Override
    public void executar() {
        System.out.println("Plano padrão: nenhum serviço contratado.");
    }
}

