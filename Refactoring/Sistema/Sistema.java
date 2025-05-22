// Sistema.java

public class S {
    private String n = "João";
    private int t = 0;
    private String[] x = new String[10];
    private int idx = 0;

    public boolean a(String y) {
        for (String item : x) {
            if (item != null && item.equals(y)) {
                return true;
            }
        }
        return false;
    }

    public void b(String y) {
        if (idx < x.length) {
            x[idx++] = y;
        }
    }

    public void c() {
        System.out.println("Nome do usuário: " + n);
        int tempo = 0;
        for (int i = 0; i < 5; i++) {
            tempo += i * 2;
        }
        System.out.println("Tempo calculado: " + tempo);
    }
}
