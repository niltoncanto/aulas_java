// Define o contrato comum para todos os tipos de cliente (reais ou "nulos").
interface Customer {
    boolean isNull(); //O método isNull() permite ao cliente (facultativamente) verificar se é um Null Object.
    Plan getPlan();   //O método getPlan() retorna o plano associado ao cliente.
}

//Representa um cliente válido.
class RealCustomer implements Customer {
    private Plan plan;
    // Possui um plano associado.
    public RealCustomer(Plan plan) {
        this.plan = plan;
    }

    //Retorna false em isNull() porque é um cliente real.
    @Override
    public boolean isNull() {
        return false;
    }

    //getPlan() devolve o plano contratado.
    @Override
    public Plan getPlan() {
        return this.plan;
    }
}

// Representa um cliente "nulo" ou ausente, mas sem retornar null.  
class NullCustomer implements Customer {
    //Retorna true para isNull().
    @Override
    public boolean isNull() {
        return true;
    }

    //Retorna um plano padrão (NullPlan), que não faz nada perigoso, evitando erros como NullPointerException.
    @Override
    public Plan getPlan() {
        return new NullPlan(); // retorna um plano padrão
    }
}

// Define a interface comum para qualquer tipo de plano.
interface Plan {
    void executar();
}

// ImplementaMain1do plano que não executa nada significativo.
class NullPlan implements Plan {
    //Pode ser usada para representar a ausência de um plano sem lançar erro.
    @Override
    public void executar() {
        System.out.println("Plano padrão: nenhum serviço contratado.");
    }
}

// Classe concreta de plano premium que implementa a interface Plan
class PlanoPremium implements Plan {
    @Override
    public void executar() {
        System.out.println("Executando Plano Premium: acesso completo liberado.");
    }
}

// Classe Main para testar o padrão Null Object
public class Main {
    public static void main(String[] args) {
        
        // Cliente com plano premium (real)
        Customer cliente1 = new RealCustomer(new PlanoPremium());
        
        // Cliente nulo (substitui uso de null)
        Customer cliente2 = new NullCustomer();

        // Executando plano do cliente1
        System.out.println("Cliente 1:");
        cliente1.getPlan().executar(); // Saída esperada: Executando Plano Premium

        // Executando plano do cliente2
        System.out.println("\nCliente 2:");
        cliente2.getPlan().executar(); // Saída esperada: Plano padrão

        // Verificando uso de isNull (opcional)
        System.out.println("\nÉ cliente1 nulo? " + cliente1.isNull());
        System.out.println("É cliente2 nulo? " + cliente2.isNull());
    }
}

