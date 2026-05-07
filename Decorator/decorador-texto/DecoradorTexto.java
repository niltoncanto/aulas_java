
// Interface comum a todos os componentes e decoradores 
interface Texto {
    String getTexto();
}

// ------------------------------------------------------
// Componente concreto - fornece o texto original
// ------------------------------------------------------
class MensagemSimples implements Texto {
    @Override
    public String getTexto() {
        return "olá, mundo";
    }
}

// ------------------------------------------------------
// Classe abstrata base para os decoradores
// ------------------------------------------------------
abstract class TextoDecorator implements Texto {
    protected Texto texto; // referência ao objeto decorado

    public TextoDecorator(Texto texto) {
        this.texto = texto;
    }

    @Override
    public String getTexto() {
        return texto.getTexto(); // delega a chamada ao componente encapsulado
    }
}

// ------------------------------------------------------
// Decorador concreto - converte o texto em maiúsculas
// ------------------------------------------------------
class MaiusculoDecorator extends TextoDecorator {
    public MaiusculoDecorator(Texto texto) {
        super(texto);
    }

    @Override
    public String getTexto() {
        return texto.getTexto().toUpperCase();
    }
}

// ------------------------------------------------------
// Decorador concreto - adiciona aspas ao redor do texto
// ------------------------------------------------------
class AspasDecorator extends TextoDecorator {
    public AspasDecorator(Texto texto) {
        super(texto);
    }

    @Override
    public String getTexto() {
        return "\"" + texto.getTexto() + "\"";
    }
}

// ------------------------------------------------------
// Decorador concreto - adiciona ponto de exclamação
// ------------------------------------------------------
class PontoDecorator extends TextoDecorator {
    public PontoDecorator(Texto texto) {
        super(texto);
    }

    @Override
    public String getTexto() {
        return texto.getTexto() + "!";
    }
}

// ------------------------------------------------------
// Classe principal para demonstrar o encadeamento de decoradores
// ------------------------------------------------------
public class DecoradorTexto {
    public static void main(String[] args) {

        Texto mensagem = new MensagemSimples();         // Passo 1: Criamos o objeto base
        System.out.println("Texto base: " + mensagem.getTexto());

        mensagem = new MaiusculoDecorator(mensagem);   // Passo 2: Aplicamos o primeiro decorador
        System.out.println("Após MaiusculoDecorator: " + mensagem.getTexto());

        mensagem = new AspasDecorator(mensagem);       // Passo 3: Encadeamos mais um decorador (Aspas)
        System.out.println("Após AspasDecorator: " + mensagem.getTexto());

        mensagem = new PontoDecorator(mensagem);       // Passo 4: Encadeamos outro decorador (Ponto)
        System.out.println("Após PontoDecorator: " + mensagem.getTexto());

        // Resultado final: todos os decoradores aplicados em sequência
        System.out.println("\nResultado Final: " + mensagem.getTexto());

        // Observe que:
        // - A classe TextoDecorator evita repetição de código.
        // - Cada decorador adiciona uma camada de comportamento ao anterior.
        // - A classe base nunca é modificada — apenas “embrulhada” por novas camadas.
    }
}
