import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Arquivos{
  public static void createFile(String path){
    try{
      File arq = new File(path);
      if(arq.createNewFile()){
        System.out.println("Arquivo criado: " + arq.getName());
      }else{
        System.out.println("Arquivo já existe!");
      }
      
    }catch(Exception e){
      System.out.println("Erro!");
      e.printStackTrace();
    }
  }
  //==============================
  public static void writeFile(String path, String texto){
    try{
      FileWriter arq = new FileWriter(path);
      arq.write(texto);
      arq.close();
    }catch(Exception e){
      System.out.println("Erro!");
      e.printStackTrace();
    }
  }
  //==============================
  public static void readFile(String path){
    try{
      FileReader arq = new FileReader(path);
      Scanner scr = new Scanner(arq);
      while(scr.hasNextLine()){
        String linha = scr.nextLine();
        System.out.println(linha);
      }
      
    }catch(Exception e){
      System.out.println("Erro!");
      e.printStackTrace();
    }
  }
  //==============================
  public static void deleteFile(String path){
    try{
      File arq = new File(path);
      if(arq.delete()){
        System.out.println("Arquivo apagado: "+ arq.getName());
      }else{
        System.out.println("Erro ao apagar o arquivo!");
      }
    }catch(Exception e){
      System.out.println("Erro");
      e.printStackTrace();
    }
  }

  
}