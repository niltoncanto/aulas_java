// AvisoExpress.java
// Clone básico do NotificaçãoExpress para praticar interfaces e polimorfismo.

import java.util.Objects;

// 1) Interface do canal de aviso
interface CanalAviso {
    void enviar(String destinatario, String mensagem);
}

// 2a) Implementação: aviso por E-MAIL
class AvisoEmail implements CanalAviso {
    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.println("[EMAIL] Para: " + destinatario + " | Msg: " + mensagem);
    }
}

// 2b) Implementação: aviso por PUSH (app)
class AvisoPush implements CanalAviso {
    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.println("[PUSH]  Para: " + destinatario + " | Msg: " + mensagem);
    }
}

// 3) Classe principal
public class AvisoResponsavel {
    public static void main(String[] args) {
        // Troque entre "email" e "push" para testar
        String canal = "email"; // ou "push"

        // Referência pelo tipo da interface
        CanalAviso avisador;

        if (Objects.equals(canal.toLowerCase(), "email")) {
            avisador = new AvisoEmail();
        } else {
            avisador = new AvisoPush();
        }

        // Dispara o aviso simulado
        String destinatario = "responsavel@exemplo.com";
        String mensagem = "Lembrete: reunião pedagógica amanhã às 18h.";
        avisador.enviar(destinatario, mensagem);
    }
}
