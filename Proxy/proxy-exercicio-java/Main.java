import java.util.ArrayList;
import java.util.List;

// Classe de teste/simulação do comportamento do padrão Proxy
public class Main {
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
