// Interface que define o contrato para qualquer classe que possa ser autenticada
interface Autenticavel {
    boolean autenticar(String usuario, String senha);
}

// Classe que representa um aluno
class Aluno implements Autenticavel {
    private String usuario = "aluno01";
    private String senha = "aluno123";

    // Implementação da autenticação
    public boolean autenticar(String usuario, String senha) {
        return this.usuario.equals(usuario) && this.senha.equals(senha);
    }
}

// Classe que representa um professor
class Professor implements Autenticavel {
    private String usuario = "prof01";
    private String senha = "prof123";

    public boolean autenticar(String usuario, String senha) {
        return this.usuario.equals(usuario) && this.senha.equals(senha);
    }
}

// Classe que representa um visitante
class Visitante implements Autenticavel {
    private String chaveDeAcesso = "VISITA2025";

    // Visitante usa apenas uma chave, ignorando o login
    public boolean autenticar(String usuario, String senha) {
        return senha.equals(chaveDeAcesso);
    }
}

// Classe que executa a autenticação de qualquer tipo de usuário
class Autenticador {
    public void fazerLogin(Autenticavel autenticavel, String usuario, String senha) {
        if (autenticavel.autenticar(usuario, senha)) {
            System.out.println("✅ Acesso permitido.");
        } else {
            System.out.println("❌ Acesso negado.");
        }
    }
}

// Classe principal para testar o sistema
public class SistemaDeLogin {
    public static void main(String[] args) {
        // Criação do "porteiro"
        Autenticador autenticador = new Autenticador();

        // Usuários do sistema
        Autenticavel aluno = new Aluno();
        Autenticavel professor = new Professor();
        Autenticavel visitante = new Visitante();

        // Testes de login
        System.out.println("Tentando login como aluno...");
        autenticador.fazerLogin(aluno, "aluno01", "aluno123"); // Sucesso

        System.out.println("\nTentando login como professor...");
        autenticador.fazerLogin(professor, "prof01", "prof123"); // Sucesso

        System.out.println("\nTentando login como visitante com chave errada...");
        autenticador.fazerLogin(visitante, "", "senhaErrada"); // Falha

        System.out.println("\nTentando login como visitante com chave correta...");
        autenticador.fazerLogin(visitante, "", "VISITA2025"); // Sucesso
    }
}
