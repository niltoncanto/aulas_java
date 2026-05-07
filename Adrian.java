import java.util.*;

// ==== Configuração ====
class Config {
    public int totalRodadas(){ return 5; }
    public int maxDecisoesPorRodada(){ return 2; }
}

// ==== Engine e Score ====
class GameEngine {
    public void aplicarDecisao(Startup s, String tipo){
        DecisaoStrategy strat = DecisaoFactory.criar(tipo);
        if(strat == null) throw new IllegalArgumentException("Decisão desconhecida: " + tipo);
        Deltas deltas = strat.aplicar(s);
        double novoCaixa = s.getCaixa().valor() + deltas.caixaDelta();
        if(novoCaixa < 0) novoCaixa = 0;
        s.setCaixa(new Dinheiro(novoCaixa));
        if(deltas.reputacaoDelta() > 0) s.setReputacao(s.getReputacao().aumentar((int)deltas.reputacaoDelta()));
        else if(deltas.reputacaoDelta() < 0) s.setReputacao(s.getReputacao().reduzir((int)(-deltas.reputacaoDelta())));
        if(deltas.moralDelta() > 0) s.setMoral(s.getMoral().aumentar((int)deltas.moralDelta()));
        else if(deltas.moralDelta() < 0) s.setMoral(s.getMoral().reduzir((int)(-deltas.moralDelta())));
        if(deltas.bonusDelta() != 0.0) s.addBonusPercentReceitaProx(deltas.bonusDelta());
        s.getHistorico().add("Rodada " + s.getRodadaAtual() + " -> " + tipo + " aplicada: " + deltas.toString());
    }
}
class ScoreService {
    public double calcularScore(Startup s){
        return s.getCaixa().valor() + s.getReputacao().valor() * 50 + s.getMoral().valor() * 30;
    }
}

// ==== Decisões ====
interface DecisaoStrategy {
    Deltas aplicar(Startup s);
}
class DecisaoFactory {
    public static DecisaoStrategy criar(String tipo){
        if(tipo == null) return null;
        return switch (tipo.toUpperCase()) {
            case "MARKETING" -> new MarketingStrategy();
            case "EQUIPE" -> new EquipeStrategy();
            case "PRODUTO" -> new ProdutoStrategy();
            case "INVESTIDORES" -> new InvestidoresStrategy();
            case "CORTARCUSTOS" -> new CortarCustosStrategy();
            default -> null;
        };
    }
}
class MarketingStrategy implements DecisaoStrategy {
    public Deltas aplicar(Startup s){ return new Deltas(-500.0, 5, 2, 0.05); }
}
class EquipeStrategy implements DecisaoStrategy {
    public Deltas aplicar(Startup s){ return new Deltas(-700.0, 2, 8, 0.0); }
}
class ProdutoStrategy implements DecisaoStrategy {
    public Deltas aplicar(Startup s){ return new Deltas(-1200.0, 10, 3, 0.10); }
}
class InvestidoresStrategy implements DecisaoStrategy {
    public Deltas aplicar(Startup s){ return new Deltas(2000.0, -3, 0, 0.0); }
}
class CortarCustosStrategy implements DecisaoStrategy {
    public Deltas aplicar(Startup s){ return new Deltas(300.0, -5, -10, 0.0); }
}

// ==== Modelos VO e Startup ====
record Deltas(double caixaDelta, int reputacaoDelta, int moralDelta, double bonusDelta) { }
record Dinheiro(double valor) {
    public Dinheiro { if (valor < 0) throw new IllegalArgumentException("Dinheiro não pode ser negativo"); }
    public Dinheiro somar(Dinheiro outro){ return new Dinheiro(this.valor + outro.valor); }
    public Dinheiro subtrair(Dinheiro outro){
        double novo = this.valor - outro.valor();
        if (novo < 0) throw new IllegalArgumentException("Saldo insuficiente");
        return new Dinheiro(novo);
    }
    public String toString(){ return String.format("R$%.2f", valor); }
}
record Humor(int valor) {
    public Humor { if (valor < 0 || valor > 100) throw new IllegalArgumentException("Humor deve estar entre 0 e 100"); }
    public Humor aumentar(int delta){ return new Humor(Math.min(100, valor + delta)); }
    public Humor reduzir(int delta){ return new Humor(Math.max(0, valor - delta)); }
}
record Percentual(double valor) {
    public Percentual { if (valor < -1.0 || valor > 1.0) throw new IllegalArgumentException("Percentual deve estar entre -100% e +100%"); }
    public double aplicarSobre(double base){ return base * (1.0 + valor); }
}
class Startup {
    private String nome;
    private Dinheiro caixa;
    private Dinheiro receitaBase;
    private Humor reputacao;
    private Humor moral;
    private int rodadaAtual = 1;
    private Percentual bonusPercentReceitaProx = new Percentual(0.0);
    private final List<String> historico = new ArrayList<>();
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Dinheiro getCaixa() { return caixa; }
    public void setCaixa(Dinheiro caixa) { this.caixa = caixa; }
    public Dinheiro getReceitaBase() { return receitaBase; }
    public void setReceitaBase(Dinheiro receitaBase) { this.receitaBase = receitaBase; }
    public Humor getReputacao() { return reputacao; }
    public void setReputacao(Humor reputacao) { this.reputacao = reputacao; }
    public Humor getMoral() { return moral; }
    public void setMoral(Humor moral) { this.moral = moral; }
    public int getRodadaAtual() { return rodadaAtual; }
    public void setRodadaAtual(int rodadaAtual) { this.rodadaAtual = rodadaAtual; }
    public Percentual getBonusPercentReceitaProx() { return bonusPercentReceitaProx; }
    public void addBonusPercentReceitaProx(double delta) { 
        this.bonusPercentReceitaProx = new Percentual(this.bonusPercentReceitaProx.valor() + delta); 
    }
    public void setBonusPercentReceitaProx(Percentual p){ this.bonusPercentReceitaProx = p; }
    public List<String> getHistorico() { return historico; }
    public String toString() {
        return String.format(Locale.US, "%s | Caixa: R$%.2f | ReceitaBase: R$%.2f | Rep: %d | Moral: %d",
            nome, caixa != null ? caixa.valor() : 0.0, receitaBase != null ? receitaBase.valor() : 0.0,
            reputacao != null ? reputacao.valor() : 0, moral != null ? moral.valor() : 0
        );
    }
}

// ==== Console App ====
public class Main {
    public static void main(String[] args) {
        new ConsoleApp().start();
    }
}
class ConsoleApp {
    private final Config config = new Config();
    public void start(){
        System.out.println("Startup Game - Console (versão simplificada)");
        System.out.println("total.rodadas=" + config.totalRodadas());
        System.out.println("max.decisoes.por.rodada=" + config.maxDecisoesPorRodada());

        Scanner sc = new Scanner(System.in);
        System.out.print("Nome da sua startup: ");
        String nome = sc.nextLine().trim();
        if(nome.isEmpty()) nome = "MinhaStartup";

        Startup s = new Startup();
        s.setNome(nome);
        s.setCaixa(new Dinheiro(3000.0));
        s.setReceitaBase(new Dinheiro(1000.0));
        s.setReputacao(new Humor(50));
        s.setMoral(new Humor(50));
        s.setBonusPercentReceitaProx(new Percentual(0.0));

        GameEngine engine = new GameEngine();
        ScoreService scoreService = new ScoreService();

        for(int rodada = 1; rodada <= config.totalRodadas(); rodada++){
            s.setRodadaAtual(rodada);
            System.out.println("\n=== Rodada " + rodada + " ===");
            System.out.println(s);
            System.out.println("Decisões disponíveis: MARKETING, EQUIPE, PRODUTO, INVESTIDORES, CORTARCUSTOS, NONE");
            int decisions = 0;
            while(decisions < config.maxDecisoesPorRodada()){
                System.out.print("Escolha decisão (ou NONE para terminar rodada): ");
                String escolha = sc.nextLine().trim();
                if(escolha.equalsIgnoreCase("NONE") || escolha.isEmpty()) break;
                try {
                    engine.aplicarDecisao(s, escolha);
                    System.out.println("Decisão aplicada.");
                } catch(Exception e){
                    System.out.println("Erro: " + e.getMessage());
                }
                decisions++;
            }
            double receita = s.getReceitaBase().valor() * (1.0 + s.getBonusPercentReceitaProx().valor());
            s.setCaixa(new Dinheiro(s.getCaixa().valor() + receita));
            s.setBonusPercentReceitaProx(new Percentual(0.0));
            System.out.println("Receita aplicada: R$" + String.format("%.2f", receita));
        }

        System.out.println("\n--- Fim de jogo ---");
        System.out.println(s);
        System.out.println("Score final: " + scoreService.calcularScore(s));
        System.out.println("Historico de ações:");
        s.getHistorico().forEach(System.out::println);

        sc.close();
    }
}
