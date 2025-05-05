public class Array {
    public static void main(String[] args) {
        String[] frutas = {"Maçã", "Banana", "Laranja", "Uva"};

        System.out.println(frutas);
        // Percorrendo o array com for-each (mais simples)
        for (String fruta : frutas) {
            System.out.println("Fruta: " + fruta);
        }
    }
}
