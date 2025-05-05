public class Quadrado extends Retangulo{
  //sobreposição (override).
  public float calArea() {
     return this.largura*this.largura;
  }

  public float calPerimetro() {
    return this.largura*4;
  }
}

/*
 * Criar uma classe para um Retângulo. A classe possui os atributos comprimento
 * e largura. Esta classe deve possuir métodos que calculam o perímetro e a área
 * do retângulo. A criação da classe deve garantir que comprimento e largura
 * assumam valores maiores do que 0.
 */