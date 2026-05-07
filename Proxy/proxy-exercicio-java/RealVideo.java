import java.time.LocalDateTime;

// Classe concreta que representa um vídeo "pesado" (carregamento custoso)
public class RealVideo implements Video {
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
