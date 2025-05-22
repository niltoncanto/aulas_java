/* 
Leilao: objeto observado, onde o estado (preço) muda.
Usuario: observadores que desejam ser notificados das mudanças.
Main: simula o uso do sistema. 
*/
import java.util.ArrayList;
import java.util.List;

// Interface que define o comportamento do Observer (usuário)
interface Observador {
    void atualizar(double novoPreco);
}

// Classe que representa um usuário observador
class Usuario implements Observador {
    private String nome;

    public Usuario(String nome) {
        this.nome = nome;
    }

    // Este método será chamado quando o leilão for atualizado
    @Override
    public void atualizar(double novoPreco) {
        System.out.println("Usuário " + nome + " foi notificado: novo preço do leilão é R$" + novoPreco);
    }
}

// Classe que representa o Leilão (Subject)
class Leilao {
    private List<Observador> observadores = new ArrayList<>();
    private double preco;

    // Método para adicionar observadores
    public void adicionarObservador(Observador o) {
        observadores.add(o);
    }

    // Método para remover observadores (não usado neste exemplo, mas útil)
    public void removerObservador(Observador o) {
        observadores.remove(o);
    }

    // Notifica todos os usuários registrados
    private void notificarObservadores() {
        for (Observador o : observadores) {
            o.atualizar(preco);
        }
    }

    // Atualiza o preço do leilão e notifica os usuários
    public void atualizarPreco(double novoPreco) {
        System.out.println("\n>> Novo lance recebido: R$" + novoPreco);
        this.preco = novoPreco;
        notificarObservadores();
    }
}

// Classe principal que simula o sistema
public class Main {
    public static void main(String[] args) {
        // Criando um leilão
        Leilao leilao = new Leilao();

        // Criando alguns usuários
        Usuario u1 = new Usuario("Ana");
        Usuario u2 = new Usuario("Carlos");
        Usuario u3 = new Usuario("Fernanda");

        // Registrando os usuários no leilão
        leilao.adicionarObservador(u1);
        leilao.adicionarObservador(u2);
        leilao.adicionarObservador(u3);

        // Simulando lances
        leilao.atualizarPreco(100.0);
        leilao.atualizarPreco(150.0);
        leilao.atualizarPreco(200.0);
    }
}
