import java.util.Scanner;          // Para ler entradas do usuário
import java.io.FileWriter;         // Para escrever em arquivo
import java.io.PrintWriter;        // Para facilitar a escrita formatada
import java.io.IOException;        // Para tratar erros de I/O

/* ===================== ENUM ===================== */
// Enum representa conjunto fechado de prioridades (tipo seguro)
enum NivelPrioridade {

}

/* ===================== INTERFACE ===================== */
// Contrato para serviços de atendimento
interface Atendimento {

}

/* ===================== ENTIDADES (COMPOSIÇÃO) ===================== */
// Representa o cliente que abriu o chamado (nome e email)
class Cliente {

}

// Representa o chamado; possui um Cliente (composição)
class Chamado {


}

/* ===================== IMPLEMENTAÇÕES DA INTERFACE ===================== */
// Suporte N1: resolve BAIXA e MEDIA
class SuporteBasico implements Atendimento {

}

// Suporte N2: resolve ALTA e CRITICA
class SuporteAvancado implements Atendimento {

}

/* ===================== CLASSE PRINCIPAL ===================== */
// Classe principal: orquestra o fluxo, interação e persistência
public class ServiceDeskEx {
    
}