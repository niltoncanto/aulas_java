// ===============================================================
// Objetivo: aplicar transformações (numeração de linhas, cifra de César,
// compressão RLE e assinatura) compondo objetos em cadeia.
// Obs.: Não usamos classe abstrata base para decoradores; cada decorador
//       implementa a mesma interface e mantém referência ao "documento"
//       encapsulado.
// ===============================================================

public class SolucaoDecoratorSimples {

    // -----------------------------
    // 1) Interface comum
    // -----------------------------
    interface Documento {
        // Retorna o conteúdo atual do documento (após as transformações)
        String getConteudo();
    }

    // -----------------------------
    // 2) Componente concreto
    // -----------------------------
    static class TextoSimples implements Documento {
        // Conteúdo original do texto
        private final String conteudo;

        // Recebe o conteúdo no construtor
        public TextoSimples(String conteudo) {
            this.conteudo = conteudo;
        }

        // Retorna o conteúdo original sem alterações
        @Override
        public String getConteudo() {
            return conteudo;
        }
    }

    // --------------------------------------------------------
    // 3) Decorador CONCRETO - Numeração de Linhas
    // --------------------------------------------------------
    static class NumeradorDeLinhas implements Documento {
        // Referência ao documento "encapsulado"
        private final Documento documento;

        // Construtor recebe o documento a ser numerado
        public NumeradorDeLinhas(Documento documento) {
            this.documento = documento;
        }

        // Adiciona "1: ...", "2: ..." em cada linha
        @Override
        public String getConteudo() {
            // Divide por quebras de linha (Windows/Linux/Mac)
            String[] linhas = documento.getConteudo().split("\\R", -1);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < linhas.length; i++) {
                // Número da linha começa em 1
                sb.append(i + 1).append(": ").append(linhas[i]);
                // Evita \n extra ao final
                if (i < linhas.length - 1) sb.append(System.lineSeparator());
            }
            return sb.toString();
        }
    }

    // --------------------------------------------------------
    // 4) Decorador CONCRETO - Cifra de César (deslocamento N)
    // --------------------------------------------------------
    static class CifraDeCesar implements Documento {
        private final Documento documento; // referência ao documento interno
        private final int deslocamento;    // deslocamento (padrão recomendado: 3)

        // Construtor com deslocamento explícito
        public CifraDeCesar(Documento documento, int deslocamento) {
            this.documento = documento;
            this.deslocamento = deslocamento;
        }

        // Construtor com deslocamento padrão = 3
        public CifraDeCesar(Documento documento) {
            this(documento, 3);
        }

        @Override
        public String getConteudo() {
            String original = documento.getConteudo();
            StringBuilder sb = new StringBuilder();

            // Aplica cifra apenas em letras A-Z / a-z; mantém outros caracteres
            for (char c : original.toCharArray()) {
                if (c >= 'a' && c <= 'z') {
                    int base = 'a';
                    char enc = (char) (base + (c - base + deslocamento) % 26);
                    sb.append(enc);
                } else if (c >= 'A' && c <= 'Z') {
                    int base = 'A';
                    char enc = (char) (base + (c - base + deslocamento) % 26);
                    sb.append(enc);
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
    }

    // --------------------------------------------------------
    // 5) Decorador CONCRETO - Compressão simples RLE por linha
    //    Ex.: "aaabb" -> "a3b2" ; "abc" -> "abc"
    // --------------------------------------------------------
    static class CompactadorRLE implements Documento {
        private final Documento documento;

        public CompactadorRLE(Documento documento) {
            this.documento = documento;
        }

        @Override
        public String getConteudo() {
            // Divide por linhas
            String[] linhas = documento.getConteudo().split("\\R", -1);
            StringBuilder out = new StringBuilder();

            for (int l = 0; l < linhas.length; l++) {
                String linha = linhas[l];

                // Mantém linha vazia como está
                if (linha.isEmpty()) {
                    if (l < linhas.length - 1) out.append(System.lineSeparator());
                    continue;
                }

                // Aplica RLE
                StringBuilder sb = new StringBuilder();
                char prev = linha.charAt(0);
                int count = 1;

                for (int i = 1; i < linha.length(); i++) {
                    char c = linha.charAt(i);
                    if (c == prev) {
                        count++;
                    } else {
                        sb.append(prev);
                        if (count > 1) sb.append(count);
                        prev = c;
                        count = 1;
                    }
                }
                // Fecha o último grupo
                sb.append(prev);
                if (count > 1) sb.append(count);

                // Concatena com quebra de linha quando necessário
                out.append(sb.toString());
                if (l < linhas.length - 1) out.append(System.lineSeparator());
            }
            return out.toString();
        }
    }

    // --------------------------------------------------------
    // 6) Decorador CONCRETO - Assinatura/Selo no final
    // --------------------------------------------------------
    static class AssinaturaNoFinal implements Documento {
        private final Documento documento;   // documento interno
        private final String assinatura;     // texto da assinatura

        public AssinaturaNoFinal(Documento documento, String assinatura) {
            this.documento = documento;
            this.assinatura = assinatura;
        }

        @Override
        public String getConteudo() {
            // Acrescenta uma linha com "-- assinatura --" ao final
            return documento.getConteudo()
                    + System.lineSeparator()
                    + "-- " + assinatura + " --";
        }
    }

    // --------------------------------------------------------
    // 7) Demonstração (main): composições exigidas no enunciado
    // --------------------------------------------------------
    public static void main(String[] args) {
        // a) Texto base com múltiplas linhas (explora letras repetidas p/ RLE)
        String textoBase = String.join(System.lineSeparator(),
                "Sistemas  de  Software",
                "Engenharia    de    Software",
                "Programacao   Orientada   a   Objetos",
                "Decorator     Pattern     Teste"
        );

        // Instancia o componente base
        Documento original = new TextoSimples(textoBase);

        // 1) Exibe texto original
        System.out.println("=== (1) Texto Original ===");
        System.out.println(original.getConteudo());

        // 2) Texto após Numeração de Linhas
        Documento numerado = new NumeradorDeLinhas(original);
        System.out.println("\n=== (2) Após Numeração de Linhas ===");
        System.out.println(numerado.getConteudo());

        // 3) Texto após Criptografia de César + Assinatura
        Documento criptAss = new AssinaturaNoFinal(
                new CifraDeCesar(original, 3),
                "Departamento de Engenharia"
        );
        System.out.println("\n=== (3) Após Cifra de César + Assinatura ===");
        System.out.println(criptAss.getConteudo());

        // 4) Texto após Numeração + Compressão RLE + Assinatura
        Documento numCompAss = new AssinaturaNoFinal(
                new CompactadorRLE(new NumeradorDeLinhas(original)),
                "Equipe POO"
        );
        System.out.println("\n=== (4) Após Numeração + RLE + Assinatura ===");
        System.out.println(numCompAss.getConteudo());

        // 5) Ordem livre (ex.: Remoção de espaços extras -> RLE -> Numeração)
        //    Aqui faremos uma versão simples de "normalizar espaços" direto no pipeline,
        //    implementando um decorador mínimo dentro do main para fins didáticos.
        Documento remocaoEspacos = new Documento() {
            private final Documento doc = original;

            @Override
            public String getConteudo() {
                // Normaliza espaços em cada linha (substitui múltiplos por um único)
                String[] linhas = doc.getConteudo().split("\\R", -1);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < linhas.length; i++) {
                    String normalizada = linhas[i].replaceAll("\\s+", " ").trim();
                    sb.append(normalizada);
                    if (i < linhas.length - 1) sb.append(System.lineSeparator());
                }
                return sb.toString();
            }
        };

        Documento pipelineLivre = new NumeradorDeLinhas(new CompactadorRLE(remocaoEspacos));
        System.out.println("\n=== (5) RemoçãoEspaços -> RLE -> Numeração ===");
        System.out.println(pipelineLivre.getConteudo());

        // Comentário (didático):
        // - A ordem das transformações altera o resultado.
        // - Numeração antes da RLE inclui dígitos e dois-pontos na linha, o que afeta a compressão.
        // - Normalizar espaços ANTES da RLE tende a melhorar a compressão.
        // - Assinatura sempre adiciona uma linha ao final, independente das demais transformações.
    }
}
