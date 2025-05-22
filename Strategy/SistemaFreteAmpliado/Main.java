import java.util.*;

// Classe Produto com nome, peso e preço
class Produto {
    private String nome;
    private double peso;
    private double preco;

    public Produto(String nome, double peso, double preco) {
        this.nome = nome;
        this.peso = peso;
        this.preco = preco;
    }

    public double getPeso() {
        return peso;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return nome + " - Peso: " + peso + "kg - Preço: R$" + preco;
    }
}

// Classe Pedido que armazena vários produtos
class Pedido {
    private List<Produto> itens = new ArrayList<>();

    public void adicionarProduto(Produto p) {
        itens.add(p);
    }

    public double getPesoTotal() {
        double total = 0.0;
        for (Produto p : itens) {
            total += p.getPeso();
        }
        return total;
    }

    public double getValorTotal() {
        double total = 0.0;
        for (Produto p : itens) {
            total += p.getPreco();
        }
        return total;
    }

    public void listarProdutos() {
        for (Produto p : itens) {
            System.out.println(p);
        }
    }
}

// Interface Strategy para cálculo de frete com base em um pedido
interface FreteStrategy {
    double calcularFrete(Pedido pedido);
}

// Estratégia SEDEX: frete = peso * 1.45
class SedexFrete implements FreteStrategy {
    public double calcularFrete(Pedido pedido) {
        return pedido.getPesoTotal() * 1.45;
    }
}

// Estratégia PAC: frete = peso * 1.10
class PacFrete implements FreteStrategy {
    public double calcularFrete(Pedido pedido) {
        return pedido.getPesoTotal() * 1.10;
    }
}

// Estratégia Frete Grátis: frete sempre 0
class FreteGratis implements FreteStrategy {
    public double calcularFrete(Pedido pedido) {
        return 0.0;
    }
}

// Estratégia com condição: frete grátis se valor > 100, senão peso * 1.25
class FreteCondicional implements FreteStrategy {
    public double calcularFrete(Pedido pedido) {
        if (pedido.getValorTotal() > 100.0) {
            return 0.0;
        } else {
            return pedido.getPesoTotal() * 1.25;
        }
    }
}

// Calculadora que usa a estratégia definida
class CalculadoraFrete {
    private FreteStrategy estrategia;

    public void setEstrategia(FreteStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public double calcular(Pedido pedido) {
        return estrategia.calcularFrete(pedido);
    }
}

// Classe principal com menu interativo
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pedido pedido = new Pedido();
        CalculadoraFrete calculadora = new CalculadoraFrete();

        // Bônus: Estratégias em mapa para acesso por nome
        Map<String, FreteStrategy> estrategias = new HashMap<>();
        estrategias.put("sedex", new SedexFrete());
        estrategias.put("pac", new PacFrete());
        estrategias.put("gratis", new FreteGratis());
        estrategias.put("condicional", new FreteCondicional());

        String opcao;
        do {
            System.out.println("\nMENU:");
            System.out.println("1. Adicionar produto");
            System.out.println("2. Escolher estratégia de frete");
            System.out.println("3. Calcular total do pedido + frete");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Peso (kg): ");
                    double peso = Double.parseDouble(scanner.nextLine());
                    System.out.print("Preço (R$): ");
                    double preco = Double.parseDouble(scanner.nextLine());
                    pedido.adicionarProduto(new Produto(nome, peso, preco));
                    break;

                case "2":
                    System.out.println("Escolha a estratégia: sedex | pac | gratis | condicional");
                    String escolha = scanner.nextLine();
                    if (estrategias.containsKey(escolha)) {
                        calculadora.setEstrategia(estrategias.get(escolha));
                        System.out.println("Estratégia '" + escolha + "' aplicada.");
                    } else {
                        System.out.println("Estratégia inválida.");
                    }
                    break;

                case "3":
                    System.out.println("Produtos no pedido:");
                    pedido.listarProdutos();
                    double valor = pedido.getValorTotal();
                    double frete = calculadora.calcular(pedido);
                    System.out.printf("Valor dos produtos: R$%.2f\n", valor);
                    System.out.printf("Frete: R$%.2f\n", frete);
                    System.out.printf("Total final: R$%.2f\n", valor + frete);
                    break;

                case "0":
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (!opcao.equals("0"));

        scanner.close();
    }
}

