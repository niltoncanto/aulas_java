import java.util.Scanner;          // Para ler entradas do usuário
import java.io.FileWriter;         // Para escrever em arquivo
import java.io.PrintWriter;        // Para facilitar a escrita formatada
import java.io.IOException;        // Para tratar erros de I/O


/* ===================== ENUM ===================== */
// Enum representa conjunto fechado de prioridades (tipo seguro)
enum NivelPrioridade {
    BAIXA, MEDIA, ALTA, CRITICA;

    // (Opcional) Retornar descrição amigável
    @Override
    public String toString() {
        switch (this) {
            case BAIXA:   return "BAIXA";
            case MEDIA:   return "MEDIA";
            case ALTA:    return "ALTA";
            case CRITICA: return "CRITICA";
            default:      return name();
        }
    }
}

/* ===================== INTERFACE ===================== */
// Contrato para serviços de atendimento
interface Atendimento {
    String resolverChamado(Chamado chamado);
}

/* ===================== ENTIDADES (COMPOSIÇÃO) ===================== */
// Representa o cliente que abriu o chamado
class Cliente {
    private  String nome;
    private  String email;

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome()  { return nome;  }
    public String getEmail() { return email; }
}

// Representa o chamado; possui um Cliente (composição)
class Chamado {
    private  int id;
    private  String descricao;
    private  NivelPrioridade prioridade;
    private  Cliente cliente;

    public Chamado(int id, String descricao, NivelPrioridade prioridade, Cliente cliente) {
        this.id = id;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.cliente = cliente;
    }

    // Validação de regras mínimas do domínio
    // complemente as regras, só fizemos a primeira validação.
    public void validar() {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente obrigatório.");
        }
    }

    // Getters de leitura
    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public NivelPrioridade getPrioridade() { return prioridade; }
    public Cliente getCliente() { return cliente; }
}

/* ===================== IMPLEMENTAÇÕES DA INTERFACE ===================== */
// Suporte N1: resolve BAIXA e MEDIA
class SuporteBasico implements Atendimento {
    @Override
    public String resolverChamado(Chamado c) {
        // Simulação simples de tratamento
        return String.format("[Suporte Básico] Chamado #%d (%s) resolvido: %s.",
                c.getId(), c.getPrioridade(), c.getDescricao());
    }
}

// Suporte N2: resolve ALTA e CRITICA
class SuporteAvancado implements Atendimento {
    @Override
    public String resolverChamado(Chamado c) {
        // Simulação simples de tratamento
        String acao = "Troca de hardware / intervenção remota avançada";
        return String.format("[Suporte Avançado] Chamado #%d (%s) resolvido: %s.",
                c.getId(), c.getPrioridade(), c.getDescricao());
    }
}

// Classe principal: orquestra o fluxo, interação e persistência
public class ServiceDesk {
    // Sequência simples para IDs dos chamados
    private static int seqId = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Scanner para entradas

        // Exibe as prioridades disponíveis ao usuário
        System.out.print("Prioridades: ");
        for (NivelPrioridade p : NivelPrioridade.values()) {
            System.out.print(p + " ");
        }
        System.out.println("\n");

        try {
            // Lê dados do cliente
            System.out.print("Nome do cliente: ");
            String nome = sc.nextLine();

            System.out.print("E-mail: ");
            String email = sc.nextLine();

            // Lê dados do chamado
            System.out.print("Descrição do chamado: ");
            String descricao = sc.nextLine();

            System.out.print("Prioridade (BAIXA, MEDIA, ALTA, CRITICA): ");
            String entradaPrioridade = sc.nextLine().trim().toUpperCase();

            // Converte texto para enum (pode lançar IllegalArgumentException)
            NivelPrioridade prioridade = NivelPrioridade.valueOf(entradaPrioridade);

            // Composição: Chamado possui Cliente
            Cliente cliente = new Cliente(nome, email);

            // Cria o chamado e valida (pode lançar IllegalArgumentException)
            Chamado chamado = new Chamado(seqId, descricao, prioridade, cliente);
            seqId++;
            chamado.validar();

            // Seleciona atendimento conforme regra de negócio
            Atendimento atendimento = selecionarAtendimento(chamado.getPrioridade());

            // Resolve o chamado
            String resultado = atendimento.resolverChamado(chamado);

            // Exibe o resultado no console
            System.out.println("\n" + resultado);

            // Grava o chamado no arquivo (tratando IOException internamente)
            // desenvolver função static veja final do código!!
            gravarChamado(chamado, resultado);

        } catch (IllegalArgumentException e) {
            // Captura erros de enum inválido, validação de campos, etc.
            System.out.println("Erro nos dados: " + e.getMessage());
        } catch (Exception e) {
            // Qualquer outro erro inesperado
            System.out.println("Erro inesperado: " + e.getMessage());
        } finally {
            sc.close(); // Fecha o Scanner
        }
    }

    // Método utilitário: escolhe a implementação de Atendimento
    private static Atendimento selecionarAtendimento(NivelPrioridade p) {
        switch (p) {
            case BAIXA:
            case MEDIA:
                return new SuporteBasico();
            case ALTA:
            case CRITICA:
                return new SuporteAvancado();
            // default não é necessário pois cobrimos todos os enums,
            // mas retornamos básico por segurança
            default:
                return new SuporteBasico();
        }
    }

    // Persistência: grava uma linha em chamados.txt após a resolução
    // implementar!!!!
     private static void gravarChamado(Chamado chamado, String resultado) {
        return;
     }

}