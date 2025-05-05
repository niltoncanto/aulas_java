public class Album {
    private String nome;
    private int anoLancamento;
    private int numeroFaixas;

    public Album(String nome, int anoLancamento, int numeroFaixas) {
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.numeroFaixas = numeroFaixas;
    }

    public String getNome() {
        return nome;
    }

    public String informacoes() {
        return "Álbum " + nome + " lançado em " + anoLancamento + " com " + numeroFaixas + " faixas.";
    }
}
