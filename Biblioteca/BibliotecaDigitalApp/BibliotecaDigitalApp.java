// =============================================================
// BibliotecaDigitalApp.java
// Implementa o exercício "Sistema de Biblioteca Digital em Java"
// com herança e polimorfismo, além de uma classe Biblioteca
// que gerencia os materiais (ArrayList).
// =============================================================

import java.util.ArrayList;   // Importa a estrutura de lista dinâmica
import java.util.List;        // Interface List para tipagem abstrata
import java.util.Iterator;    // Iterator para remoção segura em loops

// -------------------------------------------------------------
// SUPERCLASSE: MaterialBiblioteca
// - Representa um material genérico da biblioteca.
// - Contém atributos e comportamentos comuns.
// - Subclasses sobrescrevem getInformacoes() para incluir dados
//   específicos (polimorfismo).
// -------------------------------------------------------------
class MaterialBiblioteca {
    // --------- Atributos comuns (privados → encapsulamento) ----------
    private String titulo;         // Título do material
    private String autor;          // Autor(a) do material
    private int anoPublicacao;     // Ano de publicação

    // --------- Construtor: inicializa os atributos comuns ----------
    public MaterialBiblioteca(String titulo, String autor, int anoPublicacao) {
        // Validações simples para evitar estados inválidos
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título inválido.");
        }
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor inválido.");
        }
        if (anoPublicacao <= 0) {
            throw new IllegalArgumentException("Ano de publicação inválido.");
        }

        this.titulo = titulo;                 // Atribui título
        this.autor = autor;                   // Atribui autor
        this.anoPublicacao = anoPublicacao;   // Atribui ano
    }

    // --------- Getters (exposição controlada dos campos) ----------
    public String getTitulo() { return titulo; }                 // Retorna o título
    public String getAutor() { return autor; }                   // Retorna o autor
    public int getAnoPublicacao() { return anoPublicacao; }      // Retorna o ano

    // --------- Método comum: string com informações base ----------
    // Subclasses podem sobrescrever (override) para acrescentar dados.
    public String getInformacoes() {
        // Monta uma string com os dados básicos do material
        return String.format("Título: %s | Autor: %s | Ano: %d",
                titulo, autor, anoPublicacao);
    }
}

// -------------------------------------------------------------
// SUBCLASSE: Livro
// - Adiciona o atributo específico numeroPaginas.
// - Sobrescreve getInformacoes() para incluir esse dado.
// -------------------------------------------------------------
class Livro extends MaterialBiblioteca {
    private int numeroPaginas;   // Número de páginas do livro

    public Livro(String titulo, String autor, int anoPublicacao, int numeroPaginas) {
        // Chama o construtor da superclasse para os campos comuns
        super(titulo, autor, anoPublicacao);
        if (numeroPaginas <= 0) {
            throw new IllegalArgumentException("Número de páginas inválido.");
        }
        this.numeroPaginas = numeroPaginas;   // Atribui páginas
    }

    // Sobrescrita para incluir "páginas" no texto
    @Override
    public String getInformacoes() {
        // Reaproveita a string base e adiciona o campo específico
        return super.getInformacoes() + String.format(" | Páginas: %d", numeroPaginas);
    }
}

// -------------------------------------------------------------
// SUBCLASSE: Revista
// - Adiciona o atributo específico edicao.
// - Sobrescreve getInformacoes() para incluir a edição.
// -------------------------------------------------------------
class Revista extends MaterialBiblioteca {
    private int edicao;   // Número da edição da revista

    public Revista(String titulo, String autor, int anoPublicacao, int edicao) {
        super(titulo, autor, anoPublicacao);  // Inicializa campos comuns
        if (edicao <= 0) {
            throw new IllegalArgumentException("Edição inválida.");
        }
        this.edicao = edicao;                 // Atribui edição
    }

    @Override
    public String getInformacoes() {
        // Acrescenta a informação da edição
        return super.getInformacoes() + String.format(" | Edição: %d", edicao);
    }
}

// -------------------------------------------------------------
// SUBCLASSE: Ebook
// - Adiciona o atributo específico formatoArquivo (PDF/EPUB/MOBI...).
// - Sobrescreve getInformacoes() para incluir o formato.
// -------------------------------------------------------------
class Ebook extends MaterialBiblioteca {
    private String formatoArquivo;  // Formato do eBook (ex.: PDF, EPUB)

    public Ebook(String titulo, String autor, int anoPublicacao, String formatoArquivo) {
        super(titulo, autor, anoPublicacao);  // Inicializa campos da base
        if (formatoArquivo == null || formatoArquivo.isBlank()) {
            throw new IllegalArgumentException("Formato de arquivo inválido.");
        }
        this.formatoArquivo = formatoArquivo.trim().toUpperCase(); // Normaliza formato
    }

    @Override
    public String getInformacoes() {
        // Acrescenta o formato do arquivo
        return super.getInformacoes() + String.format(" | Formato: %s", formatoArquivo);
    }
}

// -------------------------------------------------------------
// SUBCLASSE: Audiolivro
// - Adiciona o atributo específico duracaoMinutos.
// - Sobrescreve getInformacoes() para incluir a duração.
// -------------------------------------------------------------
class Audiolivro extends MaterialBiblioteca {
    private int duracaoMinutos;  // Duração total em minutos

    public Audiolivro(String titulo, String autor, int anoPublicacao, int duracaoMinutos) {
        super(titulo, autor, anoPublicacao);  // Inicializa campos comuns
        if (duracaoMinutos <= 0) {
            throw new IllegalArgumentException("Duração inválida.");
        }
        this.duracaoMinutos = duracaoMinutos; // Atribui duração
    }

    @Override
    public String getInformacoes() {
        // Acrescenta a duração (minutos)
        return super.getInformacoes() + String.format(" | Duração: %d min", duracaoMinutos);
    }
}

// -------------------------------------------------------------
// CLASSE: Biblioteca
// - Mantém e gerencia uma lista de MaterialBiblioteca.
// - Operações: adicionar, remover por título, exibir por título,
//   e listar todos os materiais.
// - Aqui ocorre o POLIMORFISMO: ao chamar getInformacoes() sobre
//   a referência base, a versão da subclasse é executada.
// -------------------------------------------------------------
class Biblioteca {
    // Lista polimórfica: pode armazenar Livro, Revista, Ebook, Audiolivro...
    private List<MaterialBiblioteca> materiais;

    public Biblioteca() {
        this.materiais = new ArrayList<>();  // Inicializa o ArrayList
    }

    // Adiciona um material ao acervo (não aceita null)
    public void adicionarMaterial(MaterialBiblioteca material) {
        if (material == null) {
            throw new IllegalArgumentException("Material nulo não pode ser adicionado.");
        }
        materiais.add(material);  // Insere no final da lista
    }

    // Remove o primeiro material cujo título case (case-insensitive)
    public void removerMaterial(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            System.out.println("Título inválido para remoção.");
            return;
        }
        // Usa Iterator para remoção segura durante a iteração
        Iterator<MaterialBiblioteca> it = materiais.iterator();
        while (it.hasNext()) {
            MaterialBiblioteca m = it.next();                     // Próximo item
            if (m.getTitulo().equalsIgnoreCase(titulo)) {         // Compara título
                it.remove();                                      // Remove com segurança
                System.out.println("Removido: " + m.getTitulo()); // Feedback
                return;                                           // Sai após remover o primeiro
            }
        }
        System.out.println("Material não encontrado para remoção: " + titulo);
    }

    // Exibe informações de um material específico (pelo título)
    public void exibirInformacoesMaterial(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            System.out.println("Título inválido para consulta.");
            return;
        }
        for (MaterialBiblioteca m : materiais) {                    // Percorre lista
            if (m.getTitulo().equalsIgnoreCase(titulo)) {           // Match por título
                // Polimorfismo: chama a versão correta de getInformacoes()
                System.out.println(m.getInformacoes());
                return;                                            // Encerra após encontrar
            }
        }
        System.out.println("Material não encontrado: " + titulo);
    }

    // Lista todos os materiais cadastrados
    public void listarTodosMateriais() {
        if (materiais.isEmpty()) {                                 // Lista vazia?
            System.out.println("Nenhum material cadastrado.");
            return;
        }
        System.out.println("Acervo da Biblioteca (" + materiais.size() + " item/ns):");
        for (MaterialBiblioteca m : materiais) {
            // Polimorfismo novamente: cada subclasse imprime seu extra
            System.out.println("- " + m.getInformacoes());
        }
    }
}

// -------------------------------------------------------------
// CLASSE PRINCIPAL (Main)
// - Cria uma instância de Biblioteca, adiciona materiais de
//   diferentes tipos e demonstra as operações solicitadas.
// -------------------------------------------------------------
public class BibliotecaDigitalApp {
    public static void main(String[] args) {
        // Cria a biblioteca (lista inicialmente vazia)
        Biblioteca bib = new Biblioteca();

        // Adiciona materiais de tipos diversos (exercita a herança)
        bib.adicionarMaterial(new Livro(
                "Clean Code", "Robert C. Martin", 2008, 464));

        bib.adicionarMaterial(new Revista(
                "IEEE Software", "IEEE", 2024, 5)); // edição 5/2024

        bib.adicionarMaterial(new Ebook(
                "Design Patterns", "GoF", 1994, "PDF"));

        bib.adicionarMaterial(new Audiolivro(
                "The Pragmatic Programmer", "Andrew Hunt", 1999, 780)); // 13h

        // Lista tudo (demonstra polimorfismo no getInformacoes())
        bib.listarTodosMateriais();

        // Busca pontual por título (case-insensitive)
        System.out.println("\nConsulta por título: 'clean code'");
        bib.exibirInformacoesMaterial("clean code");

        // Remoção por título
        System.out.println("\nRemovendo 'Design Patterns'...");
        bib.removerMaterial("Design Patterns");

        // Lista novamente após a remoção
        System.out.println("\nAcervo após remoção:");
        bib.listarTodosMateriais();
    }
}
