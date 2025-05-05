import java.util.ArrayList;

class Filme {
    private String nome;
    private int duracao;
    private int classificacaoEtaria;

    public Filme(String nome, int duracao, int classificacaoEtaria) {
        this.nome = nome;
        this.duracao = duracao;
        this.classificacaoEtaria = classificacaoEtaria;
    }

    public String getNome() {
        return nome;
    }
}

class Sala {
    private int capacidade;
    private Filme filmeEmExibicao;

    public Sala(int capacidade) {
        this.capacidade = capacidade;
    }

    public void exibirFilme(Filme filme) {
        this.filmeEmExibicao = filme;
        System.out.println("Agora exibindo: " + filme.getNome());
    }

    public int getCapacidade() {
        return capacidade;
    }
}

public class MainCinema {
    public static void main(String[] args) {
        Filme titanic = new Filme("Titanic", 195, 12);
        Sala sala1 = new Sala(100);

        sala1.exibirFilme(titanic);
    }
}

