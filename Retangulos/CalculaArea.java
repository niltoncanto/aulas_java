import java.util.Scanner;
class CalculaArea {
  public static void main(String[] args) {
    
    Scanner scr = new Scanner(System.in);
    float a = 0;
    float l = 0;
    try{
      System.out.print("Entre com 1 para Quad e 2 para Ret: ");
      int tipo = scr.nextInt();
      switch(tipo)
      {
        case 1:
            Quadrado quad = new Quadrado();
            System.out.print("Entre com o lado do quad em cm: ");
            l = scr.nextFloat();
            quad.setLargura(l);
            System.out.println("Area: " + quad.calArea());
            System.out.println("Perimetro: " + quad.calPerimetro());
            break;
        case 2:
          Retangulo ret = new Retangulo();
          do{
            System.out.println("Entre com a largura em cm: ");
            l = scr.nextFloat();
          }while(l<=0);
    
          do{
          System.out.print("Entre com a altura em cm: ");
            a = scr.nextFloat();
          }while(a<=0);
          
          ret.setAltura(a);
          ret.setLargura(l);
          System.out.println("Area: " + ret.calArea());
          System.out.println("Perimetro: " + ret.calPerimetro());
          break;
      }
    
      }catch(Exception e){
      System.out.println("Entrada inválida, tente novamente");
      System.out.println(e);
    }  

    
  }
}