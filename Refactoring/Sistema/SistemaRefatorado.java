// SistemaRefatorado.java

class Usuario {
    private String nome;
    private String[] itens = new String[10];
    private int idx = 0;

    public Usuario(String nome) {
        this.nome = nome;
    }

    public boolean contemItem(String item) {
        for (String i : itens) {
            if (i != null && i.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void exibirNome() {
        System.out.println("Nome do usuário: " + nome);
    }

    public void adicionarItem(String item) {
        if (idx < itens.length) {
            itens[idx++] = item;
        }
    }
}

class CalculadoraTempo {
    public void calcularTempo() {
        int tempo = 0;
        for (int i = 0; i < 5; i++) {
            tempo += i * 2;
        }
        System.out.println("Tempo calculado: " + tempo);
    }
}
