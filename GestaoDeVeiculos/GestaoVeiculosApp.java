// ============================================================
// GestaoVeiculosApp.java
// Exemplo didático em Java que demonstra os 4 pilares da POO:
// - Encapsulamento: atributos privados + getters/setters validados
// - Herança: Carro, Moto e Caminhao estendem Veiculo
// - Composição: Veiculo "tem" Departamento; Concessionaria "tem" uma lista de Veiculo
// - Polimorfismo: calcularIpva() implementado de forma diferente em cada subclasse
// ============================================================

import java.util.ArrayList;   // Importa a implementação de lista dinâmica
import java.util.List;        // Interface de lista para usar como tipo abstrato
import java.util.Iterator;    // Usado para remoção segura ao iterar

// ---------------------------
// Classe simples usada na COMPOSIÇÃO
// Um Veiculo "tem um" Departamento
// ---------------------------
class Departamento {
    // Atributo privado (ENCAPSULAMENTO)
    private String nome;

    // Construtor que garante que o nome não seja nulo/vazio
    public Departamento(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do departamento inválido.");
        }
        this.nome = nome; // Atribui valor validado
    }

    // Getter público controlado (ENCAPSULAMENTO)
    public String getNome() {
        return nome; // Retorna o nome (imutável no exemplo)
    }

    // toString facilita depuração e impressão
    @Override
    public String toString() {
        return "Departamento{" + "nome='" + nome + '\'' + '}';
    }
}

// -------------------------------------------------------
// Classe base ABSTRATA (ABSTRAÇÃO) para a hierarquia
// -------------------------------------------------------
abstract class Veiculo {
    // Atributos privados (ENCAPSULAMENTO)
    private String marca;        // Ex.: "Toyota"
    private String modelo;       // Ex.: "Corolla"
    private int ano;             // Ex.: 2022
    private double preco;        // Ex.: 120000.00
    private boolean ligado;      // Estado do veículo (ligado/desligado)
    private double velocidade;   // Velocidade atual em km/h

    // COMPOSIÇÃO: todo Veiculo pertence a um Departamento
    private Departamento departamento;

    // Construtor com validações simples (garante estados válidos)
    public Veiculo(String marca, String modelo, int ano, double preco, Departamento departamento) {
        // Valida marca
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("Marca inválida.");
        }
        // Valida modelo
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("Modelo inválido.");
        }
        // Valida ano (ex.: rejeita anos não positivos)
        if (ano <= 0) {
            throw new IllegalArgumentException("Ano inválido.");
        }
        // Valida preço (não pode ser negativo)
        if (preco < 0.0) {
            throw new IllegalArgumentException("Preço inválido.");
        }
        // Valida departamento (não pode ser nulo)
        if (departamento == null) {
            throw new IllegalArgumentException("Departamento não pode ser nulo.");
        }

        // Atribuições após validação
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        this.departamento = departamento;

        // Estado inicial padronizado
        this.ligado = false;     // Começa desligado
        this.velocidade = 0.0;   // Velocidade inicial zero
    }

    // -----------------------------
    // Getters (exposição controlada)
    // -----------------------------
    public String getMarca() { return marca; }          // Retorna a marca
    public String getModelo() { return modelo; }        // Retorna o modelo
    public int getAno() { return ano; }                 // Retorna o ano
    public double getPreco() { return preco; }          // Retorna o preço
    public boolean isLigado() { return ligado; }        // Retorna estado ligado
    public double getVelocidade() { return velocidade; }// Retorna velocidade atual
    public Departamento getDepartamento() { return departamento; } // Retorna o departamento

    // -----------------------------
    // Setters (com validação)
    // -----------------------------
    public void setPreco(double novoPreco) {
        // Impede preço negativo
        if (novoPreco < 0.0) {
            throw new IllegalArgumentException("Preço inválido.");
        }
        this.preco = novoPreco; // Atualiza o preço
    }

    public void setAno(int novoAno) {
        // Impede ano não positivo
        if (novoAno <= 0) {
            throw new IllegalArgumentException("Ano inválido.");
        }
        this.ano = novoAno; // Atualiza o ano
    }

    // -----------------------------
    // Comportamentos comuns
    // -----------------------------
    public void ligar() {
        // Liga o veículo apenas se estiver desligado
        if (!ligado) {
            ligado = true;       // Atualiza estado para ligado
            // (Opcional) imprimir feedback
            // System.out.println("Veículo ligado.");
        }
    }

    public void desligar() {
        // Desliga o veículo e zera velocidade por segurança
        ligado = false;          // Atualiza estado para desligado
        velocidade = 0.0;        // Zera velocidade quando desliga
    }

    public void acelerar(double delta) {
        // Acelera somente se estiver ligado e delta for positivo
        if (!ligado) {
            System.out.println("Não é possível acelerar: veículo desligado.");
            return; // Sai sem alterar estado
        }
        if (delta <= 0) {
            System.out.println("Delta de aceleração deve ser positivo.");
            return; // Sai sem alterar velocidade
        }
        velocidade += delta; // Incrementa velocidade
    }

    public void frear(double delta) {
        // Freia com delta positivo; não deixa velocidade ficar negativa
        if (delta <= 0) {
            System.out.println("Delta de frenagem deve ser positivo.");
            return; // Sai sem alterar velocidade
        }
        velocidade -= delta;              // Subtrai delta
        if (velocidade < 0) velocidade = 0; // Garante piso zero
    }

    // Método que retorna texto com as informações básicas
    public String info() {
        // Monta uma string com os dados comuns do veículo
        return String.format(
            "Departamento: %s | %s %s (%d) | Preço: R$ %.2f | Ligado: %s | Velocidade: %.1f km/h",
            departamento.getNome(), marca, modelo, ano, preco, ligado ? "sim" : "não", velocidade
        );
    }

    // POLIMORFISMO: método abstrato; cada subclasse terá sua regra de IPVA
    public abstract double calcularIpva();
}

// ---------------------------------------
// Subclasse 1 — Carro (HERANÇA + POLIMORFISMO)
// ---------------------------------------
class Carro extends Veiculo {
    // Atributos específicos do Carro
    private int portas;          // Quantidade de portas (ex.: 4)
    private boolean temAirbag;   // Se possui airbag (true/false)

    // Construtor que invoca o super e valida os próprios campos
    public Carro(String marca, String modelo, int ano, double preco, Departamento departamento,
                 int portas, boolean temAirbag) {
        super(marca, modelo, ano, preco, departamento); // Chama construtor da base
        if (portas <= 0) {
            throw new IllegalArgumentException("Quantidade de portas inválida.");
        }
        this.portas = portas;       // Atribui portas após validação
        this.temAirbag = temAirbag; // Atribui se tem airbag
    }

    // Sobrescreve info() para acrescentar dados específicos do Carro
    @Override
    public String info() {
        // Reusa info() da base e anexa os atributos do Carro
        return super.info() + String.format(" | Portas: %d | Airbag: %s", portas, temAirbag ? "sim" : "não");
    }

    // Implementa a regra de IPVA específica do Carro
    @Override
    public double calcularIpva() {
        // Regra: 3% se ano >= 2010; senão 1%
        double aliquota = (getAno() >= 2010) ? 0.03 : 0.01; // Define alíquota
        return getPreco() * aliquota;                      // Calcula o IPVA
    }
}

// ---------------------------------------
// Subclasse 2 — Moto (HERANÇA + POLIMORFISMO)
// ---------------------------------------
class Moto extends Veiculo {
    // Atributo específico da Moto
    private int cilindradas; // Ex.: 160, 300, 600

    // Construtor que invoca a base e valida cilindradas
    public Moto(String marca, String modelo, int ano, double preco, Departamento departamento,
                int cilindradas) {
        super(marca, modelo, ano, preco, departamento); // Chama construtor da base
        if (cilindradas <= 0) {
            throw new IllegalArgumentException("Cilindradas inválidas.");
        }
        this.cilindradas = cilindradas; // Atribui após validação
    }

    // Sobrescreve info() para acrescentar as cilindradas
    @Override
    public String info() {
        return super.info() + String.format(" | Cilindradas: %dcc", cilindradas); // Anexa dado específico
    }

    // Implementa a regra de IPVA específica da Moto
    @Override
    public double calcularIpva() {
        // Regra: 2% se cilindradas >= 300; senão isento (0%)
        double aliquota = (cilindradas >= 300) ? 0.02 : 0.0; // Define alíquota conforme cilindrada
        return getPreco() * aliquota;                        // Calcula IPVA
    }
}

// ---------------------------------------
// Subclasse 3 — Caminhao (HERANÇA + POLIMORFISMO)
// ---------------------------------------
class Caminhao extends Veiculo {
    // Atributos específicos do Caminhão
    private double capacidadeTon; // Capacidade em toneladas (ex.: 10.5)
    private int eixos;            // Número de eixos (ex.: 2, 3, 4)

    // Construtor com validações
    public Caminhao(String marca, String modelo, int ano, double preco, Departamento departamento,
                    double capacidadeTon, int eixos) {
        super(marca, modelo, ano, preco, departamento); // Chama base
        if (capacidadeTon <= 0.0) {
            throw new IllegalArgumentException("Capacidade (ton) inválida.");
        }
        if (eixos <= 0) {
            throw new IllegalArgumentException("Quantidade de eixos inválida.");
        }
        this.capacidadeTon = capacidadeTon; // Atribui capacidade
        this.eixos = eixos;                 // Atribui eixos
    }

    // Sobrescreve info() acrescentando capacidade e eixos
    @Override
    public String info() {
        return super.info() + String.format(" | Capacidade: %.1f ton | Eixos: %d", capacidadeTon, eixos);
    }

    // Implementa regra de IPVA específica do Caminhão
    @Override
    public double calcularIpva() {
        // Regra: 1% do preço por eixo, limitado a 4% (teto)
        double aliquotaPorEixo = 0.01 * eixos; // 1% * número de eixos
        double aliquotaLimitada = Math.min(aliquotaPorEixo, 0.04); // Teto de 4%
        return getPreco() * aliquotaLimitada; // Calcula IPVA
    }
}

// ----------------------------------------------------
// Concessionaria usa COMPOSIÇÃO (tem uma lista de Veiculo)
// ----------------------------------------------------
class Concessionaria {
    // Estoque é uma lista de Veiculo (polimórfica)
    private List<Veiculo> estoque = new ArrayList<>();

    // Adiciona veículo ao estoque (null não é permitido)
    public void adicionar(Veiculo v) {
        if (v == null) {
            throw new IllegalArgumentException("Veículo nulo não pode ser adicionado.");
        }
        estoque.add(v); // Insere no final da lista
    }

    // Remove o primeiro veículo cujo modelo coincida (retorna true se removeu)
    public boolean removerPorModelo(String modelo) {
        // Usa Iterator para remoção segura durante iteração
        Iterator<Veiculo> it = estoque.iterator();
        while (it.hasNext()) {
            Veiculo v = it.next();                  // Obtém próximo veículo
            // Compara ignorando maiúsculas/minúsculas
            if (v.getModelo().equalsIgnoreCase(modelo)) {
                it.remove();                        // Remove com segurança
                return true;                        // Indica que removeu
            }
        }
        return false; // Não encontrou modelo correspondente
    }

    // Busca todos os veículos de uma marca específica
    public List<Veiculo> buscarPorMarca(String marca) {
        List<Veiculo> resultado = new ArrayList<>(); // Lista de resultado
        for (Veiculo v : estoque) {                  // Itera sobre estoque
            if (v.getMarca().equalsIgnoreCase(marca)) {
                resultado.add(v);                    // Adiciona os que casam
            }
        }
        return resultado; // Retorna lista (pode estar vazia)
    }

    // Imprime info() de todos os veículos do estoque
    public void listar() {
        if (estoque.isEmpty()) {
            System.out.println("Estoque vazio."); // Mensagem amigável
            return;                               // Sai do método
        }
        System.out.println("Concessionária: " + estoque.size() + " veículo(s)");
        for (Veiculo v : estoque) {               // Itera por todos
            System.out.println("- " + v.info());  // Imprime info polimórfico
        }
    }

    // Soma o preço de todos os veículos
    public double valorTotalEstoque() {
        double total = 0.0;                  // Acumulador
        for (Veiculo v : estoque) {          // Percorre a lista
            total += v.getPreco();           // Soma preço de cada
        }
        return total;                        // Retorna total
    }
}

// ---------------------------------------
// Classe executável com o método main
// ---------------------------------------
public class GestaoVeiculosApp {
    public static void main(String[] args) {
        // Cria alguns departamentos (COMPOSIÇÃO)
        Departamento depNovos = new Departamento("Veículos Novos");
        Departamento depUsados = new Departamento("Seminovos / Usados");

        // Cria um Carro, uma Moto e um Caminhão (HERANÇA)
        Carro carro = new Carro(
            "Toyota",        // marca
            "Corolla",       // modelo
            2022,            // ano
            120_000.00,      // preço
            depNovos,        // departamento
            4,               // portas
            true             // temAirbag
        );

        Moto moto = new Moto(
            "Honda",         // marca
            "CB 500F",       // modelo
            2021,            // ano
            36_000.00,       // preço
            depUsados,       // departamento
            471              // cilindradas
        );

        Caminhao caminhao = new Caminhao(
            "Volvo",         // marca
            "FH 540",        // modelo
            2019,            // ano
            780_000.00,      // preço
            depUsados,       // departamento
            25.0,            // capacidade em toneladas
            4                // eixos
        );

        // Cria a Concessionaria (COMPOSIÇÃO com lista de Veiculo)
        Concessionaria loja = new Concessionaria();

        // Adiciona os veículos ao estoque
        loja.adicionar(carro);
        loja.adicionar(moto);
        loja.adicionar(caminhao);

        // Lista todos os veículos (usa info() polimórfico)
        loja.listar();

        // Mostra o valor total do estoque
        System.out.printf("Valor total do estoque: R$ %.2f%n", loja.valorTotalEstoque());

        // Demonstra POLIMORFISMO: a mesma chamada invoca a regra de cada subclasse
        System.out.printf("IPVA Carro: R$ %.2f%n", carro.calcularIpva());
        System.out.printf("IPVA Moto: R$ %.2f%n", moto.calcularIpva());
        System.out.printf("IPVA Caminhão: R$ %.2f%n", caminhao.calcularIpva());

        // Demonstra comportamentos comuns (ligar/acelerar/frear/desligar)
        System.out.println("\nDemonstração de ligar/acelerar/frear/desligar no Carro:");
        carro.ligar();           // Liga o carro
        carro.acelerar(50);      // Acelera +50 km/h
        carro.frear(20);         // Freia -20 km/h
        System.out.println(carro.info()); // Mostra estado atual (ligado/velocidade)
        carro.desligar();        // Desliga o carro
        System.out.println(carro.info()); // Mostra estado final (desligado, velocidade 0)

        // Exemplo de busca por marca
        System.out.println("\nBusca por marca 'Honda':");
        List<Veiculo> hondas = loja.buscarPorMarca("Honda");
        for (Veiculo v : hondas) {
            System.out.println("- " + v.info());
        }

        // Exemplo de remoção por modelo
        System.out.println("\nRemovendo modelo 'Corolla' do estoque...");
        boolean removido = loja.removerPorModelo("Corolla");
        System.out.println("Removido? " + (removido ? "Sim" : "Não"));

        // Lista novamente após remoção
        loja.listar();
    }
}
