/*
 * ============================================================
 *  Exercício de POO — Sistema simples de gerenciamento de músicas
 *  Conteúdos: modificadores de visibilidade, classes concretas,
 *             sobrescrita de método, sobrecarga de método/ctor,
 *             getters/setters com validação.
 *
 *  Como compilar/executar:
 *      javac AppMusicas.java
 *      java AppMusicas
 *
 *  Observação:
 *  - Coloquei todas as classes no mesmo arquivo para facilitar a prática.
 *  - Em Java, apenas UMA classe pode ser public por arquivo; as demais ficam
 *    com visibilidade de pacote (package-private), suficiente para o exercício.
 * ============================================================
 */

/* ============================================================
 * CLASSE BASE CONCRETA: Musica
 * ------------------------------------------------------------
 * Responsabilidade:
 *   Representar uma música com título, artista e duração (em segundos).
 *   Demonstra:
 *     - Encapsulamento via atributos private
 *     - Getter/Setter (com validação no setter)
 *     - Método concreto exibirInfo() (será sobrescrito na subclasse)
 * ============================================================
 */
class Musica {
    // ---------- Atributos (estado) ----------
    // 'private' garante encapsulamento: acesso externo ocorre só via métodos.
    private String titulo;    // título da faixa
    private String artista;   // artista/banda
    private int duracaoSeg;   // duração em segundos (ex.: 215s)

    // ---------- Construtor ----------
    // Constrói uma música preenchendo todos os campos obrigatórios.
    public Musica(String titulo, String artista, int duracaoSeg) {
        this.titulo = titulo;
        this.artista = artista;
        // Validação simples: duração precisa ser positiva.
        if (duracaoSeg <= 0) {
            // Se inválida, corrige para um padrão mínimo e avisa didaticamente.
            System.out.println("[Aviso] Duração inválida (" + duracaoSeg + "s). Ajustada para 60s.");
            this.duracaoSeg = 60;
        } else {
            this.duracaoSeg = duracaoSeg;
        }
    }

    // ---------- Getter ----------
    // Expõe leitura controlada do título (exemplo de getter)
    public String getTitulo() {
        return titulo;
    }

    // (Poderíamos ter getters para artista/duração; deixei só um para o requisito.)

    // ---------- Setter com validação ----------
    // Permite alterar a duração, mas só para valores > 0 (exemplo de setter)
    public void setDuracaoSeg(int novaDuracao) {
        if (novaDuracao <= 0) {
            System.out.println("[Erro] Nova duração inválida (" + novaDuracao + "s). Mantendo valor atual: " + duracaoSeg + "s.");
            return;
        }
        this.duracaoSeg = novaDuracao;
    }

    // ---------- Método utilitário privado ----------
    // Converte segundos para string "m:ss" (ex.: 215 -> "3:35")
    // private String formatarDuracao() {
    //     int m = duracaoSeg / 60;
    //     int s = duracaoSeg % 60;
    //     // Formata segundo com 2 dígitos.
    //     return String.format("%d:%02d", m, s);
    // }

    // ---------- Método "público" que poderá ser sobrescrito ----------
    // Exibe informações básicas da música.
    public void exibirInfo() {
        //System.out.println("Música: " + titulo + " | Artista: " + artista + " | Duração: " + formatarDuracao());
        System.out.println("Música: " + titulo + " | Artista: " + artista + " | Duração: " + duracaoSeg + "s");
    }
}

/* ============================================================
 * SUBCLASSE CONCRETA: MusicaPremium
 * ------------------------------------------------------------
 * Responsabilidade:
 *   Especializar Musica com informação de qualidade de áudio.
 *   Demonstra:
 *     - Herança (extends Musica)
 *     - Sobrescrita (override de exibirInfo)
 *     - Sobrecarga de construtor (dois ctors)
 *     - Encapsulamento via atributo private
 * ============================================================
 */
class MusicaPremium extends Musica {
    // Atributo específico desta subclasse: qualidade do arquivo.
    // Para o exercício, aceitamos duas strings típicas.
    private String qualidade; // "MP3" ou "FLAC"

    // ---------- Construtor 1 (com qualidade explícita) ----------
    public MusicaPremium(String titulo, String artista, int duracaoSeg, String qualidade) {
        // Chama o construtor da classe base para inicializar os campos herdados.
        super(titulo, artista, duracaoSeg);
        // Validação simples: se não for um dos valores esperados, define padrão.
        if (!"MP3".equalsIgnoreCase(qualidade) && !"FLAC".equalsIgnoreCase(qualidade)) {
            System.out.println("[Aviso] Qualidade \"" + qualidade + "\" não reconhecida. Usando padrão MP3.");
            this.qualidade = "MP3";
        } else {
            this.qualidade = qualidade.toUpperCase();
        }
    }

    // ---------- Construtor 2 (sobrecarga) ----------
    // Sobrecarga: quando a qualidade não for informada, assume "MP3".
    public MusicaPremium(String titulo, String artista, int duracaoSeg) {
        this(titulo, artista, duracaoSeg, "MP3"); // delega para o construtor principal
    }

    // ---------- Sobrescrita de método ----------
    // Acrescenta a qualidade à exibição padrão.
    @Override
    public void exibirInfo() {
        // Podemos reutilizar parte do comportamento base imprimindo algo adicional,
        // ou reimplementar completamente. Aqui, reimplementamos para controlar a saída.
        System.out.println("[Premium] " + getTitulo() + " | Qualidade: " + qualidade);
        // Observação: usamos getTitulo() (getter) para reforçar encapsulamento.
        // Em um caso real, poderíamos expor getters para artista/duração também e apresentar tudo.
    }
}

/* ============================================================
 * CLASSE CONCRETA: Playlist
 * ------------------------------------------------------------
 * Responsabilidade:
 *   Representar uma playlist simples (apenas nome/dono) e simular adição
 *   de músicas por objeto OU por dados brutos (sobrecarga).
 *   Demonstra:
 *     - Modificadores de visibilidade (private/public)
 *     - Sobrecarga de método (adicionarMusica em 2 formas)
 * ============================================================
 */
class Playlist {
    // Atributos encapsulados
    private String nome; // nome da playlist (ex.: "Treino")
    private String dono; // responsável (ex.: "Nilton")

    // Construtor básico
    public Playlist(String nome, String dono) {
        this.nome = nome;
        this.dono = dono;
    }

    // Método "simulado" para adicionar música já criada
    public void adicionarMusica(Musica m) {
        // Neste exercício não estamos mantendo uma lista interna para simplificar;
        // apenas demonstramos mensagens (poderia ser um ArrayList<Musica>).
        System.out.println("Adicionada à playlist \"" + nome + "\": " + m.getTitulo());
    }

    // Sobrecarga: permite adicionar informando os dados crus
    public void adicionarMusica(String titulo, String artista, int duracaoSeg) {
        // Cria uma Musica simples e delega para a outra versão (evita duplicação de lógica).
        Musica temp = new Musica(titulo, artista, duracaoSeg);
        adicionarMusica(temp);
    }
}

/* ============================================================
 * CLASSE DE EXECUÇÃO (public): AppMusicas
 * ------------------------------------------------------------
 * Responsabilidade:
 *   Ponto de entrada do programa. Demonstra o uso das classes acima:
 *     - Instancia Musica e MusicaPremium (usando os dois construtores)
 *     - Chama exibirInfo (sobrescrita visível)
 *     - Usa setter com validação
 *     - Usa sobrecarga de adicionarMusica na Playlist
 * ============================================================
 */
public class AppMusicas {
    public static void main(String[] args) {
        // 1) Criar uma música "comum"
        Musica m1 = new Musica("Caminhos", "Banda X", 215);
        m1.exibirInfo(); // exibe info base (título, artista, duração formatada)

        // 2) Criar músicas premium usando os DOIS construtores (sobrecarga)
        MusicaPremium mp1 = new MusicaPremium("Horizonte", "Artista Y", 180, "FLAC");
        MusicaPremium mp2 = new MusicaPremium("Amanhecer", "Artista Z", 200); // qualidade padrão "MP3"

        // 3) Chamar exibirInfo() nas premium (mostra a SOBRESCRITA)
        mp1.exibirInfo(); // saída específica de MusicaPremium
        mp2.exibirInfo(); // idem

        // 4) Testar setter com validação (valor inválido)
        System.out.println("-- Testando setter de duração com valor inválido --");
        m1.setDuracaoSeg(-5);    // inválido: mantém valor anterior
        m1.setDuracaoSeg(300);   // válido: altera para 5:00
        m1.exibirInfo();

        // 5) Usar Playlist e a SOBRECARGA de adicionarMusica
        Playlist p = new Playlist("Estudos", "Nilton");
        p.adicionarMusica(m1);                              // passa objeto
        p.adicionarMusica("Fluxo", "Lo-Fi Beats", 150);     // passa dados crus (sobrecarga)

        // Fim da demonstração didática
        System.out.println("-- Fim --");
    }
}
