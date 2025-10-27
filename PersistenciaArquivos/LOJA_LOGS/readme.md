/*
- ================================================================================
 - PROJETO: Cadastro de Produtos com SQLite + Log
 - DURAÇÃO: ~1h de execução em laboratório
 - OBJETIVO: Praticar persistência em banco (SQLite via JDBC), tratamento de exceções
           e registro de sucessos/erros em arquivo de log.
 - ARQUIVOS FORNECIDOS: FileTools.java (log/arquivos), GestaoBD.java (DAO SQLite)
- ================================================================================

SUMÁRIO
1) Como usar este projeto (checklist do aluno)
2) O que é um PreparedStatement (explicação e exemplos)
3) Principais métodos de execução em PreparedStatement (tabela)
4) Trabalhando com arquivos em Java (passo a passo + tabela comparativa)
5) Persistência com SQLite em Java (passo a passo)
6) Critérios de avaliação (rubrica)
7) Mini-esqueleto de uso (exemplo curto no Main)

--------------------------------------------------------------------------------
1) COMO USAR ESTE PROJETO (CHECKLIST DO ALUNO)
--------------------------------------------------------------------------------
1. Garanta o driver JDBC do SQLite no classpath (ex.: org.xerial:sqlite-jdbc).
2. Compile as classes fornecidas: FileTools.java e GestaoBD.java.
3. No início do Main, chame GestaoBD.criarTabelaSeNaoExistir(); 
      -> registre SUCESSO/ERRO em loja.log via FileTools.
4. Implemente um menu simples:
        1) Adicionar produto
        2) Listar todos
        3) Listar com estoque <= limite
        4) Atualizar estoque por id
        0) Sair
5. Para cada opção:
      - Valide entradas (nome não vazio, preço >= 0, quantidade >= 0).
      - Use os métodos de GestaoBD (inserir, listar, atualizar) dentro de try/catch.
      - Registre SUCESSO/ERRO em "loja.log" com FileTools.appendLog(...).
6. Formate preços com 2 casas decimais ao exibir.
7. Feche recursos automaticamente usando try-with-resources (JDBC e I/O).
8. Teste todos os fluxos e confira o conteúdo do loja.log.

--------------------------------------------------------------------------------
2) O QUE É UM PreparedStatement
--------------------------------------------------------------------------------
- É uma classe da API JDBC para enviar comandos SQL ao banco de forma parametrizada.
- Diferente de concatenar strings (inseguro), o PreparedStatement usa "?" como
  placeholders; os valores são passados separadamente via métodos setXxx(...).
- Benefícios:
  * Segurança: previne SQL Injection (dados ≠ código).
  * Clareza: separa o SQL dos dados.
  * Desempenho: pode ser pré-compilado e reutilizado.

Exemplo (INSERT seguro):
    String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)";
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:loja.db");
         PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, "Caderno");
        ps.setDouble(2, 15.90);
        ps.setInt(3, 50);
        int linhas = ps.executeUpdate();
        if (linhas > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long idGerado = rs.getLong(1);
                    // use idGerado...
                }
            }
        }
    } catch (SQLException ex) {
        // tratar e logar
    }

--------------------------------------------------------------------------------
3) PRINCIPAIS MÉTODOS DE EXECUÇÃO EM PreparedStatement
--------------------------------------------------------------------------------
| Operação SQL                                | Método                | Retorno                                |
|---------------------------------------------|-----------------------|----------------------------------------|
| INSERT / UPDATE / DELETE / CREATE TABLE ... | ps.executeUpdate()    | int (linhas afetadas)                  |
| SELECT                                      | ps.executeQuery()     | ResultSet (dados consultados)          |
| Desconhecida/Genérica (raro em CRUD simples)| ps.execute()          | boolean (true=ResultSet, false=update) |

Observação: Não existe ps.executeInsert() no JDBC.

--------------------------------------------------------------------------------
4) TRABALHANDO COM ARQUIVOS EM JAVA (PASSO A PASSO)
--------------------------------------------------------------------------------
Passos práticos:
  1) Escolha a classe/abordagem:
     - Leitura: FileReader + BufferedReader, ou Files.readAllLines(...)
     - Escrita: FileWriter + BufferedWriter, PrintWriter, ou Files.write(...)
     - Gerenciamento: File ou Files (NIO.2)
  2) Abra o arquivo (leitura ou escrita) — preferir try-with-resources.
  3) Leia/Escreva o conteúdo (br.readLine(), bw.write(...), etc.).
  4) Feche (automático no try-with-resources).
  5) Trate IOException (try/catch).
  6) Extras: criar/verificar/deletar arquivo com File/Files.

Tabela comparativa rápida:
| Objetivo                           | Classe/Abordagem               | Exemplo curto                                                               |
|-----------------------------------|---------------------------------|-----------------------------------------------------------------------------|
| Criar/Verificar arquivo           | File ou Files                   | new File("arq.txt").createNewFile();                                        |
| Escrever (sobrescrever)           | FileWriter + BufferedWriter     | try (bw = new BufferedWriter(new FileWriter("x.txt"))) { bw.write("..."); } |
| Escrever (append no final)        | FileWriter (append = true)      | new FileWriter("x.txt", true);                                              |
| Escrever texto formatado          | PrintWriter                     | try (pw = new PrintWriter("x.txt")) { pw.println("..."); }                  |
| Ler linha a linha                 | FileReader + BufferedReader     | try (br = new BufferedReader(new FileReader("x.txt"))) { String s; while ((s=br.readLine())!=null) { ... } } |
| Ler tudo (lista de linhas)        | Files.readAllLines(...)         | List<String> ls = Files.readAllLines(Paths.get("x.txt"));                   |
| Escrever tudo de uma vez          | Files.write(...)                | Files.write(Paths.get("x.txt"), Arrays.asList("a","b"));                    |
| Deletar arquivo                   | File.delete()                   | new File("x.txt").delete();                                                 |

Dica: a classe FileTools (fornecida) já abstrai operações comuns de .log:
  - FileTools.appendLog("loja.log", "SUCESSO", "mensagem");
  - FileTools.appendLog("loja.log", "ERRO", "mensagem", ex);

--------------------------------------------------------------------------------
5) PERSISTÊNCIA COM SQLite EM JAVA (PASSO A PASSO)
--------------------------------------------------------------------------------
1) Preparar o ambiente
   - Adicione o driver JDBC do SQLite (org.xerial:sqlite-jdbc).
   - O banco será um arquivo local: "loja.db".

2) Definir/Confirmar o esquema (executado apenas uma vez)
   - CREATE TABLE IF NOT EXISTS produtos(
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       nome TEXT NOT NULL,
       preco REAL NOT NULL,
       quantidade INTEGER NOT NULL DEFAULT 0
     );
   - Use GestaoBD.criarTabelaSeNaoExistir() no início do programa.
   - Registre SUCESSO/ERRO no loja.log.

3) Abrir conexão
   - URL JDBC: "jdbc:sqlite:loja.db".
   - Sempre em try-with-resources.

4) Usar PreparedStatement (sempre parametrizado)
   - INSERT/UPDATE/DELETE → executeUpdate()
   - SELECT → executeQuery()
   - Se precisar do ID gerado em INSERT, use RETURN_GENERATED_KEYS + getGeneratedKeys().

5) Tratar exceções de JDBC (obrigatório)
   - try/catch (SQLException).
   - Em caso de erro ou sucesso relevante → FileTools.appendLog(...).

6) Transações (quando houver várias operações dependentes)
   - c.setAutoCommit(false); ... c.commit(); // em caso de erro: rollback.

7) Mapear ResultSet em DTOs (ex.: ProdutoDTO) ao fazer SELECT.

8) Fechar recursos sempre (try-with-resources resolve).

9) Validar entradas antes de ir ao banco (nome, preço, quantidade).

10) Log centralizado
   - SUCESSO: "Produto 'X' inserido (id Y)".
   - ERRO: inclua a exceção para diagnóstico rápido.

--------------------------------------------------------------------------------
6) CRITÉRIOS DE AVALIAÇÃO (RUBRICA)
--------------------------------------------------------------------------------
- 30% Persistência correta (SQLite) com CRUD do menu.
- 25% Tratamento de exceções com mensagens claras.
- 25% Log de sucessos e erros (loja.log) usando FileTools.
- 20% Organização/legibilidade e formatação da saída.

--------------------------------------------------------------------------------
7) MINI-ESQUELETO DE USO (EXEMPLO CURTO NO Main)
--------------------------------------------------------------------------------
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DB_FILE = "loja.db";
    private static final String LOG_FILE = "loja.log";
    private static final DecimalFormat DF = new DecimalFormat("#0.00");

    public static void main(String[] args) {
        GestaoBD dao = new GestaoBD(DB_FILE);

        try {
            dao.criarTabelaSeNaoExistir();
            FileTools.appendLog(LOG_FILE, "SUCESSO", "Tabela verificada/criada.");
        } catch (SQLException ex) {
            FileTools.appendLog(LOG_FILE, "ERRO", "Falha ao criar/verificar tabela.", ex);
            System.err.println("Não foi possível preparar a tabela. Encerrando.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1-Adicionar  2-Listar  3-Listar (estoque<=X)  4-Atualizar qtd  0-Sair");
            System.out.print("Opção: ");
            String op = sc.nextLine().trim();
            try {
                switch (op) {
                    case "1": // Adicionar
                        System.out.print("Nome: "); String nome = sc.nextLine().trim();
                        System.out.print("Preço: "); double preco = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Quantidade: "); int qtd = Integer.parseInt(sc.nextLine().trim());
                        if (nome.isEmpty() || preco < 0 || qtd < 0) {
                            System.out.println("Dados inválidos.");
                            FileTools.appendLog(LOG_FILE, "ERRO", "Tentativa de inserir com dados inválidos.");
                            break;
                        }
                        long id = dao.inserirProduto(nome, preco, qtd);
                        System.out.println("Inserido id=" + id);
                        FileTools.appendLog(LOG_FILE, "SUCESSO", "Produto '" + nome + "' inserido (id " + id + ").");
                        break;

                    case "2": // Listar todos
                        List<GestaoBD.ProdutoDTO> todos = dao.listarTodos();
                        for (GestaoBD.ProdutoDTO p : todos) {
                            System.out.println(p.id + " | " + p.nome + " | R$ " + DF.format(p.preco) + " | qtd=" + p.quantidade);
                        }
                        FileTools.appendLog(LOG_FILE, "SUCESSO", "Listagem completa executada.");
                        break;

                    case "3": // Listar estoque <= limite
                        System.out.print("Limite: "); int lim = Integer.parseInt(sc.nextLine().trim());
                        List<GestaoBD.ProdutoDTO> baixos = dao.listarPorEstoqueAte(lim);
                        for (GestaoBD.ProdutoDTO p : baixos) {
                            System.out.println(p.id + " | " + p.nome + " | R$ " + DF.format(p.preco) + " | qtd=" + p.quantidade);
                        }
                        FileTools.appendLog(LOG_FILE, "SUCESSO", "Listagem estoque<= " + lim + " executada.");
                        break;

                    case "4": // Atualizar quantidade
                        System.out.print("ID: "); int idAlvo = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Nova quantidade: "); int novaQtd = Integer.parseInt(sc.nextLine().trim());
                        if (novaQtd < 0) { System.out.println("Quantidade inválida."); break; }
                        int linhas = dao.atualizarQuantidade(idAlvo, novaQtd);
                        System.out.println("Linhas afetadas: " + linhas);
                        FileTools.appendLog(LOG_FILE, "SUCESSO", "Atualização de qtd id=" + idAlvo + " para " + novaQtd + " (linhas=" + linhas + ").");
                        break;

                    case "0":
                        System.out.println("Encerrando...");
                        return;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (SQLException ex) {
                FileTools.appendLog(LOG_FILE, "ERRO", "Falha na operação do menu.", ex);
                System.err.println("Ocorreu um erro ao acessar o banco: " + ex.getMessage());
            } catch (Exception ex) {
                FileTools.appendLog(LOG_FILE, "ERRO", "Falha inesperada.", ex);
                System.err.println("Erro inesperado: " + ex.getMessage());
            }
        }
    }
}

================================================================================
FIM DO BLOCO DE DOCUMENTAÇÃO
================================================================================
*/
