/*
Manipulação de Texto com String e StringBuilder
Crie um programa em Java que permita ao usuário inserir uma frase e realizar operações de manipulação de texto usando tanto String quanto StringBuilder.

O programa deve:
1 Solicitar uma frase ao usuário (entrada como String).
2 Exibir o tamanho da frase (número de caracteres).
3 Converter a frase para maiúsculas usando String.
4 Reverter a frase usando StringBuilder.
5 Substituir todas as vogais por * usando StringBuilder.
 */

import java.util.Scanner;
public class String {
    public static void main(String[] args) {
        // 1. Criar Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Solicita a frase ao usuário
        System.out.print("Digite uma frase: ");
        String frase = scanner.nextLine(); // lê a linha inteira (pode ter espaços)

        // 2. Exibir o tamanho da frase
        System.out.println("\n--- Resultados ---");
        System.out.println("Tamanho da frase: " + frase.length() + " caracteres");

        // 3. Converter a frase para maiúsculas
        String fraseMaiuscula = frase.toUpperCase();
        System.out.println("Frase em maiúsculas: " + fraseMaiuscula);

        // 4. Reverter a frase usando StringBuilder
        StringBuilder sb = new StringBuilder(frase);  // cria um StringBuilder com a frase
        String fraseReversa = sb.reverse().toString(); // reverse() inverte e toString() volta para String
        System.out.println("Frase invertida: " + fraseReversa);

        // 5. Substituir todas as vogais por '*' usando StringBuilder
        StringBuilder substituida = new StringBuilder(frase); // novo StringBuilder para modificar
        for (int i = 0; i < substituida.length(); i++) {
            char c = substituida.charAt(i); // pega cada caractere
            if ("AEIOUaeiou".indexOf(c) != -1) { // verifica se é vogal
                substituida.setCharAt(i, '*');  // substitui por '*'
            }
        }
        System.out.println("Frase com vogais substituídas: " + substituida);
        // Fecha o scanner
        scanner.close();
    }
}
