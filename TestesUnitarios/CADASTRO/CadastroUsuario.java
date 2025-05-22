// CadastroUsuario.java

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CadastroUsuario {
    private String nome;
    private String cpf;
    private String nascimento;
    private String endereco;
    private String telefone;
    private String senha;

    public CadastroUsuario(String nome, String cpf, String nascimento, String endereco, String telefone, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.endereco = endereco;
        this.telefone = telefone;
        this.senha = senha;
    }

    public void validarNome() {
        if (nome == null || nome.trim().split("\\s+").length < 2) {
            throw new IllegalArgumentException("Nome inválido. Deve conter pelo menos nome e sobrenome.");
        }
    }

    public void validarCpf() {
        if (!Pattern.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", cpf)) {
            throw new IllegalArgumentException("CPF inválido. O formato correto é XXX.XXX.XXX-XX.");
        }
    }

    public void validarNascimento() {
        try {
            Date data = new SimpleDateFormat("dd/MM/yyyy").parse(nascimento);
            if (data.after(new Date())) {
                throw new IllegalArgumentException("Data de nascimento inválida. A data não pode ser no futuro.");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Data de nascimento inválida. O formato correto é DD/MM/AAAA.");
        }
    }

    public void validarEndereco() {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço inválido. Não pode ser vazio.");
        }
    }

    public void validarTelefone() {
        if (!Pattern.matches("\\(\\d{2}\\) \\d{4,5}-\\d{4}", telefone)) {
            throw new IllegalArgumentException("Telefone inválido. O formato correto é (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.");
        }
    }

    public void validarSenha() {
        if (senha.length() < 8 || !senha.matches(".*\\d.*") || !senha.matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("Senha inválida. Deve conter pelo menos 8 caracteres, incluindo letras e números.");
        }
    }

    public void validarGravacao() {
        validarNome();
        validarCpf();
        validarNascimento();
        validarEndereco();
        validarTelefone();
        validarSenha();
    }

    public void mostrarInfo() {
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Nascimento: " + nascimento);
        System.out.println("Endereço: " + endereco);
        System.out.println("Telefone: " + telefone);
    }

    public void gravarInfo() throws IOException {
        validarGravacao();
        String nomeArquivo = "cadastro_" + cpf.replace(".", "").replace("-", "") + ".txt";
        try (FileWriter fw = new FileWriter(nomeArquivo)) {
            fw.write("Nome: " + nome + "\n");
            fw.write("CPF: " + cpf + "\n");
            fw.write("Nascimento: " + nascimento + "\n");
            fw.write("Endereço: " + endereco + "\n");
            fw.write("Telefone: " + telefone + "\n");
        }
    }
}
