import java.util.*;

/*
 * Main.java
 * Batalha Naval monolitica para refatoracao.
 *
 * Intencionalmente:
 * - Tudo em uma classe, estado global espalhado.
 * - Log, UI, regras, validacoes e IA misturados.
 * - Duplicacao de logica em varios pontos.
 * - Estruturas primitivas e arrays paralelos.
 *
 * Regras:
 * - Tabuleiro 10x10 (A-J, 1-10).
 * - Frota classica: 5, 4, 3, 3, 2.
 * - Sem diagonal.
 * - Tiro: agua, acerto, afundou.
 * - Vence quem afundar todos os navios do adversario.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== BATALHA NAVAL (MONOLITO) ===");
        System.out.print("Seed (vazio para aleatorio): ");
        String seedStr = sc.nextLine().trim();
        Random rng;
        if (seedStr.isEmpty()) rng = new Random();
        else {
            long seed;
            try { seed = Long.parseLong(seedStr); }
            catch (Exception e) { seed = seedStr.hashCode(); }
            rng = new Random(seed);
        }

        final int N = 10;
        final int[] shipLen = new int[] {5, 4, 3, 3, 2};
        final String[] shipName = new String[] {"Porta-avioes", "Encouracado", "Cruzador", "Submarino", "Destroyer"};

        // Tabuleiros
        // ownShips: mostra navios do jogador. '.' vazio, 'S' navio, 'X' acerto no proprio, 'o' agua no proprio (raro).
        // ownShots: mapa de tiros do jogador no inimigo. '.' desconhecido, 'X' acerto, 'o' agua.
        // cpuShips: navios da CPU. '.' vazio, 'S' navio, 'X' acerto.
        // cpuShots: tiros da CPU no jogador. '.' desconhecido, 'X' acerto, 'o' agua.
        char[][] ownShips = new char[N][N];
        char[][] ownShots = new char[N][N];
        char[][] cpuShips = new char[N][N];
        char[][] cpuShots = new char[N][N];

        fill(ownShips, '.');
        fill(ownShots, '.');
        fill(cpuShips, '.');
        fill(cpuShots, '.');

        // Mapas de navio por celula, -1 vazio, caso contrario id 0..4
        int[][] ownShipId = new int[N][N];
        int[][] cpuShipId = new int[N][N];
        fillInt(ownShipId, -1);
        fillInt(cpuShipId, -1);

        // HP de cada navio
        int[] ownHp = Arrays.copyOf(shipLen, shipLen.length);
        int[] cpuHp = Arrays.copyOf(shipLen, shipLen.length);

        // Log
        String[] log = new String[5000];
        int logSize = 0;

        // Posicionamento do jogador
        System.out.println();
        System.out.println("Posicionamento da sua frota.");
        System.out.println("Coordenadas: A-J e 1-10. Ex: A1, J10.");
        System.out.print("Deseja posicionar manualmente? (s/N): ");
        String manual = sc.nextLine().trim().toLowerCase(Locale.ROOT);

        if (manual.equals("s") || manual.equals("sim")) {
            for (int sid = 0; sid < shipLen.length; sid++) {
                boolean placed = false;
                while (!placed) {
                    System.out.println();
                    printSingleBoard("SEU TABULEIRO", ownShips, true);
                    System.out.println("Posicione: " + shipName[sid] + " (tamanho " + shipLen[sid] + ")");
                    System.out.print("Informe coordenada inicial (ex A1): ");
                    String c = sc.nextLine().trim();
                    int[] xy = parseCoord(c, N);
                    if (xy == null) {
                        System.out.println("Coordenada invalida.");
                        continue;
                    }
                    System.out.print("Direcao (H para horizontal, V para vertical): ");
                    String dir = sc.nextLine().trim().toUpperCase(Locale.ROOT);
                    boolean horiz = dir.equals("H");
                    boolean vert = dir.equals("V");
                    if (!horiz && !vert) {
                        System.out.println("Direcao invalida.");
                        continue;
                    }

                    int x = xy[0], y = xy[1];
                    if (!canPlace(ownShips, x, y, shipLen[sid], horiz)) {
                        System.out.println("Nao cabe ou colide.");
                        continue;
                    }

                    placeShip(ownShips, ownShipId, x, y, shipLen[sid], horiz, sid);
                    log[logSize++] = "Jogador posicionou " + shipName[sid] + " em " + prettyCoord(x, y);
                    placed = true;
                }
            }
        } else {
            // Auto posicionamento jogador
            for (int sid = 0; sid < shipLen.length; sid++) {
                boolean ok = false;
                int tries = 0;
                while (!ok && tries < 2000) {
                    tries++;
                    boolean horiz = rng.nextInt(2) == 0;
                    int x = rng.nextInt(N);
                    int y = rng.nextInt(N);
                    if (canPlace(ownShips, x, y, shipLen[sid], horiz)) {
                        placeShip(ownShips, ownShipId, x, y, shipLen[sid], horiz, sid);
                        log[logSize++] = "Jogador auto posicionou " + shipName[sid];
                        ok = true;
                    }
                }
                if (!ok) {
                    System.out.println("Falha ao posicionar automaticamente. Algo ficou errado.");
                    sc.close();
                    return;
                }
            }
            System.out.println("Frota posicionada automaticamente.");
        }

        // Posicionamento CPU
        for (int sid = 0; sid < shipLen.length; sid++) {
            boolean ok = false;
            int tries = 0;
            while (!ok && tries < 5000) {
                tries++;
                boolean horiz = rng.nextInt(2) == 0;
                int x = rng.nextInt(N);
                int y = rng.nextInt(N);
                if (canPlace(cpuShips, x, y, shipLen[sid], horiz)) {
                    placeShip(cpuShips, cpuShipId, x, y, shipLen[sid], horiz, sid);
                    ok = true;
                }
            }
            if (!ok) {
                System.out.println("Falha ao posicionar CPU.");
                sc.close();
                return;
            }
        }

        // Loop do jogo //
        boolean gameOver = false;
        boolean playerTurn = true;

        // IA simples com "alvo"
        // Quando acerta, guarda uma lista de candidatos ao redor para tentar depois.
        ArrayDeque<int[]> cpuTargets = new ArrayDeque<>();
        boolean[][] cpuTried = new boolean[N][N];

        while (!gameOver) {
            System.out.println();
            printTwoBoards(ownShips, ownShots);

            int ownAlive = sumPositive(ownHp);
            int cpuAlive = sumPositive(cpuHp);

            System.out.println("Navios restantes, voce: " + ownAlive + " | CPU: " + cpuAlive);

            if (cpuAlive == 0) {
                System.out.println("VITORIA. Voce afundou toda a frota inimiga.");
                log[logSize++] = "Fim: vitoria do jogador";
                break;
            }
            if (ownAlive == 0) {
                System.out.println("DERROTA. Sua frota foi afundada.");
                log[logSize++] = "Fim: vitoria da CPU";
                break;
            }

            if (playerTurn) {
                System.out.println();
                System.out.println("Seu turno.");
                System.out.println("Acoes: 1) Atirar 2) Ver log (ultimos 10) 3) Mostrar seu tabuleiro");
                System.out.print("> ");
                String opt = sc.nextLine().trim();

                if ("2".equals(opt)) {
                    printLogTail(log, logSize, 10);
                    continue;
                }
                if ("3".equals(opt)) {
                    printSingleBoard("SEU TABULEIRO", ownShips, true);
                    continue;
                }

                // Tiro do jogador
                String shot;
                while (true) {
                    System.out.print("Coordenada para atirar (ex B7): ");
                    shot = sc.nextLine().trim();
                    int[] xy = parseCoord(shot, N);
                    if (xy == null) {
                        System.out.println("Invalida.");
                        continue;
                    }
                    int x = xy[0], y = xy[1];
                    if (ownShots[y][x] != '.') {
                        System.out.println("Voce ja atirou ai.");
                        continue;
                    }
                    // Aplica tiro na CPU
                    boolean hit = cpuShips[y][x] == 'S';
                    if (hit) {
                        ownShots[y][x] = 'X';
                        cpuShips[y][x] = 'X';

                        int sid = cpuShipId[y][x];
                        if (sid >= 0) cpuHp[sid]--;

                        System.out.println("ACERTO.");
                        log[logSize++] = "Jogador acertou em " + prettyCoord(x, y);

                        if (sid >= 0 && cpuHp[sid] == 0) {
                            System.out.println("AFUNDOU um navio inimigo: " + shipName[sid]);
                            log[logSize++] = "Jogador afundou " + shipName[sid];
                        }

                        // Regra comum: acerto joga de novo? Aqui nao. Turno alterna sempre.
                    } else {
                        ownShots[y][x] = 'o';
                        System.out.println("AGUA.");
                        log[logSize++] = "Jogador errou em " + prettyCoord(x, y);
                    }

                    playerTurn = false;
                    break;
                }

            } else {
                // Turno CPU
                System.out.println();
                System.out.println("Turno da CPU.");

                // Escolha de alvo
                int tx = -1, ty = -1;

                // Se tem alvos pendentes, tenta primeiro
                while (!cpuTargets.isEmpty()) {
                    int[] t = cpuTargets.removeFirst();
                    int x = t[0], y = t[1];
                    if (x < 0 || x >= N || y < 0 || y >= N) continue;
                    if (cpuTried[y][x]) continue;
                    tx = x; ty = y;
                    break;
                }

                // Senao, aleatorio, com leve preferencia por padrao tipo x+y par
                if (tx == -1) {
                    int tries = 0;
                    while (tries < 5000) {
                        tries++;
                        int x = rng.nextInt(N);
                        int y = rng.nextInt(N);
                        if (cpuTried[y][x]) continue;
                        // Preferencia simples, mas nao garante
                        if ((x + y) % 2 == 0 || rng.nextInt(100) < 25) {
                            tx = x; ty = y;
                            break;
                        }
                    }
                    if (tx == -1) {
                        // fallback
                        outer:
                        for (int y = 0; y < N; y++) {
                            for (int x = 0; x < N; x++) {
                                if (!cpuTried[y][x]) { tx = x; ty = y; break outer; }
                            }
                        }
                    }
                }

                cpuTried[ty][tx] = true;

                // Aplica tiro no jogador
                boolean hit = ownShips[ty][tx] == 'S';
                if (hit) {
                    cpuShots[ty][tx] = 'X';
                    ownShips[ty][tx] = 'X';

                    int sid = ownShipId[ty][tx];
                    if (sid >= 0) ownHp[sid]--;

                    System.out.println("CPU acertou em " + prettyCoord(tx, ty));
                    log[logSize++] = "CPU acertou em " + prettyCoord(tx, ty);

                    // Enfileira vizinhos para tentar
                    cpuTargets.addLast(new int[]{tx + 1, ty});
                    cpuTargets.addLast(new int[]{tx - 1, ty});
                    cpuTargets.addLast(new int[]{tx, ty + 1});
                    cpuTargets.addLast(new int[]{tx, ty - 1});

                    if (sid >= 0 && ownHp[sid] == 0) {
                        System.out.println("CPU AFUNDOU seu navio: " + shipName[sid]);
                        log[logSize++] = "CPU afundou " + shipName[sid];
                        // Limpa alvos para nao ficar perseguindo ruinas
                        if (rng.nextInt(100) < 60) cpuTargets.clear();
                    }
                } else {
                    cpuShots[ty][tx] = 'o';
                    // Marca agua no seu tabuleiro? Aqui nao, para nao poluir, mas mantem cpuShots.
                    System.out.println("CPU errou em " + prettyCoord(tx, ty));
                    log[logSize++] = "CPU errou em " + prettyCoord(tx, ty);
                }

                playerTurn = true;
            }
        }

        System.out.println();
        System.out.print("Mostrar log completo? (s/N): ");
        String show = sc.nextLine().trim().toLowerCase(Locale.ROOT);
        if (show.equals("s") || show.equals("sim")) {
            for (int i = 0; i < logSize && i < log.length; i++) {
                System.out.println((i + 1) + ") " + log[i]);
            }
        }

        sc.close();
    }

    // ===== Helpers, ainda monoliticos =====

    static void fill(char[][] b, char c) {
        for (int y = 0; y < b.length; y++) {
            for (int x = 0; x < b[y].length; x++) b[y][x] = c;
        }
    }

    static void fillInt(int[][] b, int v) {
        for (int y = 0; y < b.length; y++) {
            for (int x = 0; x < b[y].length; x++) b[y][x] = v;
        }
    }

    // Retorna [x,y] ou null
    static int[] parseCoord(String s, int N) {
        if (s == null) return null;
        String t = s.trim().toUpperCase(Locale.ROOT);
        t = t.replace(" ", "");
        if (t.length() < 2 || t.length() > 3) return null;

        char col = t.charAt(0);
        if (col < 'A' || col > 'J') return null;
        int x = col - 'A';

        String numStr = t.substring(1);
        int row;
        try { row = Integer.parseInt(numStr); }
        catch (Exception e) { return null; }
        if (row < 1 || row > N) return null;
        int y = row - 1;

        return new int[]{x, y};
    }

    static String prettyCoord(int x, int y) {
        char c = (char)('A' + x);
        return "" + c + (y + 1);
    }

    static boolean canPlace(char[][] board, int x, int y, int len, boolean horiz) {
        if (horiz) {
            if (x + len > board.length) return false;
            for (int i = 0; i < len; i++) {
                if (board[y][x + i] != '.') return false;
            }
        } else {
            if (y + len > board.length) return false;
            for (int i = 0; i < len; i++) {
                if (board[y + i][x] != '.') return false;
            }
        }
        return true;
    }

    static void placeShip(char[][] board, int[][] shipId, int x, int y, int len, boolean horiz, int sid) {
        if (horiz) {
            for (int i = 0; i < len; i++) {
                board[y][x + i] = 'S';
                shipId[y][x + i] = sid;
            }
        } else {
            for (int i = 0; i < len; i++) {
                board[y + i][x] = 'S';
                shipId[y + i][x] = sid;
            }
        }
    }

    static int sumPositive(int[] hp) {
        int c = 0;
        for (int v : hp) if (v > 0) c++;
        return c;
    }

    static void printTwoBoards(char[][] ownShips, char[][] ownShots) {
        // Esquerda: seu tabuleiro, com navios
        // Direita: seus tiros no inimigo
        System.out.println(String.format("%-30s | %s", "SEU TABULEIRO", "TIROS NO INIMIGO"));
        System.out.println("    A B C D E F G H I J     |     A B C D E F G H I J");

        for (int y = 0; y < 10; y++) {
            String leftRow = String.format("%2d  ", (y + 1));
            String rightRow = String.format("%2d  ", (y + 1));

            for (int x = 0; x < 10; x++) {
                char c = ownShips[y][x];
                // Mostra navio como 'S', acerto 'X', vazio '.'
                leftRow += c + " ";
            }

            for (int x = 0; x < 10; x++) {
                char c = ownShots[y][x];
                rightRow += c + " ";
            }

            System.out.println(leftRow + "  |  " + rightRow);
        }

        System.out.println("Legenda: S navio, X acerto, o agua, . desconhecido ou vazio");
    }

    static void printSingleBoard(String title, char[][] b, boolean showShips) {
        System.out.println(title);
        System.out.println("    A B C D E F G H I J");
        for (int y = 0; y < 10; y++) {
            String row = String.format("%2d  ", (y + 1));
            for (int x = 0; x < 10; x++) {
                char c = b[y][x];
                if (!showShips && c == 'S') c = '.';
                row += c + " ";
            }
            System.out.println(row);
        }
    }

    static void printLogTail(String[] log, int logSize, int n) {
        System.out.println("Ultimos eventos:");
        int start = Math.max(0, logSize - n);
        for (int i = start; i < logSize && i < log.length; i++) {
            System.out.println((i + 1) + ") " + log[i]);
        }
    }
}
