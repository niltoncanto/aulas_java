// Interface que define o "contrato" comum para qualquer imagem exibível
interface Image {
    void display(); // Método que representa a ação de exibir a imagem na tela (ou no console, no nosso exemplo)
}

class HeavyDiskImage implements Image {
    private final String fileName;
    public HeavyDiskImage(String fileName) {
        this.fileName = fileName;
        System.out.println("Carregando imagem do disco (somente uma vez): " + fileName);
    }
    @Override
    public void display() {
        System.out.println("Exibindo imagem: " + fileName);
    }
}

// Proxy que controla o acesso à HeavyDiskImage e mantém um CACHE da instância.
// Após a primeira exibição, ele reaproveita a mesma instância para evitar recarregar.
class CachedImageProxy implements Image {
    private final String fileName;           // Guarda o nome do arquivo para poder criar a imagem real sob demanda
    private HeavyDiskImage cachedRealImage; // Referência para a imagem real "pesada" (começa nula e é preenchida no primeiro uso)

    // Construtor recebe apenas o nome do arquivo (não cria a imagem pesada aqui)
    public CachedImageProxy(String fileName) {
        this.fileName = fileName;
    }

    // Exibe a imagem garantindo que, após a primeira vez, a instância pesada será reaproveitada
    @Override
    public void display() {
        // Se ainda não existe imagem pesada em cache, cria agora (lazy + cache)
        if (cachedRealImage == null) {
            System.out.println("[Proxy] Criando e armazenando em cache a imagem pesada: " + fileName);
            cachedRealImage = new HeavyDiskImage(fileName);
        } else {
            // Se já existe, informa que utilizará o cache (sem novo "carregamento do disco")
            System.out.println("[Proxy] Usando imagem em cache: " + fileName);
        }

        // Delegação: chama o display() da imagem real (que simula carregar/exibir)
        // Observação: como mantemos a MESMA instância, você pode adaptar o HeavyDiskImage
        // para carregar apenas na primeira vez; aqui mantemos o exemplo simples e visível.
        cachedRealImage.display();
    }
}

// Programa principal
public class ProxyImageMain {
    public static void main(String[] args) {
        // Cria duas imagens "virtuais" (proxys), ainda sem carregar nada do disco
        Image capaCurso    = new CachedImageProxy("capa-curso.jpg");
        Image fotoProfessor = new CachedImageProxy("professor.jpg");

        System.out.println("=== Primeira exibição de cada imagem ===");
        // Primeira vez: o proxy cria a HeavyDiskImage e exibe (com "carregamento do disco")
        capaCurso.display();
        fotoProfessor.display();

        System.out.println("\n=== Segunda exibição (reaproveitando cache) ===");
        // Segunda vez: o proxy reutiliza a instância em cache, evitando "novo carregamento"
        capaCurso.display();
        fotoProfessor.display();
    }
}
