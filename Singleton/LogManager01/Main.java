import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum TipoLog{
    ERROR, WARNING, INFO, DEBUG
}
class LogManager{
    private static LogManager instance;
    private static String destino = "console";
    private static String aquivo = "log.txt";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LogManager(){} //construtor privado para evitar acesso indevido.

    public static LogManager getInstance(){
        if(instance == null){
            instance = new LogManager();
        }
        return instance;
    }
    public void registrar(String msg, TipoLog tipo){
        //private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logFormatado = "[" + dataHora + "] [" + tipo + "] " + msg;
        System.out.println(logFormatado);

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
        LogManager log = LogManager.getInstance();
        log.registrar("Aplicação iniciada com sucesso.", TipoLog.INFO);
        log.registrar("Conexão instável detectada.", TipoLog.WARNING);
        log.registrar("Erro ao acessar banco de dados.", TipoLog.ERROR);
    }
}
