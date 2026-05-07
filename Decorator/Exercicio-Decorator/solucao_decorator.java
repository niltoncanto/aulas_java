
public class ExerciciosDecorator {

    // ----- Interface base -----
    interface Documento {
        String getConteudo();
    }

    // ----- Componente concreto -----
    static class TextoSimples implements Documento {
        private final String conteudo;

        public TextoSimples(String conteudo) {
            this.conteudo = conteudo;
        }

        @Override
        public String getConteudo() {
            return conteudo;
        }
    }

    // ----- Decorator base -----
    static abstract class DecoratorDocumento implements Documento {
        protected final Documento wrappee;

        protected DecoratorDocumento(Documento wrappee) {
            this.wrappee = wrappee;
        }
    }

    // ----- Decorators concretos -----

    // 1) Numeracao de linhas
    static class NumeracaoLinhasDecorator extends DecoratorDocumento {
        public NumeracaoLinhasDecorator(Documento wrappee) {
            super(wrappee);
        }

        @Override
        public String getConteudo() {
            String[] linhas = wrappee.getConteudo().split("\\R");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < linhas.length; i++) {
                sb.append(i + 1).append(": ").append(linhas[i]);
                if (i < linhas.length - 1) sb.append(System.lineSeparator());
            }
            return sb.toString();
        }
    }

    // 2) Criptografia de César (apenas letras ASCII básicas para simplicidade)
    static class CriptografiaCesarDecorator extends DecoratorDocumento {
        private final int deslocamento;

        public CriptografiaCesarDecorator(Documento wrappee) {
            this(wrappee, 3);
        }

        public CriptografiaCesarDecorator(Documento wrappee, int deslocamento) {
            super(wrappee);
            this.deslocamento = deslocamento;
        }

        @Override
        public String getConteudo() {
            String original = wrappee.getConteudo();
            StringBuilder sb = new StringBuilder();
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

    // 3) Compressao simples RLE por linha (ex.: "aaabb" -> "a3b2")
    static class CompressaoRLEDecorator extends DecoratorDocumento {
        public CompressaoRLEDecorator(Documento wrappee) {
            super(wrappee);
        }

        @Override
        public String getConteudo() {
            String[] linhas = wrappee.getConteudo().split("\\R");
            StringBuilder out = new StringBuilder();
            for (int l = 0; l < linhas.length; l++) {
                String linha = linhas[l];
                if (linha.isEmpty()) {
                    // Mantém linha vazia
                    if (l < linhas.length - 1) out.append(System.lineSeparator());
                    continue;
                }
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
                sb.append(prev);
                if (count > 1) sb.append(count);

                out.append(sb.toString());
                if (l < linhas.length - 1) out.append(System.lineSeparator());
            }
            return out.toString();
        }
    }

    // 4) Sufixo de assinatura
    static class SufixoAssinaturaDecorator extends DecoratorDocumento {
        private final String assinatura;

        public SufixoAssinaturaDecorator(Documento wrappee, String assinatura) {
            super(wrappee);
            this.assinatura = assinatura;
        }

        @Override
        public String getConteudo() {
            String sep = System.lineSeparator();
            return wrappee.getConteudo() + sep + "-- " + assinatura + " --";
        }
    }

    // ---- (Opcional) Remocao de espacos extras ----
    static class RemocaoEspacosExtrasDecorator extends DecoratorDocumento {
        public RemocaoEspacosExtrasDecorator(Documento wrappee) {
            super(wrappee);
        }

        @Override
        public String getConteudo() {
            // Remove espacos duplicados e trim em cada linha
            String[] linhas = wrappee.getConteudo().split("\\R");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < linhas.length; i++) {
                String normalizada = linhas[i].replaceAll("\\s+", " ").trim();
                sb.append(normalizada);
                if (i < linhas.length - 1) sb.append(System.lineSeparator());
            }
            return sb.toString();
        }
    }

    // ------------- Demonstração -------------
    public static void main(String[] args) {
        String base = String.join(System.lineSeparator(),
            "Sistemas  de  Software",
            "Engenharia    de    Software",
            "Programacao   Orientada   a   Objetos",
            "Decorator     Pattern     Teste"
        );

        Documento original = new TextoSimples(base);

        // 1) Texto original
        System.out.println("=== Original ===");
        System.out.println(original.getConteudo());

        // 2) Numeracao de Linhas
        Documento numerado = new NumeracaoLinhasDecorator(original);
        System.out.println("\n=== Numerado ===");
        System.out.println(numerado.getConteudo());

        // 3) Criptografia + Assinatura
        Documento criptAss = new SufixoAssinaturaDecorator(
            new CriptografiaCesarDecorator(original, 3),
            "Departamento de Engenharia"
        );
        System.out.println("\n=== Criptografado + Assinatura ===");
        System.out.println(criptAss.getConteudo());

        // 4) Numeracao + Compressao + Assinatura
        Documento pipeline1 = new SufixoAssinaturaDecorator(
            new CompressaoRLEDecorator(new NumeracaoLinhasDecorator(original)),
            "Equipe POO"
        );
        System.out.println("\n=== Numeracao + Compressao + Assinatura ===");
        System.out.println(pipeline1.getConteudo());

        // 5) (Livre) RemocaoEspacos -> Compressao -> Numeracao
        Documento pipeline2 = new NumeracaoLinhasDecorator(
            new CompressaoRLEDecorator(new RemocaoEspacosExtrasDecorator(original))
        );
        System.out.println("\n=== RemocaoEspacos -> Compressao -> Numeracao ===");
        System.out.println(pipeline2.getConteudo());
    }
}
