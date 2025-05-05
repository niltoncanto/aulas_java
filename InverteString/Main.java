import java.util.Scanner;
class Main{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite uma frase: ");
        String frase = scan.nextLine();
        for (int i = frase.length()-1; i>=0; i--){
            System.out.print(frase.charAt(i));
        }
    }
}