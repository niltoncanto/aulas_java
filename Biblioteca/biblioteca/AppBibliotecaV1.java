/*
Objetivos do exercício:
- Praticar POO em Java com foco em encapsulamento, visibilidade, estado de objetos e colaboração entre classes.
- Exercitar o uso de coleções (List/ArrayList) para compor objetos (um usuário possui livros emprestados).
- Implementar regras simples de negócio (emprestar/devolver) respeitando o estado do objeto (disponível/emprestado).
- Separar responsabilidades: classe de domínio (Livro), classe de domínio que agrega uma coleção (Usuario) e classe de teste (AppBibliotecaV1).

Pontos de atenção e aprendizado:
- Encapsulamento: atributos privados e métodos públicos de acesso/ação (getters quando necessário, métodos de negócio).
- Estado e invariantes: um livro não pode ser emprestado duas vezes seguidas sem ser devolvido; devolver sempre torna o estado “disponível”.
- Acoplamento/controladores: Usuario orquestra a operação chamando métodos de Livro, ao invés de manipular diretamente os campos de Livro.
- Representação textual: sobrescrever toString para facilitar logs e depuração.
- Mensagens no console vs. retorno booleano: retornar sucesso/falha e (opcionalmente) logar mensagens; em projetos maiores preferimos separar regra de negócio (sem System.out) da camada de interface/log.
*/

import java.util.ArrayList;              // importa implementação concreta de lista dinâmica
import java.util.List;                   // importa a interface List, usada como tipo da coleção

class Livro {                            // declara a classe de domínio Livro
    // atributos
    private String autor;                // autor do livro (encapsulado: private)
    private String titulo;               // título do livro (encapsulado: private)
    private String isbn;                 // identificador ISBN (encapsulado: private)
    private boolean emprestado;          // estado interno: true = emprestado, false = disponível

    // construtor da classe
    public Livro(String autor, String titulo, String isbn){ // construtor para inicializar os campos obrigatórios
        this.autor = autor;             // atribui o autor recebido ao atributo da instância
        this.titulo = titulo;           // atribui o título recebido ao atributo da instância
        this. isbn = isbn;              // atribui o isbn recebido ao atributo da instância (observação: há um espaço após this., válido mas incomum)
        this.emprestado = false;        // livro é criado disponível (estado inicial)
    }

    // método para emprestar
    public boolean emprestar(){         // expõe operação de negócio: tentar emprestar o livro
        if(!emprestado){                // se o livro NÃO está emprestado…
            emprestado = true;          // …marca como emprestado
            return true;                // e sinaliza sucesso
        }else{                          // caso contrário…
            return false;               // …sinaliza falha: já está emprestado
        }    
    }

    // Método para devolver o livro
    public void devolver() {            // expõe operação de negócio: devolver o livro
        emprestado = false;             // devolução sempre torna o livro disponível
    }

    // Método para verificar o status do livro
    public boolean isEmprestado() {     // getter semântica de estado (nome no padrão JavaBeans para boolean)
        return emprestado;              // retorna true (emprestado) ou false (disponível)
    }

    // Método para exibir informações do livro
    public String toString() {          // sobrescreve a representação textual do objeto
        return titulo +                 // concatena o título
                " - " +                 // separador visual
                autor +                 // concatena o autor
        " (ISBN: " + isbn + ") | " +    // inclui ISBN formatado
        (emprestado ? "Emprestado" : "Disponível"); // informa o status com operador ternário
    }
}

// Classe que representa um Usuário da biblioteca
class Usuario {                          // declara a classe de domínio Usuario
    private String nome;                 // nome do usuário (encapsulado)
    private int id;                      // identificador do usuário (encapsulado)
    private List<Livro> livrosEmprestados; // coleção de livros emprestados (composição por agregação)

    // Construtor
    public Usuario(String nome, int id) { // construtor para inicializar a identidade do usuário
        this.nome = nome;                // atribui nome
        this.id = id;                    // atribui id
        this.livrosEmprestados = new ArrayList<>(); // inicia a lista vazia de livros emprestados
    }

    /* *************************************************** */
    // Método para emprestar um livro ao usuário
    public boolean emprestarLivro(Livro livro) { // orquestração: tenta registrar o empréstimo para este usuário
        if(livro.emprestar()){          // delega ao Livro a decisão de emprestar (respeitando o estado interno)
            livrosEmprestados.add(livro); // se deu certo, adiciona à lista de livros emprestados do usuário
            System.out.println("Emprestou o livro: " +  livro); // log simples (usa toString do livro)
            return true;                // retorna sucesso
        }else{                          // se o livro não estava disponível…
            System.out.println("O livro " +  livro + " não está disponivel"); // log de falha (observação: falta espaço antes de “não” e acento em “disponível”)
            return false;               // retorna falha
        }
    }

    // Método para devolver um livro
    public boolean devolverLivro(Livro livro) {  // orquestra devolução de um livro específico
        if(livrosEmprestados.contains(livro)){   // verifica se o livro está de fato sob posse deste usuário
            livro.devolver();                    // delega ao Livro a operação de devolução (estado -> disponível)
            livrosEmprestados.remove(livro);     // remove o livro da lista de emprestados
            System.out.println(nome + "devolveu o livro"); // log de sucesso (observação: falta espaço após o nome)
            return true;                         // retorna sucesso
        }else{                                   // se o usuário não tinha esse livro…
            System.out.println(nome + "nao tem o livro emprestado"); // log de falha (observação: falta acento em “não” e espaço após o nome)
            return false;                        // retorna falha
        }        
    }

    // Método para listar os livros emprestados
    public void listarLivrosEmprestados() {      // exibe conteúdo da coleção de livros do usuário
        if(livrosEmprestados.isEmpty()){         // se a lista está vazia…
            System.out.println("não possui livros emprestado"); // mensagem (observação: concordância: “emprestados” no plural)
        }else{                                   // caso haja itens na lista…
            System.out.println("LISTA DE LIVROS EMPRESTADOS"); // cabeçalho
            for(Livro livro : livrosEmprestados){ // itera sobre cada livro emprestado
                System.out.println(" - " + livro); // imprime com marcador; usa toString do Livro
            }    
        }    
    }
}

// Classe principal para testar o sistema
public class AppBibliotecaV1 {           // classe com o método main (ponto de entrada)
    public static void main(String[] args){        // assinatura padrão do main
        Livro livro1 = new Livro("George Orwell", "1984", "123-123");         // cria um Livro disponível
        Livro livro2 = new Livro("J.R.R Tolkien","Senhor dos Anéis", "4444-123"); // cria outro Livro (observação: grafia “J.R.R. Tolkien”)
        Livro livro3 = new Livro("George", "Games of Thrones", "6666-123");   // cria outro Livro (observação: “Game of Thrones”)
        Usuario usuario1 = new Usuario("Alice",1); // cria um usuário com id 1
        Usuario usuario2 = new Usuario("Bruno",2); // cria outro usuário com id 2

        usuario1.emprestarLivro(livro1); // Alice tenta emprestar “1984” (deve funcionar)
        System.out.println("\n");
        usuario2.emprestarLivro(livro3); // Bruno tenta emprestar “Games of Thrones” (deve funcionar)
        System.out.println("\n");
        usuario1.emprestarLivro(livro2); // Alice tenta emprestar “Senhor dos Anéis” (deve funcionar)
        System.out.println("\n");
        usuario1.listarLivrosEmprestados(); // lista os livros atualmente com Alice
        System.out.println("\n");
        usuario2.listarLivrosEmprestados(); // lista os livros atualmente com Bruno
        System.out.println("\n");
        usuario1.devolverLivro(livro2);     // Alice devolve “Senhor dos Anéis”
        System.out.println("\n");
        usuario1.listarLivrosEmprestados(); // lista de Alice após devolução
    }
}


