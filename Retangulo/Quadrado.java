public class Quadrado extends Retangulo {
    public float calculaArea(float l, float a){
        return l*a;
    }
    public float calculaPerimetro(float l, float a){
        return 2*l+2*a;
    }
    public float calculaArea(float l){
        return (float) Math.pow(l,2);
    }
    public float calculaPerimetro(float l){
        return 4*l;
    }

}


/* Criar uma classe para um Retângulo. A classe possui os atributos comprimento e largura. 
Esta classe deve possuir métodos que calculam o perímetro e a área do retângulo. 
A criação da classe deve garantir que comprimento e largura assumam valores maiores do que 0.*/