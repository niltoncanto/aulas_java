public class StringBuild {
    public static void main(String[] args){
        String nome = "Nilton";
        nome.concat(" Canto");
        //String nomeCompleto = nome.concat(" Canto");

        System.out.println(nome);           // Nilton (original, não mudou)
        //System.out.println(nomeCompleto); // Nilton Canto (nova string)

        StringBuilder nome = new StringBuilder("Nilton");  // MUTÁVEL
        nome.append(" Canto");
        System.out.println(nome); // imprime "Nilton Canto"
    }
}
