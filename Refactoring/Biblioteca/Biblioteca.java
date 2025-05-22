// Biblioteca.java

import java.util.ArrayList;

public class B {
    ArrayList<String> l = new ArrayList<>();

    public void a(String nome) {
        l.add(nome);
    }

    public void b() {
        for (String i : l) {
            System.out.println(i);
            System.out.println("----------");
        }
    }

    public void c(String nome) {
        for (String i : l) {
            if (i.contains(nome)) {
                System.out.println("Encontrado: " + i);
            }
        }
    }

    public void d() {
        // método não usado
        System.out.println("Sistema de biblioteca");
    }
}
