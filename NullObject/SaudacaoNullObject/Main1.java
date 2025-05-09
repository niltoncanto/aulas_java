/*Implemente um sistema simples de saudação para usuários. 
Cada usuário pode ou não ter uma saudação personalizada. 
Caso o usuário não possua saudação, deve-se utilizar o 
padrão de projeto Null Object, evitando o uso de null no código cliente.

Requisitos:
- Crie uma interface Usuario com um método saudacao().
- Implemente duas classes:
  - UsuarioReal: exibe uma mensagem personalizada.
  - UsuarioNulo: exibe uma mensagem genérica, como "Nenhuma saudação configurada."
- No método main, instancie dois usuários: um com saudação real e outro com saudação nula. 
  Ambos devem chamar exibir() sem verificar se a saudação é nula.
*/
// Interface comum para todos os tipos de usuário
interface Usuario {
    void saudacao();
}
// Implementação real do usuário
class UsuarioReal implements Usuario {
    private String nome;
    public UsuarioReal(String nome) {
        this.nome = nome;
    }
    @Override
    public void saudacao() {
        System.out.println("Olá, " + nome + "!");
    }
}
// Null Object — substitui null com comportamento padrão
class UsuarioNulo implements Usuario {
    @Override
    public void saudacao() {
        System.out.println("Usuário desconhecido.");
    }
}
// Classe principal para testar o padrão
public class Main1 {
    public static void main(String[] args) {
        Usuario u1 = new UsuarioReal("Maria");
        Usuario u2 = new UsuarioNulo(); // substitui o uso de null
        u1.saudacao(); // Saída: Olá, Maria!
        u2.saudacao(); // Saída: Usuário desconhecido.
    }
}

/*
1. Evita código espalhado com if null
    if (usuario != null) {
        usuario.saudacao();
    } else {
        System.out.println("Usuário desconhecido.");
    }
Imagine isso repetido em vários lugares da aplicação. O código fica:
- Verboso
- Propenso a erros (alguém pode esquecer do if)
- Menos coeso (a lógica do que fazer com um "usuário vazio" está fora da classe Usuario)

Com Null Object, usamos polimorfismo:
    usuario.saudacao();

2. Abstrai o tratamento de ausência
O objeto "nulo" é um objeto legítimo, com comportamento definido.
Isso mantém o princípio de substituição de Liskov: qualquer instância 
de Usuario (real ou nula) pode ser usada sem quebrar o código.

3. Facilita manutenção e testes
Adicionar ou alterar o comportamento "nulo" exige alterar apenas a 
classe UsuarioNulo, sem buscar e alterar todos os ifs.
Em testes automatizados, é fácil injetar um UsuarioNulo para simular 
ausência de usuário, sem usar null.
 */