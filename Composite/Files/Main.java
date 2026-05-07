import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String[] args) {
        // Criando arquivos
        File file1 = new File("documento.txt");
        File file2 = new File("imagem.jpg");
        File file3 = new File("planilha.xlsx");

        // Criando diretórios
        Directory dir1 = new Directory("Documentos");
        Directory dir2 = new Directory("Imagens");
        Directory root = new Directory("C:");

        // Adicionando arquivos aos diretórios
        dir1.add(file1);
        dir1.add(file3);
        dir2.add(file2);

        // Adicionando diretórios ao diretório raiz
        root.add(dir1);
        root.add(dir2);

        // Imprimindo a estrutura do sistema de arquivos
        root.printName();
    }

}

interface FileSystemNode {
    void printName();
}

class File implements FileSystemNode {
    private String name;
    public File(String name) { this.name = name; }
    public void printName() { System.out.println("Arquivo: " + name); }
}

class Directory implements FileSystemNode {
    private String name;
    private List<FileSystemNode> children = new ArrayList<>();
    public Directory(String name) { this.name = name; }
    public void add(FileSystemNode node) { children.add(node); }
    public void printName() {
        System.out.println("Pasta: " + name);
        for (FileSystemNode n : children) n.printName();
    }
}

