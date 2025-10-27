// FileTools.java
// Classe utilitária com métodos estáticos para criar, escrever, ler e deletar arquivos .txt/.log.
// Todos os métodos são comentados linha a linha para fins didáticos.
 
import java.io.BufferedWriter;            // Escrita eficiente em arquivos
import java.io.File;                      // Representa caminhos/arquivos
import java.io.FileWriter;                // Escreve caracteres em um arquivo
import java.io.IOException;               // Exceções de I/O
import java.nio.charset.StandardCharsets; // Charset padrão (UTF-8)
import java.nio.file.Files;               // Operações utilitárias com arquivos
import java.nio.file.Path;                // Representa um caminho (NIO)
import java.nio.file.Paths;               // Fábrica de Path
import java.time.LocalDateTime;           // Data/hora atual
import java.time.format.DateTimeFormatter;// Formatação de data/hora
import java.util.List;                    // Lista de linhas
 
public class FileTools {
 
    // Garante que o diretório pai exista antes de criar/usar um arquivo.
    private static void ensureParentDirectory(Path path) throws IOException {
        Path parent = path.getParent(); // Diretório pai (pode ser null)
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent); // Cria diretórios necessários
        }
    }
 
    // Cria o arquivo se ele não existir.
    public static void createFileIfNotExists(String filePath) throws IOException {
        Path path = Paths.get(filePath);     // Converte String para Path
        ensureParentDirectory(path);         // Garante diretório pai
        if (!Files.exists(path)) {           // Se não existe...
            Files.createFile(path);          // Cria o arquivo vazio
        }
    }
 
    // Escreve UMA linha no arquivo. append=true adiciona ao final; false sobrescreve.
    public static void writeLine(String filePath, String line, boolean append) throws IOException {
        createFileIfNotExists(filePath);     // Garante existência do arquivo
        try (FileWriter fw = new FileWriter(new File(filePath), append);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(line);                  // Escreve a linha
            bw.newLine();                    // Quebra de linha do SO
            // try-with-resources fecha bw e fw automaticamente
        }
    }
 
    // Lê todas as linhas de um arquivo texto e retorna como lista.
    public static List<String> readAll(String filePath) throws IOException {
        Path path = Paths.get(filePath);     // Caminho do arquivo
        createFileIfNotExists(filePath);     // Cria vazio se não existir
        return Files.readAllLines(path, StandardCharsets.UTF_8); // Lê todas as linhas
    }
 
    // Deleta o arquivo se existir. Retorna true se deletou; false se não havia arquivo.
    public static boolean deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);     // Caminho para Path
        return Files.deleteIfExists(path);   // Deleta se existir
    }
 
    // Retorna timestamp legível no padrão "yyyy-MM-dd HH:mm:ss"
    private static String nowString() {
        LocalDateTime now = LocalDateTime.now();                 // Agora
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(fmt);                                  // Formata
    }
 
    // Escreve linha de log no formato: [dataHora] [NIVEL] mensagem
    public static void appendLog(String logPath, String level, String message) {
        try {
            String line = "[" + nowString() + "] [" + level + "] " + message; // Monta a linha
            writeLine(logPath, line, true);                                    // Acrescenta ao final
        } catch (IOException e) {
            System.err.println("Falha ao escrever no log: " + e.getMessage()); // Fallback no console
        }
    }
 
    // Versão de log que inclui classe e mensagem de exceção + primeiro frame da stack.
    public static void appendLog(String logPath, String level, String message, Exception ex) {
        String exMsg = (ex != null && ex.getMessage() != null) ? ex.getMessage() : "sem mensagem";
        String where = (ex != null && ex.getStackTrace().length > 0)
                ? ex.getStackTrace()[0].toString()
                : "sem stack";
        String full = message + " | excecao=" + (ex != null ? ex.getClass().getSimpleName() : "null")
                + ": " + exMsg + " @ " + where;
        appendLog(logPath, level, full); // Reaproveita método acima
    }
}
