import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

// Classe Proxy que implementa a mesma interface que o RealVideo
// Sua função é adiar (lazy) a criação do objeto "pesado" até o momento necessário,
// além de adicionar um comportamento extra de logging de acesso.
public class ProxyVideo implements Video {
    // Referência "preguiçosa" para o objeto real
    private RealVideo realVideo;
    // Nome do arquivo do vídeo; usado para instanciar o RealVideo sob demanda
    private final String fileName;
    // Caminho do arquivo de log (o enunciado pede registrar dentro do Proxy)
    private final Path logFilePath = Path.of("log.txt");

    // Construtor do Proxy recebe o nome do arquivo do vídeo
    public ProxyVideo(String fileName) {
        // Armazena o nome do arquivo
        this.fileName = fileName;
    }

    // Implementação do método play()
    @Override
    public void play() {
        // Se o RealVideo ainda não foi criado, cria agora (lazy initialization)
        if (realVideo == null) {
            realVideo = new RealVideo(fileName);
        }

        // Delegação da chamada para o objeto real (o vídeo de fato)
        realVideo.play();

        // Após delegar, realiza o comportamento adicional de log de acesso
        logAccess();
    }

    // Método privado para registrar o acesso ao vídeo no arquivo log.txt
    private void logAccess() {
        // Monta a linha de log com data/hora e nome do arquivo de vídeo
        String line = String.format("[%s] PLAY -> %s%n", LocalDateTime.now(), fileName);
        try {
            // Garante que o arquivo exista, criando se necessário
            if (Files.notExists(logFilePath)) {
                Files.createFile(logFilePath);
            }
            // Escreve no fim do arquivo (APPEND)
            Files.writeString(
                logFilePath,
                line,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            // Em caso de falha no log, não interrompemos a execução do sistema;
            // apenas informamos no console para diagnóstico.
            System.err.println("Falha ao registrar log de acesso: " + e.getMessage());
        }
    }
}
