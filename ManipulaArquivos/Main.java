class Main {
  public static void main(String[] args) {
    String path = "file3.txt";
    String path1 = "file1.txt";
    String texto = "linha 1 - teste escrita\nlinha 2 - teste escrita\n";
    Arquivos arq = new Arquivos();
    arq.createFile(path);
    arq.writeFile(path, texto);
    arq.readFile(path);
    arq.deleteFile(path1);
  }
}