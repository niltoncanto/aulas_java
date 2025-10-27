// NotificacaoExpress.java
// Demonstração simples do uso de interfaces em Java

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

// Classe principal para testar
public class NotificacaoExpress {
    public static void main(String[] args) {
        // Você pode trocar para "sms" para testar a outra implementação
        String canal = "email"; 

        Notificador notificador;

        if (canal.equalsIgnoreCase("email")) {
            notificador = new EmailNotificador();
        } else {
            notificador = new SmsNotificador();
        }

        // Enviar mensagem simulada
        notificador.enviar("aluno@exemplo.com", "Bem-vindo ao curso!");
    }
}
