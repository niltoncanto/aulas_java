/*
 Objetivo: criar e manipular uma classe Carro que represente as características e comportamentos básicos de um carro.

Parte 1: Declaração da Classe e Atributos

1. Crie uma classe chamada Carro.
2. Declare os seguintes atributos privados:
   - `marca`: String que armazena a marca do carro.
   - `modelo`: String que armazena o modelo do carro.
   - `ano`: Inteiro que armazena o ano do carro.
   - `ligado`: Booleano que indica se o carro está ligado ou desligado.

   Parte 2: Construtor

1. Crie um construtor que aceite os parâmetros `marca`, `modelo` e `ano`.
   - Inicialize os atributos com os valores fornecidos.
   - Inicialize o atributo `ligado` como `false`, já que o carro começa desligado.
Parte 3: Métodos

1. Crie um método `ligar` sem parâmetros:
   - Atribua o valor verdadeiro ao atributo `ligado`.
   - Mostre a mensagem "Carro ligado!".
2. Crie um método `desligar` sem parâmetros:
   - Atribua o valor falso ao atributo `ligado`.
   - Mostre a mensagem "Carro desligado!".
3. Crie um método `mostrarInfo` sem parâmetros:
   - Mostre as informações do carro, incluindo a marca, o modelo, o ano e se está ligado ou não.

Parte 4:
Demonstração da classe:
   - Instancie um objeto da classe Carro com os valores "Ford", "Fiesta", 2020.
   - Chame o método `mostrarInfo` para mostrar as informações iniciais do carro.
   - Chame o método `ligar` para ligar o carro e depois chame `mostrarInfo` novamente.
   - Chame o método `desligar` para desligar o carro e depois chame `mostrarInfo` mais uma vez.
 */


public class Carro{
    //atributos
    private String marca;
    private int ano;
    private String modelo;
    private boolean ligado;

    //metodo contrutor da classe
    public Carro(String marca, int ano, String modelo){
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.ligado = false;
    }
    //ligar motor
    public void ligar(){
        ligado = true;
        System.out.print("Motor ligado!");
    }
    //desligar motor
    public void desligar(){
        ligado = false;
        System.out.print("Motor desligado!");
    }
    //mostrar informações
    public void mostraInfo(){
        System.out.println("Marca:"+marca);
        System.out.println("Modelo:"+modelo);
        System.out.println("Ano:"+ano);
        System.out.println("Status:"+ligado);
    }

    public static void main(String[] args){
        //criação do objeto carro
        Carro  c1 = new Carro("fiat", 1970, "147");
        Carro  c2 = new Carro("volvo", 1999, "xxx");
        c1.mostraInfo();
        c2.mostraInfo();
        c1.ligar();
    }

}