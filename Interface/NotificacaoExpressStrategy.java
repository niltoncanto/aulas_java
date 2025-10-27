// NotificacaoExpress.java
// Versão com lista: múltiplas notificações disparadas em loop,
// usando polimorfismo via interface Notificador.

import java.util.ArrayList;
import java.util.List;

// Interface
interface Notificador {
    void enviar(String destinatario, String mensagem);
}

// Implementação para Email
class EmailNotificador implements Notificador {
    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.println("Enviando EMAIL para " + destinatario + ": " + mensagem);
    }
}

// Implementação para SMS
class SmsNotificador implements Notificador {
    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.println("Enviando SMS para " + destinatario + ": " + mensagem);
    }
}

// (Opcional) outra implementação rápida para mostrar extensibilidade
class WhatsAppNotificador implements Notificador {
    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.println("Enviando WHATSAPP para " + destinatario + ": " + mensagem);
    }
}

// Um "pedido" de notificação: canal + dados
class PedidoNotificacao {
    private final Notificador canal;   // referência pela INTERFACE
    private final String destinatario;
    private final String mensagem;

    public PedidoNotificacao(Notificador canal, String destinatario, String mensagem) {
        this.canal = canal;
        this.destinatario = destinatario;
        this.mensagem = mensagem;
    }

    public void disparar() {
        canal.enviar(destinatario, mensagem); // polimorfismo aqui
    }
}

// Classe principal para testar
public class NotificacaoExpressStrategy {
    public static void main(String[] args) {
        // Lista de pedidos de notificação
        List<PedidoNotificacao> fila = new ArrayList<>();

        // Adiciona vários pedidos, cada um com um canal diferente
        fila.add(new PedidoNotificacao(new EmailNotificador(),
                "aluno@exemplo.com", "Bem-vindo ao curso!"));
        fila.add(new PedidoNotificacao(new SmsNotificador(),
                "+55 11 99999-9999", "Seu código é 123456"));
        fila.add(new PedidoNotificacao(new WhatsAppNotificador(),
                "+55 11 98888-7777", "Reunião às 14h. Pode confirmar?"));

        // Dispara todos via for: quem decide o "como" é a interface Notificador
        for (PedidoNotificacao pedido : fila) {
            pedido.disparar();
        }
    }
}
