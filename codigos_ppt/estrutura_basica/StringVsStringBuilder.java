public class StringVsStringBuilder {
    public static void main(String[] args) {
        // 🔹 Exemplo com String (imutável)
        System.out.println("🔹 Exemplo com String:");
        String texto = "Olá";
        texto += ", mundo!";  // Cria um novo objeto String
        texto += " Como vai?";
        System.out.println(texto); // Saída: Olá, mundo! Como vai?

        // 🔹 Exemplo com StringBuilder (mutável)
        System.out.println("\n🔹 Exemplo com StringBuilder:");
        StringBuilder sb = new StringBuilder("Olá");
        sb.append(", mundo!");  // Modifica no mesmo espaço de memória
        sb.append(" Como vai?");
        System.out.println(sb.toString()); // Saída: Olá, mundo! Como vai?


        // 🔹 Demonstrando métodos úteis do StringBuilder
        System.out.println("\n🔹 Métodos úteis do StringBuilder:");
        StringBuilder frase = new StringBuilder("Eu gosto de Python");

        frase.replace(12, 18, "Java"); // Substitui "Python" por "Java"
        System.out.println("replace(): " + frase); // Saída: Eu gosto de Java

        frase.insert(0, "Aprender "); // Insere no início
        System.out.println("insert(): " + frase); // Saída: Aprender Eu gosto de Java

        frase.delete(0, 9); // Remove "Aprender "
        System.out.println("delete(): " + frase); // Saída: Eu gosto de Java

        frase.reverse(); // Inverte o texto
        System.out.println("reverse(): " + frase); // Saída: avaJ ed otsog uoE

                // 🔹 Comparando velocidade de concatenação
                System.out.println("\n🔹 Comparando desempenho (concatenação 10000 vezes):");

                // Medindo tempo para String
                long inicioString = System.currentTimeMillis();
                String str = "";
                for (int i = 0; i < 10000; i++) {
                    str += "a";  // Ineficiente (cria um novo objeto a cada iteração)
                }
                long tempoString = System.currentTimeMillis() - inicioString;
                System.out.println("Tempo com String: " + tempoString + "ms");
        
                // Medindo tempo para StringBuilder
                long inicioSB = System.currentTimeMillis();
                StringBuilder sbTest = new StringBuilder();
                for (int i = 0; i < 10000; i++) {
                    sbTest.append("a");  // Muito mais eficiente!
                }
                long tempoSB = System.currentTimeMillis() - inicioSB;
                System.out.println("Tempo com StringBuilder: " + tempoSB + "ms");
        
    }
}

