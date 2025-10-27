import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

class FileTools {
    public static boolean createFile(String path){
        try{
            File arq = new File(path);
            return arq.createNewFile();
        } catch (IOException e){
            System.out.println("Erro ao criar o arquivo.");
            e.printStackTrace();
            return false; // <-- retorno no catch
        }
    }

    public static boolean writeFile(String path, String texto){
        try (FileWriter fw = new FileWriter(path)) { // fecha automático
            fw.write(texto);
            return true;
        } catch (IOException e){
            System.out.println("Erro ao escrever no arquivo.");
            e.printStackTrace();
            return false;
        }
    }

    public static void readFile(String path){
        try (FileReader fr = new FileReader(path);
             Scanner scr = new Scanner(fr)) {
            while (scr.hasNextLine()){
                System.out.println(scr.nextLine());
            }
        } catch (IOException e){
            System.out.println("Erro ao ler o arquivo.");
            e.printStackTrace();
        }
    }

    public static void deleteFile(String path){
        File arq = new File(path);
        if (arq.delete()){
            System.out.println("Arquivo apagado: " + arq.getName());
        } else {
            System.out.println("Erro ao apagar o arquivo!");
        }
    }
}

public class Arq {
    public static void main(String[] args) {
        String path  = "file3.txt";
        String path1 = "file1.txt";
        String texto = "linha 1 - teste escrita\nlinha 2 - teste escrita\n";

        FileTools.createFile(path);      // métodos são static
        FileTools.writeFile(path, texto);
        FileTools.readFile(path);
        FileTools.deleteFile(path1);
    }
}
