import java.util.Scanner;
public class CalculoArea{
    public static void main(String [] args) {
        Quadrado r = new Quadrado();
        Scanner scr = new Scanner(System.in);
        float l=0;
        float a=0;
        int   tipo=0;
        try{
            System.out.print("Digite 1 para Retângulo e 2 para Quadrado: ");
            tipo = scr.nextInt();
            switch (tipo){
                case 1:
                    do{
                        System.out.print("Entre com a largura: ");
                        l = scr.nextFloat();
                    }while (l<=0);

                    do{
                        System.out.print("Entre com a altura: ");
                        a = scr.nextFloat();
                    }while(a<=0);
                    System.out.println("Área do Retângulo: " + r.calculaArea(l, a));
                    System.out.println("Perimetro do Retângulo: " + r.calculaPerimetro(l, a));
                    break;
                case 2:
                    do{
                        System.out.print("Entre com o lado do quadrado: ");
                        l = scr.nextFloat();
                    }while (l<=0);
                    System.out.println("Área do Quadrado: " + r.calculaArea(l));
                    System.out.println("Perimetro do Quadrado: " + r.calculaPerimetro(l));
                    break;
            }
        }catch(Exception e){
            System.out.print("Ops! Algo errado na sua entrada, execute novamente. \n" + e);
        }
    }

}
