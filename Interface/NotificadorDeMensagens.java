// Interface que define o contrato para qualquer canal de notificação
public interface Notificavel {
    // Método que todas as classes deverão implementar
    void enviar(String destinatario, String mensagem);
}
// Classe que simula o envio de e-mails
public class EmailNotificacao implements Notificavel {
    private String servidorSMTP;
    private int porta;

    public EmailNotificacao(String servidorSMTP, int porta) {
        this.servidorSMTP = servidorSMTP;
        this.porta = porta;
    }

    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.println("[EMAIL] Enviando e-mail para " + destinatario + " usando " + servidorSMTP + ":" + porta);
        System.out.println("Mensagem: " + mensagem);
    }
}
// Classe que simula o envio de SMS
public class SmsNotificacao implements Notificavel {
    private String numeroOrigem;

    public SmsNotificacao(String numeroOrigem) {
        this.numeroOrigem = numeroOrigem;
    }

    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.println("[SMS] Enviando SMS para " + destinatario);
        System.out.println("De: " + numeroOrigem);
        System.out.println("Mensagem: " + mensagem);
    }
}
// Classe que simula o envio de notificações push
public class PushNotificacao implements Notificavel {
    private String app;

    public PushNotificacao(String app) {
        this.app = app;
    }

    @Override
    public void enviar(String destinatario, String mensagem) {
        System.out.println("[PUSH] Enviando notificação para " + destinatario + " no app " + app);
        System.out.println("Mensagem: " + mensagem);
    }
}
// Classe responsável por gerenciar o envio de mensagens
public class GerenciadorDeNotificacoes {

    // Recebe um canal de notificação e usa polimorfismo para enviar a mensagem
    public void notificarUsuario(Notificavel canal, String destinatario, String mensagem) {
        canal.enviar(destinatario, mensagem);
    }
}
// Classe principal para testes
public class NotificadorDeMensagens {
    public static void main(String[] args) {
        // Instanciando canais de notificação
        Notificavel email = new EmailNotificacao("smtp.gmail.com", 587);
        Notificavel sms = new SmsNotificacao("(11)4002-8922");
        Notificavel push = new PushNotificacao("NotificaJá");

        // Criando o gerenciador
        GerenciadorDeNotificacoes gerenciador = new GerenciadorDeNotificacoes();

        // Enviando notificações usando polimorfismo
        System.out.println("=== Testando envio por E-MAIL ===");
        gerenciador.notificarUsuario(email, "maria@exemplo.com", "Olá, Maria!");

        System.out.println("\n=== Testando envio por SMS ===");
        gerenciador.notificarUsuario(sms, "(11)98765-4321", "Olá, Maria!");

        System.out.println("\n=== Testando envio por PUSH ===");
        gerenciador.notificarUsuario(push, "usuario123", "Olá, Maria!");

        // Desafio extra: lista de canais
        System.out.println("\n=== Enviando para todos os canais ===");
        Notificavel[] canais = {email, sms, push};
        for (Notificavel canal : canais) {
            gerenciador.notificarUsuario(canal, "destinatario-teste", "Mensagem em massa.");
        }
    }
}
