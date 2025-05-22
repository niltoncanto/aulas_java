import java.util.ArrayList;
import java.util.List;

// Interface comum para produtos e categorias
interface CatalogComponent {
    // Mostra os detalhes do componente (produto ou categoria)
    void showDetails();

    // Retorna o preço total do componente
    double getTotalPrice();
}

// Classe folha (Leaf) que representa um produto
class ProductLeaf implements CatalogComponent {
    private String name;   // Nome do produto
    private double price;  // Preço do produto

    // Construtor do produto
    public ProductLeaf(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Exibe os detalhes do produto
    @Override
    public void showDetails() {
        System.out.printf("Product: %s, Price: $%.2f%n", name, price);
    }

    // Retorna o preço do produto
    @Override
    public double getTotalPrice() {
        return price;
    }
}

// Classe composta (Composite) que representa uma categoria
class CategoryComposite implements CatalogComponent {
    private String name;  // Nome da categoria
    private List<CatalogComponent> components;  // Lista de produtos ou subcategorias

    // Construtor da categoria
    public CategoryComposite(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }

    // Adiciona um componente (produto ou categoria)
    public void add(CatalogComponent component) {
        components.add(component);
    }

    // Remove um componente
    public void remove(CatalogComponent component) {
        components.remove(component);
    }

    // Exibe os detalhes da categoria e de todos os seus componentes
    @Override
    public void showDetails() {
        System.out.println("Category: " + name);
        for (CatalogComponent component : components) {
            component.showDetails();  // Chamada recursiva
        }
    }

    // Soma os preços de todos os componentes recursivamente
    @Override
    public double getTotalPrice() {
        double total = 0.0;
        for (CatalogComponent component : components) {
            total += component.getTotalPrice();  // Chamada recursiva
        }
        return total;
    }
}

// Classe cliente que monta e testa a estrutura
public class CatalogClient {
    public static void main(String[] args) {
        // Criação de produtos (folhas)
        CatalogComponent product1 = new ProductLeaf("Laptop", 1500.00);
        CatalogComponent product2 = new ProductLeaf("Smartphone", 700.00);
        CatalogComponent product3 = new ProductLeaf("Tablet", 300.00);

        // Criação de categorias (composite)
        CategoryComposite electronics = new CategoryComposite("Electronics");
        CategoryComposite computers = new CategoryComposite("Computers");
        CategoryComposite phones = new CategoryComposite("Phones");

        // Montagem da estrutura
        computers.add(product1);         // Adiciona laptop
        phones.add(product2);            // Adiciona smartphone
        electronics.add(computers);      // Adiciona categoria Computers
        electronics.add(phones);         // Adiciona categoria Phones
        electronics.add(product3);       // Adiciona tablet direto em Electronics

        // Exibe os detalhes completos
        electronics.showDetails();

        // Exibe o preço total do catálogo principal
        System.out.printf("Total Price: $%.2f%n", electronics.getTotalPrice());
    }
}
