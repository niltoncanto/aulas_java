/**
 * Benchmark simples: String vs StringBuilder
 * - Compara o tempo de 1.000.000 concatenações usando String (+=)
 *   e usando StringBuilder (append).
 * - Inclui warm-up para "esquentar" o JIT e reduzir variação.
 * - Imprime tempos em milissegundos e o tamanho final das strings
 *   para evitar eliminação de código pelo otimizador.
 *
 * Observações:
 * - Em máquinas mais lentas, 1.000.000 pode demorar com String.
 *   Se ficar muito demorado, reduza para 200_000.
 * - Opcional: ajuste a capacidade inicial do StringBuilder para
 *   reduzir realocações (ex.: new StringBuilder(capacity)).
 */
public class BenchmarkStringVsStringBuilder {

    // Quantidade de iterações do teste (ajuste se necessário)
    private static final int N = 1_000_000;

    public static void main(String[] args) {
        // -------- Warm-up (aquecimento) --------
        // Executa uma versão reduzida antes do benchmark real
        // para o JIT "otimizar" os trechos quentes de código.
        warmUp(100_000);

        // -------- Benchmark com String (+=) --------
        long startString = System.nanoTime(); // marca tempo inicial de alto resolução

        String s = ""; // String imutável: cada "+=" cria um NOVO objeto String
        for (int i = 0; i < N; i++) {
            // Concatena o número i e um caractere separador
            // OBS: isso cria uma nova String a cada iteração
            s += i;         // equivalente a: s = new StringBuilder().append(s).append(i).toString();
            s += ',';       // mais uma nova String
        }

        long endString = System.nanoTime();   // marca tempo final
        long timeStringNs = endString - startString; // tempo em nanossegundos
        double timeStringMs = timeStringNs / 1_000_000.0; // converte para ms

        // Imprime o tamanho final como "checksum" para evitar dead-code elimination
        System.out.println("String: tamanho final = " + s.length());
        System.out.printf("String: tempo = %.2f ms%n", timeStringMs);

        // -------- Benchmark com StringBuilder (append) --------
        // Dica: informar uma capacidade inicial razoável reduz realocações internas.
        // Como estimativa grosseira, cada número pode ter até ~7-10 chars + vírgula.
        // Para N=1_000_000, reserve ~9 a 12 milhões de caracteres.
        int estimatedCapacity = 12_000_000;
        StringBuilder sb = new StringBuilder(estimatedCapacity); // StringBuilder mutável

        long startSB = System.nanoTime();

        for (int i = 0; i < N; i++) {
            // Modifica o MESMO objeto, sem criar novas strings a cada iteração
            sb.append(i);
            sb.append(',');
        }

        String resultSB = sb.toString(); // converte para String só no final
        long endSB = System.nanoTime();
        long timeSBNs = endSB - startSB;
        double timeSBMs = timeSBNs / 1_000_000.0;

        System.out.println("StringBuilder: tamanho final = " + resultSB.length());
        System.out.printf("StringBuilder: tempo = %.2f ms%n", timeSBMs);

        // -------- Comparação percentual --------
        // Quanto o StringBuilder foi mais rápido em relação ao String?
        double improvement = ((timeStringMs - timeSBMs) / timeStringMs) * 100.0;
        System.out.printf("Ganho aproximado do StringBuilder: %.2f%%%n", improvement);
    }

    /**
     * Fase de aquecimento para o JIT.
     * Executa algumas concatenações menores só para "esquentar" a JVM.
     *
     * @param iterations quantidade de iterações de warm-up
     */
    private static void warmUp(int iterations) {
        // Warm-up para String
        String s = "";
        for (int i = 0; i < iterations; i++) {
            s += i;
        }

        // Warm-up para StringBuilder
        StringBuilder sb = new StringBuilder(iterations * 5);
        for (int i = 0; i < iterations; i++) {
            sb.append(i);
        }

        // Usa os resultados para evitar que o compilador descarte o trabalho
        if (s.length() + sb.length() == -1) {
            System.out.println("Nunca será impresso (antieliminação de código).");
        }
    }
}
