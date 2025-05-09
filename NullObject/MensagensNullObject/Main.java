// Interface comum para todos os tipos de notificação
interface Notificacao {
    void enviar(String mensagem);
}
// Implementação concreta para envio de e-mails
class NotificacaoEmail implements Notificacao {
    @Override
    public void enviar(String mensagem) {
        System.out.println("Enviando E-MAIL: " + mensagem);
    }
}
// Implementação concreta para envio de SMS
class NotificacaoSMS implements Notificacao {
    @Override
    public void enviar(String mensagem) {
        System.out.println("Enviando SMS: " + mensagem);
    }
}
// Implementação nula (não envia notificação)
class NotificacaoNula implements Notificacao {
    @Override
    public void enviar(String mensagem) {
        // Não faz nada
        System.out.println("Nenhuma notificação enviada (NotificacaoNula).");
    }
}
// Fábrica responsável por criar o tipo adequado de notificação
class FabricaDeNotificacao {

    // Método estático que retorna o tipo de notificação com base na escolha
    public static Notificacao criar(String tipo) {
        if (tipo == null) return new NotificacaoNula();

        switch (tipo.toLowerCase()) {
            case "email":
                return new NotificacaoEmail();
            case "sms":
                return new NotificacaoSMS();
            default:
                return new NotificacaoNula();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        // Escolhas simuladas (poderiam vir de um arquivo de configuração)
        String escolha1 = "email";
        String escolha2 = "sms";
        String escolha3 = "nenhum";

        // Instanciação dinâmica via fábrica
        Notificacao n1 = FabricaDeNotificacao.criar(escolha1);
        Notificacao n2 = FabricaDeNotificacao.criar(escolha2);
        Notificacao n3 = FabricaDeNotificacao.criar(escolha3);

        // Envio de mensagens
        n1.enviar("Bem-vindo ao sistema!");
        n2.enviar("Sua senha foi alterada.");
        n3.enviar("Isso não deve ser enviado.");
    }
}
