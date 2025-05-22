import java.util.ArrayList;
import java.util.List;

// Interface comum para todos os componentes gráficos (Graphical User Interface)
interface ComponentGUI {
    // Método responsável por renderizar o componente
    void renderizar(String indentacao);
}

// Classe que representa um botão (componente simples)
class LeafBotao implements ComponentGUI {
    private String texto;

    // Construtor para inicializar o botão com um texto
    public LeafBotao(String texto) {
        this.texto = texto;
    }

    // Implementação do método renderizar para o botão
    @Override
    public void renderizar(String indentacao) {
        System.out.println(indentacao + "->Botão: " + texto);
    }
}

// Classe que representa um texto (componente simples)
class LeafTexto implements ComponentGUI {
    private String conteudo;

    // Construtor para inicializar o conteúdo do texto
    public LeafTexto(String conteudo) {
        this.conteudo = conteudo;
    }

    // Implementação do método renderizar para o texto
    @Override
    public void renderizar(String indentacao) {
        System.out.println(indentacao + "->Texto: " + conteudo);
    }
}

// Classe que representa um painel (componente composto)
class CompositePainel implements ComponentGUI {
    private List<ComponentGUI> filhos = new ArrayList<>();

    // Método para adicionar componentes ao painel
    public void adicionar(ComponentGUI componente) {
        filhos.add(componente);
    }

    // Implementação do método renderizar para o painel
    @Override
    public void renderizar(String indentacao) {
        System.out.println(indentacao + "Painel");
        // Para cada componente filho, chamamos renderizar com mais identação
        for (ComponentGUI filho : filhos) {
            filho.renderizar(indentacao + "   ");
        }
    }
}

// Classe principal com exemplo de uso
public class ClientGUI {
    public static void main(String[] args) {
        // Criação de componentes simples
        LeafBotao botaoSalvar = new LeafBotao("Salvar");
        LeafTexto textoBemVindo = new LeafTexto("Bem-vindo!");

        // Criação de um painel interno com dois componentes
        CompositePainel painelInterno = new CompositePainel();
        painelInterno.adicionar(new LeafBotao("Cancelar"));
        painelInterno.adicionar(new LeafTexto("Mensagem interna"));

        // Criação do painel principal
        CompositePainel painelPrincipal = new CompositePainel();
        painelPrincipal.adicionar(botaoSalvar);
        painelPrincipal.adicionar(textoBemVindo);
        painelPrincipal.adicionar(painelInterno);

        // Renderiza toda a estrutura a partir do painel principal
        painelPrincipal.renderizar("");
    }
}