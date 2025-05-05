import java.util.Random;
import java.time.LocalDate;

class Cnh {
  //atributos da classe Cnh
  private int numero;
  private int cpf;
  private int pontos;
  private char tipo; // A, B, C
  private int situacao; //1.válida, 2.apr. 3. vencida;
  private LocalDate expedicao;
  private LocalDate vencimento;
  //método contrutor
  Cnh(){
    Random n = new Random();
    LocalDate hoje = LocalDate.now();
    this.numero = n.nextInt();
    this.cpf = n.nextInt();
    this.pontos =0;
    this.situacao =1;
    this.tipo = 'A';
    this.expedicao = hoje;
    this.vencimento = hoje.plusDays(730);
  }
  //método get
  public char getTipo(){
    return tipo;
  }
  //método set
  public void setTipo(char t){
    this.tipo=t;
  }
  //método get
  public int getSituacao(){
    return situacao;
  }
  //método set
  public void setSituacao(int s){
    this.situacao=s;
  }
    //método get
  public int getPontos(){
    return pontos;
  }
  //método set
  public void setPontos(int p){
    this.pontos+=p;
    if (this.pontos>40){
      this.setSituacao(3);
    }
  }
    //método get
  public int getNumero(){
    return numero;
  }
  //método set
  public void setNumero(int n){
    this.numero=n;
  }
}