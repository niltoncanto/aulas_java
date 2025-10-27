// Declaração do pacote (nenhum pacote especificado para facilitar a execução direta em linha de comando)
// (Se necessário, você pode adicionar: package seu.pacote;)

// Importações não são necessárias para este exemplo, pois utilizamos apenas classes básicas do Java

// Declaração da classe pública principal que deve ter o mesmo nome do arquivo: SistemaDeFrete.java
public class SistemaDeFrete { // Início da classe pública principal

    // ==============================
    // 1) Interface FreteStrategy
    // ==============================

    // Interface que define o contrato de cálculo de frete segundo o padrão Strategy
    public interface FreteStrategy { // Início da interface FreteStrategy
        // Método que calcula o valor do frete recebendo peso e valor do pedido
        double calcularFrete(double peso, double valor); // Assinatura do método da estratégia
    } // Fim da interface FreteStrategy

    // ===============================================
    // 2) Estratégias concretas que implementam a interface
    // ===============================================

    // Estratégia de frete para SEDEX: custo = peso * 1.45
    public static class SedexFrete implements FreteStrategy { // Início da classe SedexFrete
        // Implementação do cálculo de frete específico para SEDEX
        @Override // Anotação indicando que estamos sobrescrevendo o método da interface
        public double calcularFrete(double peso, double valor) { // Início do método calcularFrete
            return peso * 1.45; // Retorna o custo multiplicando o peso pelo fator 1.45
        } // Fim do método calcularFrete
    } // Fim da classe SedexFrete

    // Estratégia de frete para PAC: custo = peso * 1.10
    public static class PacFrete implements FreteStrategy { // Início da classe PacFrete
        // Implementação do cálculo de frete específico para PAC
        @Override // Anotação de sobrescrita
        public double calcularFrete(double peso, double valor) { // Início do método calcularFrete
            return peso * 1.10; // Retorna o custo multiplicando o peso pelo fator 1.10
        } // Fim do método calcularFrete
    } // Fim da classe PacFrete

    // Estratégia de Frete Grátis: custo = 0 quando o valor do pedido > 200
    public static class FreteGratis implements FreteStrategy { // Início da classe FreteGratis
        // Implementação do cálculo de frete para frete grátis, condicionado ao valor do pedido
        @Override // Anotação de sobrescrita
        public double calcularFrete(double peso, double valor) { // Início do método calcularFrete
            // Verifica se o valor do pedido é maior que 200 para conceder frete grátis
            if (valor > 200.0) { // Condicional para verificar a elegibilidade do frete grátis
                return 0.0; // Se for elegível, retorna custo zero
            } else { // Caso não seja elegível ao frete grátis
                // Mensagem informativa para fins didáticos (poderia lançar exceção ou usar outra estratégia)
                System.out.println("[Aviso] Frete Grátis não aplicável (valor <= 200). Retornando custo padrão PAC."); // Mensagem de aviso
                return peso * 1.10; // Retorna um custo padrão (ex.: PAC) para manter o exemplo funcional
            } // Fim do else
        } // Fim do método calcularFrete
    } // Fim da classe FreteGratis

    // ==============================
    // 3) Classe CalculadoraFrete
    // ==============================

    // Classe responsável por manter a estratégia atual e delegar o cálculo
    public static class CalculadoraFrete { // Início da classe CalculadoraFrete
        // Atributo privado que armazena a estratégia de frete atual
        private FreteStrategy estrategia; // Atributo 'estrategia' do tipo FreteStrategy

        // Método 'setter' para definir ou trocar a estratégia de frete em tempo de execução
        public void setEstrategia(FreteStrategy estrategia) { // Início do método setEstrategia
            this.estrategia = estrategia; // Atribui a estratégia recebida ao atributo interno
        } // Fim do método setEstrategia

        // Sobrecarga 1: cálculo de frete recebendo apenas o peso (valor do pedido assumido como 0)
        public double calcular(double peso) { // Início do método calcular (apenas peso)
            // Verifica se uma estratégia foi definida antes de calcular
            if (this.estrategia == null) { // Se estratégia for nula
                throw new IllegalStateException("Estratégia de frete não definida."); // Lança exceção para indicar uso incorreto
            } // Fim do if
            return this.estrategia.calcularFrete(peso, 0.0); // Delegação do cálculo para a estratégia, usando valor 0.0
        } // Fim do método calcular (apenas peso)

        // Sobrecarga 2: cálculo de frete recebendo peso e valor do pedido (necessário para Frete Grátis)
        public double calcular(double peso, double valor) { // Início do método calcular (peso e valor)
            // Verifica se uma estratégia foi definida antes de calcular
            if (this.estrategia == null) { // Se estratégia for nula
                throw new IllegalStateException("Estratégia de frete não definida."); // Lança exceção para indicar uso incorreto
            } // Fim do if
            return this.estrategia.calcularFrete(peso, valor); // Delegação do cálculo para a estratégia com peso e valor
        } // Fim do método calcular (peso e valor)
    } // Fim da classe CalculadoraFrete

    // ==============================
    // 4) Método main (Classe Principal)
    // ==============================

    // Ponto de entrada do programa onde demonstramos o uso do padrão Strategy
    public static void main(String[] args) { // Início do método main
        // Instancia a calculadora de frete que usará estratégias intercambiáveis
        CalculadoraFrete calculadora = new CalculadoraFrete(); // Cria uma nova CalculadoraFrete

        // Define a estratégia como SedexFrete e calcula o frete para peso = 10
        calculadora.setEstrategia(new SedexFrete()); // Define a estratégia atual para SEDEX
        double freteSedex = calculadora.calcular(10.0); // Calcula o frete usando apenas o peso
        System.out.printf("Frete (SEDEX) para peso 10.0: R$ %.2f%n", freteSedex); // Imprime o resultado formatado

        // Define a estratégia como PacFrete e calcula o frete para peso = 10
        calculadora.setEstrategia(new PacFrete()); // Troca a estratégia para PAC
        double fretePac = calculadora.calcular(10.0); // Calcula o frete usando apenas o peso
        System.out.printf("Frete (PAC) para peso 10.0: R$ %.2f%n", fretePac); // Imprime o resultado formatado

        // Define a estratégia como FreteGratis e calcula o frete para peso = 10 e valor = 250
        calculadora.setEstrategia(new FreteGratis()); // Troca a estratégia para Frete Grátis
        double freteGratisElegivel = calculadora.calcular(10.0, 250.0); // Calcula o frete considerando valor do pedido elegível (>200)
        System.out.printf("Frete (Grátis) para peso 10.0 e valor 250.0: R$ %.2f%n", freteGratisElegivel); // Imprime o resultado formatado

        // Demonstração opcional: quando o valor não é elegível para frete grátis
        double freteGratisNaoElegivel = calculadora.calcular(10.0, 150.0); // Calcula o frete com valor não elegível (<=200)
        System.out.printf("Frete (\"Grátis\" não elegível) para peso 10.0 e valor 150.0: R$ %.2f%n", freteGratisNaoElegivel); // Imprime o resultado formatado
    } // Fim do método main
} // Fim da classe SistemaDeFrete
