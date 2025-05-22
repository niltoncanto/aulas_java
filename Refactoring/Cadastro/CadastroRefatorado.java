// CadastroRefatorado.java

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Usuario {
    private String nome;
    private int idade;

    public Usuario(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    @Override
    public String toString() {
        return nome + ", " + idade + " anos";
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }
}

class Cadastro {
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void listarUsuarios() {
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    public void salvarUsuarioEmArquivo(Usuario usuario) {
        try (FileWriter f = new FileWriter("usuarios.txt", true)) {
            f.write(usuario.getNome() + "," + usuario.getIdade() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
