// Cadastro.java

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class A {
    private ArrayList<String[]> d = new ArrayList<>();

    public void b(String[] u) {
        d.add(u);
    }

    public void c() {
        for (String[] u : d) {
            System.out.println(u[0]);
            System.out.println(u[1]);
            System.out.println("Usuário salvo!");
        }
    }

    public void s(String nome, int idade) {
        String[] u = { nome, Integer.toString(idade) };
        b(u);
        try (FileWriter f = new FileWriter("usuarios.txt", true)) {
            f.write(nome + "," + idade + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
