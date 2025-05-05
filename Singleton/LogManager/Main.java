import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

    // Enum para representar os tipos de mensagem
enum TipoLog {
        ERROR, WARNING, INFO, DEBUG
}

// Classe Singleton para gerenciar logs
class LogManager {
    // Instância única do LogManager
    private static LogManager instancia;
    // Define onde o log será gravado: "arquivo" ou "console"
    private String destino = "console";
    // Nome do arquivo de log
    private String nomeArquivo = "logs.txt";
    // Formatter para a data e hora
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // Construtor privado para garantir Singleton
    private LogManager() {}

    // Método para obter a instância Singleton
    public static LogManager getInstancia() {
        if (instancia == null) {
            instancia = new LogManager();
        }
        return instancia;
    }

    // Método para configurar o destino do log
    public void configurarDestino(String destino) {
        if (destino.equalsIgnoreCase("arquivo") || destino.equalsIgnoreCase("console")) {
            this.destino = destino.toLowerCase();
        } else {
            System.out.println("Destino inválido. Usando console como padrão.");
        }
    }

    // Método para registrar mensagens de log
    public void registrar(String mensagem, TipoLog tipo) {
        String dataHora = LocalDateTime.now().format(formatter);
        String logFormatado = "[" + dataHora + "] [" + tipo + "] " + mensagem;

        if (destino.equals("arquivo")) {
            gravarEmArquivo(logFormatado);
        } else {
            System.out.println(logFormatado);
        }
    }

    // Método privado para gravar logs em arquivo
    private void gravarEmArquivo(String mensagem) {
        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo, true))) {
            out.println(mensagem);
        } catch (IOException e) {
            System.out.println("Erro ao gravar no arquivo de log: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        LogManager log = LogManager.getInstancia();

        // Primeira configuração: Console
        //log.configurarDestino("console");
        log.registrar("Aplicação iniciada com sucesso.", TipoLog.INFO);
        log.registrar("Conexão instável detectada.", TipoLog.WARNING);
        log.registrar("Erro ao acessar banco de dados.", TipoLog.ERROR);

        // Segunda configuração: Arquivo
        //log.configurarDestino("arquivo");
        log.registrar("Registrando no arquivo de log.", TipoLog.INFO);
        log.registrar("Aviso importante no log de arquivo.", TipoLog.WARNING);
        log.registrar("Erro fatal registrado no arquivo!", TipoLog.ERROR);
    }
}
