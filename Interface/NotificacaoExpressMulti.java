// NotificacaoExpress.java
// Versão minimalista: usa lista de Notificador e dados paralelos, sem criar novas classes

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

// Classe principal
public class NotificacaoExpressMulti {
    public static void main(String[] args) {
        // Lista de canais de notificação (interface)
        List<Notificador> canais = new ArrayList<>();
        // Listas paralelas para destinatários e mensagens
        List<String> destinatarios = new ArrayList<>();
        List<String> mensagens = new ArrayList<>();

        // Adiciona várias notificações (cada posição corresponde a um canal + destinatário + mensagem)
        canais.add(new EmailNotificador());
        destinatarios.add("aluno1@exemplo.com");
        mensagens.add("Bem-vindo ao curso!");

        canais.add(new SmsNotificador());
        destinatarios.add("+55 11 99999-9999");
        mensagens.add("Seu código é 123456");

        canais.add(new EmailNotificador());
        destinatarios.add("aluno2@exemplo.com");
        mensagens.add("Lembrete: prova amanhã às 10h.");

        // Dispara todos via for
        for (int i = 0; i < canais.size(); i++) {
            Notificador n = canais.get(i);
            String dest = destinatarios.get(i);
            String msg = mensagens.get(i);
            n.enviar(dest, msg);
        }
    }
}
