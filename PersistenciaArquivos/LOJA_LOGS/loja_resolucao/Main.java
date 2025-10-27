import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String DB_FILE = "loja.db";
    private static final String LOG_FILE = "loja.log";
    private static final DecimalFormat DF = new DecimalFormat("#0.00");
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        GerenciarBD dao = new GerenciarBD(DB_FILE);

        try {
            dao.criarTabelaSeNaoExistir();
            FileTools.appendLog(LOG_FILE, "SUCESSO", "Tabela 'produtos' verificada/criada.");
        } catch (SQLException ex) {
            FileTools.appendLog(LOG_FILE, "ERRO", "Falha ao criar/verificar tabela.", ex);
            System.err.println("Não foi possível preparar a tabela. Encerrando.");
            return;
        }

        while (true) {
            mostrarMenu();
            String op = lerLinha("Opção: ").trim();
            try {
                switch (op) {
                    case "1":
                        acaoAdicionarProduto(dao);
                        break;
                    case "2":
                        acaoListarTodos(dao);
                        break;
                    case "3":
                        acaoListarEstoqueAte(dao);
                        break;
                    case "4":
                        acaoAtualizarQuantidade(dao);
                        break;
                    case "0":
                        System.out.println("Encerrando...");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
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

    private static void mostrarMenu() {
        System.out.println("\n====================== MENU ======================");
        System.out.println("1 - Adicionar produto");
        System.out.println("2 - Listar todos os produtos");
        System.out.println("3 - Listar produtos com estoque baixo (<= limite)");
        System.out.println("4 - Atualizar estoque de um produto (por id)");
        System.out.println("0 - Sair");
        System.out.println("=================================================");
    }

    private static void acaoAdicionarProduto(GerenciarBD dao) throws SQLException {
        String nome = lerLinha("Nome: ").trim();
        if (nome.isEmpty()) {
            System.out.println("Nome não pode ser vazio.");
            FileTools.appendLog(LOG_FILE, "ERRO", "Tentativa de inserir com nome vazio.");
            return;
        }

        Double preco = lerDouble("Preço (use ponto como separador decimal): ");
        if (preco == null || preco < 0) {
            System.out.println("Preço inválido (deve ser >= 0).");
            FileTools.appendLog(LOG_FILE, "ERRO", "Tentativa de inserir com preço inválido.");
            return;
        }

        Integer qtd = lerInteiro("Quantidade: ");
        if (qtd == null || qtd < 0) {
            System.out.println("Quantidade inválida (deve ser >= 0).");
            FileTools.appendLog(LOG_FILE, "ERRO", "Tentativa de inserir com quantidade inválida.");
            return;
        }

        long id = dao.inserirProduto(nome, preco, qtd);
        if (id > 0) {
            System.out.println("Produto inserido com sucesso! id=" + id);
            FileTools.appendLog(LOG_FILE, "SUCESSO", "Produto '" + nome + "' inserido (id " + id + ").");
        } else {
            System.out.println("Produto inserido, mas não foi possível obter o ID.");
            FileTools.appendLog(LOG_FILE, "SUCESSO", "Produto '" + nome + "' inserido (sem id gerado).");
        }
    }

    private static void acaoListarTodos(GerenciarBD dao) throws SQLException {
        List<GerenciarBD.ProdutoDTO> todos = dao.listarTodos();
        if (todos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out.println("\nID | NOME                       | PREÇO   | QTD");
            System.out.println("-----------------------------------------------");
            for (GerenciarBD.ProdutoDTO p : todos) {
                System.out.printf("%-3d| %-26s | R$ %6s | %d%n",
                        p.id, abreviar(p.nome, 26), DF.format(p.preco), p.quantidade);
            }
        }
        FileTools.appendLog(LOG_FILE, "SUCESSO", "Listagem completa executada. (qtd=" + todos.size() + ")");
    }

    private static void acaoListarEstoqueAte(GerenciarBD dao) throws SQLException {
        Integer lim = lerInteiro("Listar produtos com estoque <= (limite): ");
        if (lim == null || lim < 0) {
            System.out.println("Limite inválido (deve ser >= 0).");
            FileTools.appendLog(LOG_FILE, "ERRO", "Tentativa de listar com limite inválido.");
            return;
        }

        List<GerenciarBD.ProdutoDTO> baixos = dao.listarPorEstoqueAte(lim);
        if (baixos.isEmpty()) {
            System.out.println("Nenhum produto com estoque <= " + lim + ".");
        } else {
            System.out.println("\nID | NOME                       | PREÇO   | QTD");
            System.out.println("-----------------------------------------------");
            for (GerenciarBD.ProdutoDTO p : baixos) {
                System.out.printf("%-3d| %-26s | R$ %6s | %d%n",
                        p.id, abreviar(p.nome, 26), DF.format(p.preco), p.quantidade);
            }
        }
        FileTools.appendLog(LOG_FILE, "SUCESSO", "Listagem estoque <= " + lim + " executada. (qtd=" + baixos.size() + ")");
    }

    private static void acaoAtualizarQuantidade(GerenciarBD dao) throws SQLException {
        Integer idAlvo = lerInteiro("ID do produto a atualizar: ");
        if (idAlvo == null || idAlvo <= 0) {
            System.out.println("ID inválido.");
            FileTools.appendLog(LOG_FILE, "ERRO", "Tentativa de atualizar com ID inválido.");
            return;
        }

        Integer novaQtd = lerInteiro("Nova quantidade: ");
        if (novaQtd == null || novaQtd < 0) {
            System.out.println("Quantidade inválida (deve ser >= 0).");
            FileTools.appendLog(LOG_FILE, "ERRO", "Tentativa de atualizar com quantidade inválida.");
            return;
        }

        int linhas = dao.atualizarQuantidade(idAlvo, novaQtd);
        System.out.println("Linhas afetadas: " + linhas);
        if (linhas > 0) {
            FileTools.appendLog(LOG_FILE, "SUCESSO", "Atualização de qtd id=" + idAlvo + " para " + novaQtd + " (linhas=" + linhas + ").");
        } else {
            FileTools.appendLog(LOG_FILE, "ERRO", "Nenhuma linha afetada ao atualizar id=" + idAlvo + ".");
        }
    }

    // ---------- Utilidades ----------
    private static String lerLinha(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }

    private static Integer lerInteiro(String prompt) {
        try {
            System.out.print(prompt);
            String s = SC.nextLine().trim();
            if (s.isEmpty()) return null;
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            System.out.println("Valor inválido. Digite um número inteiro.");
            return null;
        }
    }

    private static Double lerDouble(String prompt) {
        try {
            System.out.print(prompt);
            String s = SC.nextLine().trim();
            if (s.isEmpty()) return null;
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            System.out.println("Valor inválido. Digite um número decimal (use ponto).");
            return null;
        }
    }

    private static String abreviar(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, Math.max(0, max - 1)) + "…";
    }
}
