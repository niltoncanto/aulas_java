// Interface comum para todos os tipos de mensagem
interface Mensagem {
    void exibir();
}
// Implementação real com mensagem personalizada
class MensagemReal implements Mensagem {
    private String conteudo;
    public MensagemReal(String conteudo) {
        this.conteudo = conteudo;
    }
    @Override
    public void exibir() {
        System.out.println("Mensagem: " + conteudo);
    }
}
// Null Object: substitui o uso de null
class MensagemVazia implements Mensagem {
    @Override
    public void exibir() {
        System.out.println("Nenhuma mensagem configurada.");
    }
}
// Classe que representa um usuário do sistema
class Usuario {
    private String nome;
    private Mensagem mensagem;
    public Usuario(String nome, Mensagem mensagem) {
        this.nome = nome;
        this.mensagem = mensagem;
    }
    public void exibirMensagem() {
        System.out.print(nome + " -> ");
        mensagem.exibir(); // sempre funciona, mesmo se for "vazio"
    }
}
//Classe principal para teste
public class Main {
    public static void main(String[] args) {
        Usuario u1 = new Usuario("Ana", new MensagemReal("Bem-vinda ao sistema!"));
        Usuario u2 = new Usuario("Carlos", new MensagemVazia()); // não tem mensagem

        u1.exibirMensagem(); // Saída: Ana -> Mensagem: Bem-vinda ao sistema!
        u2.exibirMensagem(); // Saída: Carlos -> Nenhuma mensagem configurada.
    }
}




