public class Retangulo {
  protected float largura;
  private float altura;

  public void setLargura(float l) {
    this.largura = l;
  }

  public float getLargura() {
    return largura;
  }

  public void setAltura(float a) {
    this.altura = a;
  }

  public float getAltura() {
    return altura;
  }

  public float calArea() {
    return this.largura*this.largura;
  }

  public float calPerimetro() {
    return this.largura * 2 + this.altura * 2;
  }
}

/*
 * Criar uma classe para um Retângulo. A classe possui os atributos comprimento
 * e largura. Esta classe deve possuir métodos que calculam o perímetro e a área
 * do retângulo. A criação da classe deve garantir que comprimento e largura
 * assumam valores maiores do que 0.
 */