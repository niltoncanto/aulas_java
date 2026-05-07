// Exemplo didático de "Protection Proxy"

enum Role {
    ADMIN,   // Pode ler e escrever
    EDITOR,  // Pode ler e escrever
    VIEWER   // Pode apenas ler
}

// Interface comum: qualquer documento deve poder ser lido e escrito
interface Document {
    void read();
    void write(String newContent);
}

// Representa o usuário autenticado no sistema
class User {
    private final String username;
    private final Role role;

    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }
    public Role getRole() { return role; }
}

// Proxy responsável por controlar o acesso ao "documento"
class DocumentProxy implements Document {
    private String content;   // Conteúdo simulado
    private final User user;  // Usuário atual

    public DocumentProxy(User user, String initialContent) {
        this.user = user;
        this.content = initialContent;
    }

    @Override
    public void read() {
        System.out.printf("[PROXY] %s (%s) leu o conteúdo.%n",
                user.getUsername(), user.getRole());
        System.out.println("Conteúdo: " + content);
    }

    @Override
    public void write(String newContent) {
        // Somente ADMIN e EDITOR podem alterar
        if (user.getRole() == Role.ADMIN || user.getRole() == Role.EDITOR) {
            content = newContent;
            System.out.printf("[PROXY] %s (%s) atualizou o conteúdo.%n",
                    user.getUsername(), user.getRole());
        } else {
            System.out.printf("[PROXY] %s (%s) NÃO tem permissão para alterar o conteúdo.%n",
                    user.getUsername(), user.getRole());
        }
    }
}

// Classe principal para demonstrar o comportamento
public class SimpleProtectionProxy {
    public static void main(String[] args) {
        // Usuários com diferentes perfis
        User admin = new User("Ana", Role.ADMIN);
        User editor = new User("Bruno", Role.EDITOR);
        User viewer = new User("Cris", Role.VIEWER);

        System.out.println("=== Testando acesso com diferentes perfis ===");

        Document docAdmin = new DocumentProxy(ADMIN, "Versão 1: rascunho inicial");
        Document docEditor = new DocumentProxy(editor, "Versão 1: rascunho inicial");
        Document docViewer = new DocumentProxy(viewer, "Versão 1: rascunho inicial");

        // Leitura (permitida para todos)
        docAdmin.read();
        docEditor.read();
        docViewer.read();

        System.out.println("\n=== Tentativas de escrita ===");
        docAdmin.write("Versão 2: revisado por Ana (ADMIN)");
        docEditor.write("Versão 3: revisado por Bruno (EDITOR)");
        docViewer.write("Tentativa de alteração por Cris (VIEWER)");
    }
}
