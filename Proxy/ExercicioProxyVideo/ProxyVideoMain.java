import java.time.LocalDateTime;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

// Interface comum que define o contrato para reprodução de vídeo
interface Video {
    // Método que representa a ação de reproduzir o vídeo
    void play();
}

// Classe concreta que representa um vídeo "pesado" (carregamento custoso)
class RealVideo implements Video {
    // Nome do arquivo de vídeo (por exemplo, "video1.mp4")
    private final String fileName;

    // Construtor recebe o nome do arquivo e realiza o carregamento do recurso
    public RealVideo(String fileName) {
        // Armazena o nome do arquivo internamente
        this.fileName = fileName;
        // Simula um processo pesado de carregamento (ex.: leitura do disco, decodificação etc.)
        loadFromDisk(fileName);
    }

    // Método privado que simula o carregamento do arquivo de vídeo
    private void loadFromDisk(String fileName) {
        // Exibe uma mensagem para indicar o carregamento do recurso "pesado"
        System.out.println("Carregando vídeo do disco: " + fileName + " (" + LocalDateTime.now() + ")");
    }

    // Implementação do método play() definido pela interface Video
    @Override
    public void play() {
        // Exibe uma mensagem para indicar que o vídeo está sendo reproduzido
        System.out.println("Reproduzindo vídeo: " + fileName);
    }

    // Getter opcional para o nome do arquivo
    public String getFileName() {
        return fileName;
    }
}

// Classe Proxy que implementa a mesma interface que o RealVideo
// Sua função é adiar (lazy) a criação do objeto "pesado" até o momento necessário,
// além de adicionar um comportamento extra de logging de acesso.
class ProxyVideo implements Video {
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

// Classe de teste/simulação do comportamento do padrão Proxy
public class ProxyVideoMain {
    public static void main(String[] args) {
        // Cria uma lista com nomes de arquivos de vídeo disponíveis
        List<String> catalogo = new ArrayList<>();
        catalogo.add("video1.mp4");
        catalogo.add("video2.mp4");
        catalogo.add("video3.mp4");

        // Imprime que o catálogo foi carregado (sem carregar vídeos de fato)
        System.out.println("[App] Lista de vídeos carregada.");

        // Cria uma lista de proxies para representar cada vídeo do catálogo
        List<Video> videos = new ArrayList<>();
        for (String nomeArquivo : catalogo) {
            // Para cada arquivo, cria um ProxyVideo (nenhum RealVideo é carregado ainda)
            videos.add(new ProxyVideo(nomeArquivo));
        }

        // Simula o usuário escolhendo assistir ao primeiro vídeo
        System.out.println("[App] Usuário escolheu assistir: video1.mp4");
        videos.get(0).play(); // Na primeira vez, o RealVideo é criado e carregado

        // Simula o usuário repetindo o mesmo vídeo (não deve carregar novamente)
        System.out.println("\n[App] Usuário escolheu assistir novamente: video1.mp4");
        videos.get(0).play(); // Agora o vídeo já está carregado; apenas reproduz

        // Simula o usuário escolhendo outro vídeo
        System.out.println("\n[App] Usuário escolheu assistir: video3.mp4");
        videos.get(2).play(); // Cria e carrega o RealVideo para video3.mp4 na primeira vez
    }
}