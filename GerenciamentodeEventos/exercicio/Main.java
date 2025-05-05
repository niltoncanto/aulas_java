import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        // Instanciando os 3 eventos
        Evento evento1 = new Evento("Show XYZ", "Estádio 1", LocalDateTime.of(2024, 4, 25, 0, 0), 10);
        Evento evento2 = new Evento("Show K3", "Estádio 2", LocalDateTime.of(2023, 10, 25, 0, 0), 10);
        Evento evento3 = new Evento("Show TUBA", "Estádio 3", LocalDateTime.of(2023, 9, 25, 0, 0), 10);

        // Instanciando os 2 participantes
        Participante participante1 = new Participante("Nilton", "nilton@etapa.com.br");
        Participante participante2 = new Participante("josé", "jose@xyz.com.br");

        // Compras de ingressos
        new Ingresso(participante1, evento1);
        new Ingresso(participante1, evento2);
        new Ingresso(participante1, evento3);
        new Ingresso(participante2, evento3);

        // Mostrando os ingressos de cada participante
        participante1.mostrarIngressos();
        participante2.mostrarIngressos();
        System.out.println(("******************************"));
        // Mostrando os ingressos dos eventos
        evento1.mostraInfoEvento();
        evento2.mostraInfoEvento();
        evento3.mostraInfoEvento();
        System.out.println(("******************************"));

    }
}

