import java.util.Objects;
interface CanalAviso{
     public void enviar(String destinatario, String mensagem);
}

class Whatsapp implements CanalAviso{
    public void enviar(String destinatario, String mensagem){
        System.out.println("Enviando whatsapp");
    }

}

class Push implements CanalAviso{
    public void enviar(String destinatario, String mensagem){
        System.out.println("enviando Push notification");
    }
}

public class AvisoResp{
    public static void main(String[] args){
        String canal = "whatsapp";
        CanalAviso aviso = null;
        if(canal.equalsIgnoreCase("whatsapp")){
            aviso = new Whatsapp();
        }
        if(canal.equalsIgnoreCase("push")){
            aviso = new Push();
        }
        aviso.enviar("1198959123456", "Seu filho sumiu!");
    }
}