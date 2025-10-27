// CaixaRapido.java
// Exercício mais elaborado demonstrando interfaces, polimorfismo e múltiplas estratégias de pagamento.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

// Interface de Processador de Pagamento
interface ProcessadorPagamento {
    boolean pagar(double valor);

    default void emitirRecibo(double valor) {
        System.out.println("Recibo: pagamento de R$ " + String.format("%.2f", valor) + " confirmado.");
    }
}

// Implementação de Pagamento via Pix
class PagamentoPix implements ProcessadorPagamento {
    private String chavePix;

    public PagamentoPix(String chavePix) {
        this.chavePix = chavePix;
    }

    @Override
    public boolean pagar(double valor) {
        if (valor > 0 && chavePix != null && !chavePix.isEmpty()) {
            System.out.println("Processando PIX para chave: " + chavePix + " ...");
            System.out.println("Pagamento aprovado via PIX!");
            return true;
        }
        System.out.println("Pagamento via PIX recusado.");
        return false;
    }
}

// Implementação de Pagamento via Cartão de Crédito
class PagamentoCartaoCredito implements ProcessadorPagamento {
    private String numero;
    private String nomeTitular;
    private String cvv;
    private double limiteSimulado = 5000.0;

    public PagamentoCartaoCredito(String numero, String nomeTitular, String cvv) {
        this.numero = numero;
        this.nomeTitular = nomeTitular;
        this.cvv = cvv;
    }

    @Override
    public boolean pagar(double valor) {
        if (numero != null && nomeTitular != null && cvv != null
                && !numero.isEmpty() && !nomeTitular.isEmpty() && !cvv.isEmpty()
                && valor <= limiteSimulado) {
            System.out.println("Autorizando cartão de crédito para " + nomeTitular + " ...");
            System.out.println("Pagamento aprovado no cartão!");
            return true;
        }
        System.out.println("Pagamento no cartão recusado.");
        return false;
    }
}

// Implementação de Pagamento via Boleto
class PagamentoBoleto implements ProcessadorPagamento {
    @Override
    public boolean pagar(double valor) {
        if (valor > 0) {
            String linhaDigitavel = UUID.randomUUID().toString();
            System.out.println("Boleto gerado. Linha digitável: " + linhaDigitavel);
            System.out.println("Pague em qualquer banco até o vencimento.");
            return true;
        }
        System.out.println("Valor inválido para boleto.");
        return false;
    }
}

// Classe Item
class Item {
    private String descricao;
    private double preco;

    public Item(String descricao, double preco) {
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }
}

// Classe Carrinho
class Carrinho {
    private List<Item> itens = new ArrayList<>();

    public void adicionar(Item item) {
        itens.add(item);
    }

    public double total() {
        double soma = 0;
        for (Item i : itens) {
            soma += i.getPreco();
        }
        return soma;
    }

    public void exibirItens() {
        System.out.println("Carrinho:");
        for (Item i : itens) {
            System.out.println("- " + i.getDescricao() + ": R$ " + String.format("%.2f", i.getPreco()));
        }
        System.out.println("Total: R$ " + String.format("%.2f", total()));
    }

    public void finalizarCompra(ProcessadorPagamento proc) {
        double valor = total();
        if (proc.pagar(valor)) {
            proc.emitirRecibo(valor);
        } else {
            System.out.println("Pagamento recusado. Compra não finalizada.");
        }
    }
}

// Classe principal
public class CaixaRapido {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Criar carrinho
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Item("Café", 8.50));
        carrinho.adicionar(new Item("Pão de Queijo", 6.00));
        carrinho.adicionar(new Item("Suco", 9.90));

        carrinho.exibirItens();

        System.out.println("\nSelecione o meio de pagamento [1-Pix, 2-Cartão, 3-Boleto]: ");
        int opcao = sc.nextInt();
        sc.nextLine(); // consumir quebra de linha

        ProcessadorPagamento processador = null;

        switch (opcao) {
            case 1:
                System.out.print("Digite a chave Pix: ");
                String chavePix = sc.nextLine();
                processador = new PagamentoPix(chavePix);
                break;
            case 2:
                System.out.print("Número do cartão: ");
                String numero = sc.nextLine();
                System.out.print("Nome do titular: ");
                String nomeTitular = sc.nextLine();
                System.out.print("CVV: ");
                String cvv = sc.nextLine();
                processador = new PagamentoCartaoCredito(numero, nomeTitular, cvv);
                break;
            case 3:
                processador = new PagamentoBoleto();
                break;
            default:
                System.out.println("Opção inválida.");
                System.exit(0);
        }

        carrinho.finalizarCompra(processador);
        sc.close();
    }
}

