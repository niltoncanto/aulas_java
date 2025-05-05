// 1. Declaração do pacote (opcional, usado em projetos organizados por pacotes)
package codigos_ppt.estrutura_basica;

// 2. Importação de bibliotecas (opcional)
import java.util.Scanner;

// 3. Declaração da classe pública principal
public class Exemplo02 {

    // 4. Atributos da classe (opcional)
    private String mensagem = "Olá, Mundo!";

    // 4. Métodos da classe (opcional)
    public void exibirMensagem() {
        System.out.println(mensagem);
    }

    // 5. Método main: ponto de entrada do programa
    public static void main(String[] args) {
        // Criação de um objeto da classe
        Exemplo02 programa = new Exemplo02();
        
        // Chamada de um método
        programa.exibirMensagem();

        // Uso de uma biblioteca importada
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.println("Olá, " + nome + "!");

        scanner.close(); // Fecha o Scanner
    }
}
