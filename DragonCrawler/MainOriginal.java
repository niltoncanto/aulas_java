import java.util.*;

/*
 * MainOriginal.java
 * Dungeon Crawler monolítico para refatoração.
 *
 * Características intencionais do monólito:
 * - Estado do jogo espalhado em variáveis soltas.
 * - Itens e inimigos representados por Strings e arrays paralelos.
 * - Regras repetidas e números mágicos.
 * - UI, regra de negócio e "persistência" (log em memória) misturados.
 *
 * Objetivo pedagógico:
 * - Refatorar para um design orientado a objetos, com separação de responsabilidades.
 * - Introduzir padrões como Strategy, State, Factory, Observer, etc.
 */
public class MainOriginal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Config geral
        System.out.println("=== DUNGEON CRAWLER (MONOLITO) ===");
        System.out.print("Nome do heroi: ");
        String playerName = sc.nextLine().trim();
        if (playerName.isEmpty()) playerName = "Heroi";

        System.out.print("Seed (vazio para aleatorio): ");
        String seedStr = sc.nextLine().trim();
        Random rng;
        if (seedStr.isEmpty()) {
            rng = new Random();
        } else {
            long seed;
            try { seed = Long.parseLong(seedStr); }
            catch (Exception e) { seed = seedStr.hashCode(); }
            rng = new Random(seed);
        }

        // Estado do jogador
        int maxHp = 30;
        int hp = maxHp;
        int maxMana = 10;
        int mana = maxMana;
        int level = 1;
        int xp = 0;
        int xpToNext = 12;
        int gold = 12;
        int hunger = 0; // 0 a 100, quanto maior pior
        int roomIndex = 0;
        boolean gameOver = false;
        boolean victory = false;

        // Equipamentos
        // Representacao string: "TYPE|NAME|ATK|DEF|PRICE|RARITY"
        String equippedWeapon = "WEAPON|Faca Ferrugenta|2|0|0|COMMON";
        String equippedArmor  = "ARMOR|Tecido Rasgado|0|1|0|COMMON";

        // Inventario (strings) com capacidade fixa
        int invCap = 12;
        String[] inv = new String[invCap];
        int invSize = 0;

        // Itens iniciais
        inv[invSize++] = "POTION|Pocao Pequena|+10HP|6|COMMON";
        inv[invSize++] = "FOOD|Pao Seco|H-15|4|COMMON";

        // Log de eventos (pseudo observador)
        String[] log = new String[5000];
        int logSize = 0;

        // Mapa simples: lista de salas
        int totalRooms = 18;
        String[] rooms = new String[totalRooms];
        for (int i = 0; i < totalRooms; i++) {
            rooms[i] = generateRoom(rng, i, totalRooms);
        }
        // Garante boss perto do final
        rooms[totalRooms - 2] = "BOSS";
        rooms[totalRooms - 1] = "EXIT";

        // Flags de estado
        boolean inCombat = false;
        String currentEnemy = null; // "NAME|HP|ATK|DEF|XP|GOLD|TYPE"
        int enemyHp = 0;
        int enemyAtk = 0;
        int enemyDef = 0;
        int enemyXp = 0;
        int enemyGold = 0;
        String enemyType = "";

        // Loop principal
        while (!gameOver) {

            // Verificacoes de derrota
            if (hp <= 0) {
                gameOver = true;
                victory = false;
                log[logSize++] = "Derrota: HP chegou a 0.";
                break;
            }
            if (hunger >= 100) {
                // Fome extrema causa dano
                int dmg = 2 + (hunger - 100) / 10;
                hp -= dmg;
                log[logSize++] = "Fome extrema causa dano: -" + dmg + " HP.";
                System.out.println("Voce esta morrendo de fome, -" + dmg + " HP.");
                hunger = 100;
                if (hp <= 0) continue;
            }

            // Entrada de sala se nao estiver em combate
            if (!inCombat) {
                String roomType = rooms[roomIndex];
                System.out.println();
                System.out.println("Sala " + (roomIndex + 1) + "/" + totalRooms + " - Tipo: " + roomType);
                log[logSize++] = "Entrou na sala " + (roomIndex + 1) + " tipo " + roomType + ".";

                // Fome aumenta por sala
                hunger += 6;
                if (hunger > 120) hunger = 120;

                // Evento por tipo
                if ("EMPTY".equals(roomType)) {
                    System.out.println("Uma sala vazia. Um silencio ruim.");
                } else if ("TRAP".equals(roomType)) {
                    int trap = 2 + rng.nextInt(6);
                    if (rng.nextInt(100) < 20) trap += 4;
                    hp -= trap;
                    System.out.println("Armadilha. Voce toma -" + trap + " HP.");
                    log[logSize++] = "Armadilha: -" + trap + " HP.";
                } else if ("CHEST".equals(roomType)) {
                    System.out.println("Um bau enferrujado.");
                    if (rng.nextInt(100) < 60) {
                        String loot = generateLoot(rng, level);
                        System.out.println("Voce encontrou: " + prettyItem(loot));
                        log[logSize++] = "Bau gerou loot: " + loot;
                        invSize = addToInventory(inv, invSize, invCap, loot, log, logSize);
                        // addToInventory atualiza logSize pelo retorno? Nao, aqui esta monolito, entao replicamos
                        // Simulando bug comum, logSize nao muda aqui.
                    } else {
                        int g = 3 + rng.nextInt(10);
                        gold += g;
                        System.out.println("Voce encontrou " + g + " moedas.");
                        log[logSize++] = "Bau: +" + g + " gold.";
                    }
                } else if ("FOUNTAIN".equals(roomType)) {
                    System.out.println("Uma fonte antiga com agua brilhante.");
                    if (rng.nextInt(100) < 55) {
                        int heal = 8 + rng.nextInt(10);
                        hp += heal;
                        if (hp > maxHp) hp = maxHp;
                        System.out.println("Voce bebe e recupera +" + heal + " HP.");
                        log[logSize++] = "Fonte curou +" + heal + " HP.";
                    } else {
                        int curse = 2 + rng.nextInt(4);
                        maxHp -= curse;
                        if (maxHp < 10) maxHp = 10;
                        if (hp > maxHp) hp = maxHp;
                        System.out.println("O gosto e ruim. Seu vigor cai. MaxHP -" + curse + ".");
                        log[logSize++] = "Fonte amaldiçoou: MaxHP -" + curse + ".";
                    }
                } else if ("SHOP".equals(roomType)) {
                    System.out.println("Um mercador aparece do nada.");
                    log[logSize++] = "Encontrou loja.";
                    handleShop(sc, rng, level, inv, invCap, gold, log, logSize);
                    // Monolito de proposito: handleShop nao altera gold nem inv por referencia.
                    // Os alunos vao descobrir esse tipo de problema.
                    System.out.println("O mercador some.");
                } else if ("COMBAT".equals(roomType)) {
                    // Inicia combate
                    currentEnemy = generateEnemy(rng, level);
                    String[] parts = currentEnemy.split("\\|");
                    enemyType = parts[0];
                    String enemyName = parts[1];
                    enemyHp = Integer.parseInt(parts[2]);
                    enemyAtk = Integer.parseInt(parts[3]);
                    enemyDef = Integer.parseInt(parts[4]);
                    enemyXp = Integer.parseInt(parts[5]);
                    enemyGold = Integer.parseInt(parts[6]);

                    System.out.println("Um inimigo aparece: " + enemyName + " (" + enemyType + ")");
                    log[logSize++] = "Combate iniciou com " + currentEnemy;

                    inCombat = true;
                } else if ("BOSS".equals(roomType)) {
                    currentEnemy = "BOSS|Sentinela do Portao|" + (28 + level * 4) + "|" + (6 + level) + "|" + (3 + level) + "|" + (18 + level * 2) + "|" + (25 + level * 3);
                    String[] parts = currentEnemy.split("\\|");
                    enemyType = parts[0];
                    String enemyName = parts[1];
                    enemyHp = Integer.parseInt(parts[2]);
                    enemyAtk = Integer.parseInt(parts[3]);
                    enemyDef = Integer.parseInt(parts[4]);
                    enemyXp = Integer.parseInt(parts[5]);
                    enemyGold = Integer.parseInt(parts[6]);

                    System.out.println("O chefe te encara: " + enemyName);
                    log[logSize++] = "Boss iniciou: " + currentEnemy;
                    inCombat = true;
                } else if ("EXIT".equals(roomType)) {
                    System.out.println("Voce ve a saida. O ar muda.");
                    victory = true;
                    gameOver = true;
                    log[logSize++] = "Vitoria: saiu da masmorra.";
                    break;
                }
            }

            // Menu de acao
            System.out.println();
            System.out.println("Status: HP " + hp + "/" + maxHp + " | Mana " + mana + "/" + maxMana +
                    " | Lvl " + level + " XP " + xp + "/" + xpToNext + " | Gold " + gold + " | Fome " + hunger);
            System.out.println("Equip: " + shortEquip(equippedWeapon) + " , " + shortEquip(equippedArmor));
            if (inCombat) {
                String[] parts = currentEnemy.split("\\|");
                String enemyName = parts[1];
                System.out.println("Inimigo: " + enemyName + " HP " + enemyHp + " ATK " + enemyAtk + " DEF " + enemyDef);
            }

            System.out.println();
            System.out.println("Acoes:");
            if (inCombat) {
                System.out.println("1) Atacar");
                System.out.println("2) Defender");
                System.out.println("3) Magia (custa mana)");
                System.out.println("4) Usar item");
                System.out.println("5) Fugir");
                System.out.println("6) Inventario");
                System.out.println("7) Status detalhado");
                System.out.print("> ");
            } else {
                System.out.println("1) Avancar");
                System.out.println("2) Descansar");
                System.out.println("3) Usar item");
                System.out.println("4) Inventario");
                System.out.println("5) Status detalhado");
                System.out.println("6) Ver log (ultimos 10)");
                System.out.print("> ");
            }

            String opt = sc.nextLine().trim();
            if (opt.isEmpty()) opt = "0";

            if (!inCombat) {
                if ("1".equals(opt)) {
                    roomIndex++;
                    if (roomIndex >= totalRooms) {
                        roomIndex = totalRooms - 1;
                    }
                    continue;
                } else if ("2".equals(opt)) {
                    int heal = 4 + rng.nextInt(5);
                    hp += heal;
                    if (hp > maxHp) hp = maxHp;
                    int manaGain = 2 + rng.nextInt(3);
                    mana += manaGain;
                    if (mana > maxMana) mana = maxMana;
                    hunger += 8;
                    if (hunger > 120) hunger = 120;
                    System.out.println("Voce descansa. +" + heal + " HP, +" + manaGain + " Mana, fome aumenta.");
                    log[logSize++] = "Descanso: +" + heal + " HP +" + manaGain + " Mana.";
                } else if ("3".equals(opt)) {
                    // Usar item fora de combate
                    invSize = useItemFlow(sc, inv, invSize, refInt("hp", hp), refInt("mana", mana),
                            refInt("maxHp", maxHp), refInt("maxMana", maxMana), refInt("hunger", hunger), log, logSize);
                    // Monolito: esse refInt nao altera as variaveis reais.
                    System.out.println("Se algo parece nao ter mudado, faz parte do monolito.");
                } else if ("4".equals(opt)) {
                    printInventory(inv, invSize);
                    System.out.println("Opcao: (E)quipar, (D)ropar, (V)oltar");
                    String sub = sc.nextLine().trim().toUpperCase(Locale.ROOT);
                    if ("E".equals(sub)) {
                        System.out.print("Indice para equipar: ");
                        String idxStr = sc.nextLine().trim();
                        int idx = safeInt(idxStr, -1);
                        if (idx >= 1 && idx <= invSize) {
                            String item = inv[idx - 1];
                            if (item.startsWith("WEAPON|") || item.startsWith("ARMOR|")) {
                                if (item.startsWith("WEAPON|")) {
                                    inv[idx - 1] = equippedWeapon;
                                    equippedWeapon = item;
                                    log[logSize++] = "Equipou arma: " + item;
                                    System.out.println("Arma equipada.");
                                } else {
                                    inv[idx - 1] = equippedArmor;
                                    equippedArmor = item;
                                    log[logSize++] = "Equipou armadura: " + item;
                                    System.out.println("Armadura equipada.");
                                }
                            } else {
                                System.out.println("Nao e equipamento.");
                            }
                        } else {
                            System.out.println("Indice invalido.");
                        }
                    } else if ("D".equals(sub)) {
                        System.out.print("Indice para dropar: ");
                        int idx = safeInt(sc.nextLine().trim(), -1);
                        if (idx >= 1 && idx <= invSize) {
                            String dropped = inv[idx - 1];
                            for (int i = idx - 1; i < invSize - 1; i++) inv[i] = inv[i + 1];
                            inv[invSize - 1] = null;
                            invSize--;
                            log[logSize++] = "Dropou: " + dropped;
                            System.out.println("Dropado.");
                        } else {
                            System.out.println("Indice invalido.");
                        }
                    }
                } else if ("5".equals(opt)) {
                    printDetailed(playerName, hp, maxHp, mana, maxMana, level, xp, xpToNext, gold, hunger, equippedWeapon, equippedArmor);
                } else if ("6".equals(opt)) {
                    printLogTail(log, logSize, 10);
                } else {
                    System.out.println("Nada acontece.");
                }

            } else {
                // Em combate
                if ("1".equals(opt)) {
                    // Ataque basico
                    int[] eqW = parseEquip(equippedWeapon); // [atk, def]
                    int[] eqA = parseEquip(equippedArmor);
                    int atk = eqW[0] + (level / 2);
                    int def = eqA[1] + (level / 3);

                    int roll = rng.nextInt(100);
                    boolean crit = roll < (8 + level);
                    int base = atk + (rng.nextInt(4));
                    int dmg = base - enemyDef;
                    if (dmg < 1) dmg = 1;
                    if (crit) dmg *= 2;

                    enemyHp -= dmg;
                    System.out.println("Voce ataca e causa " + dmg + (crit ? " (critico)" : "") + ".");
                    log[logSize++] = "Ataque player: -" + dmg + " no inimigo.";

                    if (enemyHp <= 0) {
                        // Vitoria no combate
                        String enemyName = currentEnemy.split("\\|")[1];
                        System.out.println("Voce derrotou " + enemyName + ".");
                        log[logSize++] = "Inimigo derrotado: " + currentEnemy;

                        // Recompensas
                        gold += enemyGold;
                        xp += enemyXp;
                        System.out.println("Ganhou " + enemyGold + " gold e " + enemyXp + " XP.");
                        log[logSize++] = "Recompensas: +" + enemyGold + " gold, +" + enemyXp + " xp.";

                        // Loot
                        if (rng.nextInt(100) < 45) {
                            String loot = generateLoot(rng, level);
                            System.out.println("Drop: " + prettyItem(loot));
                            log[logSize++] = "Drop: " + loot;
                            if (invSize < invCap) {
                                inv[invSize++] = loot;
                            } else {
                                System.out.println("Inventario cheio. O item foi perdido.");
                                log[logSize++] = "Inventario cheio, perdeu loot.";
                            }
                        }

                        // Level up
                        while (xp >= xpToNext) {
                            xp -= xpToNext;
                            level++;
                            xpToNext = 12 + (level * 6);
                            int gainHp = 5 + rng.nextInt(4);
                            int gainMana = 2 + rng.nextInt(2);
                            maxHp += gainHp;
                            maxMana += gainMana;
                            hp = maxHp;
                            mana = maxMana;
                            System.out.println("LEVEL UP. Agora lvl " + level + ". MaxHP +" + gainHp + " MaxMana +" + gainMana + ".");
                            log[logSize++] = "Level up para " + level + ".";
                        }

                        // Sair do combate
                        inCombat = false;
                        currentEnemy = null;
                        enemyHp = 0;
                        enemyAtk = 0;
                        enemyDef = 0;
                        enemyXp = 0;
                        enemyGold = 0;

                        // Avanca apos vencer
                        roomIndex++;
                        if (roomIndex >= totalRooms) roomIndex = totalRooms - 1;
                        continue;
                    }

                    // Turno do inimigo
                    int inRoll = rng.nextInt(100);
                    boolean enemyCrit = inRoll < 10;
                    int raw = enemyAtk + rng.nextInt(4);
                    int taken = raw - (parseEquip(equippedArmor)[1] + (level / 4));
                    if (taken < 1) taken = 1;
                    if (enemyCrit) taken += 2;

                    hp -= taken;
                    System.out.println("O inimigo te acerta, -" + taken + " HP" + (enemyCrit ? " (brutal)" : "") + ".");
                    log[logSize++] = "Dano recebido: -" + taken + " HP.";

                } else if ("2".equals(opt)) {
                    // Defender reduz dano do inimigo
                    int block = 2 + (parseEquip(equippedArmor)[1] / 2) + (level / 3);
                    System.out.println("Voce se defende. Bloqueio " + block + ".");
                    log[logSize++] = "Defendeu com bloqueio " + block + ".";

                    int raw = enemyAtk + rng.nextInt(5);
                    int taken = raw - block;
                    if (taken < 0) taken = 0;
                    hp -= taken;
                    System.out.println("O inimigo ataca, -" + taken + " HP.");
                    log[logSize++] = "Dano recebido ao defender: -" + taken + " HP.";

                    // Pequena chance de contra ataque
                    if (rng.nextInt(100) < 25) {
                        int counter = 1 + rng.nextInt(3) + (level / 3);
                        enemyHp -= counter;
                        System.out.println("Contra ataque, -" + counter + " HP no inimigo.");
                        log[logSize++] = "Contra ataque: -" + counter + " no inimigo.";
                        if (enemyHp <= 0) {
                            System.out.println("Voce venceu no contra ataque.");
                            log[logSize++] = "Vitoria por contra ataque.";
                            gold += enemyGold;
                            xp += enemyXp;
                            inCombat = false;
                            roomIndex++;
                            continue;
                        }
                    }

                } else if ("3".equals(opt)) {
                    // Magia
                    if (mana <= 0) {
                        System.out.println("Sem mana.");
                        continue;
                    }
                    System.out.println("Escolha magia: 1) Firebolt (3 mana) 2) Ice (2 mana) 3) Heal (4 mana)");
                    String m = sc.nextLine().trim();
                    if ("1".equals(m)) {
                        if (mana < 3) { System.out.println("Mana insuficiente."); continue; }
                        mana -= 3;
                        int dmg = 6 + rng.nextInt(6) + (level / 2);
                        if ("UNDEAD".equals(enemyType)) dmg += 2;
                        enemyHp -= (dmg - (enemyDef / 2));
                        System.out.println("Firebolt, dano " + dmg + ".");
                        log[logSize++] = "Magia Firebolt: -" + dmg + ".";
                    } else if ("2".equals(m)) {
                        if (mana < 2) { System.out.println("Mana insuficiente."); continue; }
                        mana -= 2;
                        int dmg = 4 + rng.nextInt(5) + (level / 3);
                        enemyHp -= (dmg - (enemyDef / 3));
                        System.out.println("Ice, dano " + dmg + ". Inimigo fica mais lento.");
                        log[logSize++] = "Magia Ice: -" + dmg + ".";
                        enemyAtk = Math.max(1, enemyAtk - 1);
                    } else if ("3".equals(m)) {
                        if (mana < 4) { System.out.println("Mana insuficiente."); continue; }
                        mana -= 4;
                        int heal = 7 + rng.nextInt(6) + (level / 3);
                        hp += heal;
                        if (hp > maxHp) hp = maxHp;
                        System.out.println("Heal, +" + heal + " HP.");
                        log[logSize++] = "Magia Heal: +" + heal + " HP.";
                    } else {
                        System.out.println("Magia invalida.");
                        continue;
                    }

                    if (enemyHp <= 0) {
                        String enemyName = currentEnemy.split("\\|")[1];
                        System.out.println("Voce derrotou " + enemyName + " com magia.");
                        log[logSize++] = "Vitoria por magia.";
                        gold += enemyGold;
                        xp += enemyXp;
                        inCombat = false;
                        roomIndex++;
                        continue;
                    }

                    // Retaliacao inimiga
                    int raw = enemyAtk + rng.nextInt(4);
                    int taken = raw - (parseEquip(equippedArmor)[1] + (level / 5));
                    if (taken < 1) taken = 1;
                    hp -= taken;
                    System.out.println("O inimigo aproveita e ataca, -" + taken + " HP.");
                    log[logSize++] = "Dano recebido apos magia: -" + taken + " HP.";

                } else if ("4".equals(opt)) {
                    // Usar item em combate (simples)
                    if (invSize == 0) {
                        System.out.println("Inventario vazio.");
                        continue;
                    }
                    printInventory(inv, invSize);
                    System.out.print("Indice do item para usar (0 cancela): ");
                    int idx = safeInt(sc.nextLine().trim(), -1);
                    if (idx <= 0 || idx > invSize) {
                        System.out.println("Cancelado.");
                        continue;
                    }
                    String item = inv[idx - 1];
                    if (item.startsWith("POTION|")) {
                        // "POTION|NAME|+10HP|PRICE|RARITY"
                        String[] p = item.split("\\|");
                        int heal = parseSignedValue(p[2], "HP");
                        hp += heal;
                        if (hp > maxHp) hp = maxHp;
                        System.out.println("Usou " + p[1] + ", +" + heal + " HP.");
                        log[logSize++] = "Usou pocao: +" + heal + " HP.";
                        invSize = removeInv(inv, invSize, idx - 1);
                    } else if (item.startsWith("FOOD|")) {
                        String[] p = item.split("\\|");
                        int dh = parseSignedValue(p[2], "H");
                        hunger += dh; // dh e negativo, entao reduz fome
                        if (hunger < 0) hunger = 0;
                        System.out.println("Comeu " + p[1] + ", fome " + dh + ".");
                        log[logSize++] = "Usou comida: " + dh + " fome.";
                        invSize = removeInv(inv, invSize, idx - 1);
                    } else if (item.startsWith("BOMB|")) {
                        String[] p = item.split("\\|");
                        int dmg = Integer.parseInt(p[2]);
                        enemyHp -= dmg;
                        System.out.println("Bomba, -" + dmg + " no inimigo.");
                        log[logSize++] = "Bomba: -" + dmg + ".";
                        invSize = removeInv(inv, invSize, idx - 1);
                        if (enemyHp <= 0) {
                            System.out.println("O inimigo caiu com a bomba.");
                            gold += enemyGold;
                            xp += enemyXp;
                            inCombat = false;
                            roomIndex++;
                            continue;
                        }
                    } else {
                        System.out.println("Esse item nao pode ser usado agora.");
                    }

                    // Turno inimigo depois de item
                    int raw = enemyAtk + rng.nextInt(4);
                    int taken = raw - (parseEquip(equippedArmor)[1] + (level / 5));
                    if (taken < 1) taken = 1;
                    hp -= taken;
                    System.out.println("O inimigo ataca, -" + taken + " HP.");
                    log[logSize++] = "Dano recebido apos item: -" + taken + " HP.";

                } else if ("5".equals(opt)) {
                    // Fugir
                    int chance = 35 + (level * 2) - (enemyAtk);
                    if (chance < 10) chance = 10;
                    if (chance > 75) chance = 75;
                    int roll = rng.nextInt(100);
                    if (roll < chance) {
                        System.out.println("Voce fugiu.");
                        log[logSize++] = "Fuga bem sucedida.";
                        inCombat = false;
                        // Penalidade
                        int lost = 2 + rng.nextInt(5);
                        gold -= lost;
                        if (gold < 0) gold = 0;
                        hunger += 6;
                        // Volta uma sala
                        roomIndex = Math.max(0, roomIndex - 1);
                    } else {
                        System.out.println("Falhou em fugir.");
                        log[logSize++] = "Fuga falhou.";
                        int raw = enemyAtk + rng.nextInt(6);
                        int taken = raw - (parseEquip(equippedArmor)[1] / 2);
                        if (taken < 1) taken = 1;
                        hp -= taken;
                        System.out.println("Punicao, -" + taken + " HP.");
                        log[logSize++] = "Punicao por fuga: -" + taken + ".";
                    }

                } else if ("6".equals(opt)) {
                    printInventory(inv, invSize);
                } else if ("7".equals(opt)) {
                    printDetailed(playerName, hp, maxHp, mana, maxMana, level, xp, xpToNext, gold, hunger, equippedWeapon, equippedArmor);
                } else {
                    System.out.println("Hesitacao.");
                }
            }
        }

        // Fim
        System.out.println();
        if (victory) {
            System.out.println("VITORIA. " + playerName + " escapou da masmorra.");
        } else {
            System.out.println("DERROTA. " + playerName + " caiu na masmorra.");
        }

        System.out.println();
        System.out.println("Resumo:");
        System.out.println("Level " + level + " | Gold " + gold + " | Salas alcançadas " + (roomIndex + 1) + "/" + totalRooms);

        System.out.println();
        System.out.print("Ver log completo? (s/N): ");
        String show = sc.nextLine().trim().toLowerCase(Locale.ROOT);
        if (show.equals("s") || show.equals("sim")) {
            for (int i = 0; i < logSize; i++) {
                System.out.println((i + 1) + ") " + log[i]);
            }
        }

        sc.close();
    }

    // Gera tipo de sala de forma arbitraria
    static String generateRoom(Random rng, int i, int total) {
        if (i == 0) return "EMPTY";
        if (i == total - 1) return "EXIT";
        int r = rng.nextInt(100);
        if (r < 10) return "TRAP";
        if (r < 25) return "CHEST";
        if (r < 35) return "FOUNTAIN";
        if (r < 45) return "SHOP";
        return "COMBAT";
    }

    // Inimigo string: "TYPE|NAME|HP|ATK|DEF|XP|GOLD"
    static String generateEnemy(Random rng, int level) {
        int r = rng.nextInt(100);
        if (r < 40) {
            int hp = 10 + level * 2 + rng.nextInt(5);
            int atk = 3 + level / 2 + rng.nextInt(3);
            int def = 1 + rng.nextInt(2);
            int xp = 6 + level;
            int gold = 4 + rng.nextInt(6);
            return "BEAST|Rato Gigante|" + hp + "|" + atk + "|" + def + "|" + xp + "|" + gold;
        } else if (r < 75) {
            int hp = 12 + level * 2 + rng.nextInt(7);
            int atk = 4 + level / 2 + rng.nextInt(3);
            int def = 2 + rng.nextInt(3);
            int xp = 7 + level;
            int gold = 5 + rng.nextInt(8);
            return "BANDIT|Saqueador|" + hp + "|" + atk + "|" + def + "|" + xp + "|" + gold;
        } else {
            int hp = 14 + level * 2 + rng.nextInt(9);
            int atk = 4 + level / 2 + rng.nextInt(4);
            int def = 2 + rng.nextInt(3);
            int xp = 8 + level + 2;
            int gold = 6 + rng.nextInt(9);
            return "UNDEAD|Esqueleto|" + hp + "|" + atk + "|" + def + "|" + xp + "|" + gold;
        }
    }

    // Loot: equipamento ou consumivel em strings
    static String generateLoot(Random rng, int level) {
        int r = rng.nextInt(100);
        if (r < 25) {
            int heal = 8 + rng.nextInt(8) + (level / 2);
            int price = 5 + level;
            return "POTION|Pocao|" + "+" + heal + "HP|" + price + "|COMMON";
        } else if (r < 45) {
            int dh = -12 - rng.nextInt(10);
            int price = 4 + level;
            return "FOOD|Racao|" + "H" + dh + "|" + price + "|COMMON";
        } else if (r < 60) {
            int dmg = 10 + rng.nextInt(6) + (level / 2);
            int price = 9 + level;
            return "BOMB|Bomba|" + dmg + "|" + price + "|UNCOMMON";
        } else if (r < 80) {
            int atk = 3 + (level / 2) + rng.nextInt(4);
            int def = 0;
            int price = 12 + level * 2;
            String name = (rng.nextInt(100) < 50) ? "Espada Curta" : "Machado";
            return "WEAPON|" + name + "|" + atk + "|" + def + "|" + price + "|UNCOMMON";
        } else {
            int atk = 0;
            int def = 2 + (level / 3) + rng.nextInt(4);
            int price = 12 + level * 2;
            String name = (rng.nextInt(100) < 50) ? "Cota de Couro" : "Armadura Leve";
            return "ARMOR|" + name + "|" + atk + "|" + def + "|" + price + "|UNCOMMON";
        }
    }

    static String prettyItem(String item) {
        if (item == null) return "(null)";
        String[] p = item.split("\\|");
        if (p.length < 2) return item;
        if (item.startsWith("WEAPON|") || item.startsWith("ARMOR|")) {
            return p[1] + " [ATK " + p[2] + " DEF " + p[3] + "]";
        }
        if (item.startsWith("POTION|")) return p[1] + " " + p[2];
        if (item.startsWith("FOOD|")) return p[1] + " " + p[2];
        if (item.startsWith("BOMB|")) return p[1] + " Dano " + p[2];
        return item;
    }

    static void printInventory(String[] inv, int invSize) {
        System.out.println("Inventario:");
        if (invSize == 0) {
            System.out.println("(vazio)");
            return;
        }
        for (int i = 0; i < invSize; i++) {
            System.out.println((i + 1) + ") " + prettyItem(inv[i]) + " :: raw=" + inv[i]);
        }
    }

    static int removeInv(String[] inv, int invSize, int idx) {
        if (idx < 0 || idx >= invSize) return invSize;
        for (int i = idx; i < invSize - 1; i++) inv[i] = inv[i + 1];
        inv[invSize - 1] = null;
        return invSize - 1;
    }

    static void printDetailed(String name, int hp, int maxHp, int mana, int maxMana, int level, int xp, int xpToNext,
                              int gold, int hunger, String weap, String armor) {
        System.out.println();
        System.out.println("Detalhes:");
        System.out.println("Nome: " + name);
        System.out.println("HP: " + hp + "/" + maxHp);
        System.out.println("Mana: " + mana + "/" + maxMana);
        System.out.println("Level: " + level);
        System.out.println("XP: " + xp + "/" + xpToNext);
        System.out.println("Gold: " + gold);
        System.out.println("Fome: " + hunger);
        System.out.println("Arma: " + weap);
        System.out.println("Armadura: " + armor);
    }

    static void printLogTail(String[] log, int logSize, int n) {
        System.out.println("Ultimos eventos:");
        int start = Math.max(0, logSize - n);
        for (int i = start; i < logSize; i++) {
            System.out.println((i + 1) + ") " + log[i]);
        }
    }

    static int[] parseEquip(String equip) {
        // "WEAPON|NAME|ATK|DEF|PRICE|RARITY"
        // "ARMOR|NAME|ATK|DEF|PRICE|RARITY"
        int[] out = new int[]{0,0};
        if (equip == null) return out;
        String[] p = equip.split("\\|");
        if (p.length >= 4) {
            out[0] = safeInt(p[2], 0);
            out[1] = safeInt(p[3], 0);
        }
        return out;
    }

    static String shortEquip(String equip) {
        if (equip == null) return "(null)";
        String[] p = equip.split("\\|");
        if (p.length < 2) return equip;
        if (p.length >= 4) return p[1] + " [A " + p[2] + " D " + p[3] + "]";
        return p[1];
    }

    static int safeInt(String s, int def) {
        try { return Integer.parseInt(s); }
        catch (Exception e) { return def; }
    }

    static int parseSignedValue(String token, String suffix) {
        // "+10HP" ou "H-15"
        // Feito de forma confusa de proposito.
        token = token.trim().toUpperCase(Locale.ROOT);
        String t = token.replace(" ", "");
        if (t.startsWith("H")) {
            // H-15
            String num = t.substring(1);
            return safeInt(num, 0);
        }
        if (t.endsWith(suffix.toUpperCase(Locale.ROOT))) {
            String num = t.substring(0, t.length() - suffix.length());
            return safeInt(num.replace("+",""), 0);
        }
        return 0;
    }

    static int addToInventory(String[] inv, int invSize, int invCap, String item, String[] log, int logSize) {
        if (invSize >= invCap) {
            System.out.println("Inventario cheio, item perdido: " + prettyItem(item));
            if (logSize < log.length) log[logSize++] = "Inventario cheio, perdeu: " + item;
            return invSize;
        }
        inv[invSize++] = item;
        return invSize;
    }

    // Representacao fake de "passagem por referencia" para confundir a vida
    static int[] refInt(String name, int value) {
        return new int[]{ value };
    }

    static int useItemFlow(Scanner sc, String[] inv, int invSize,
                           int[] hpRef, int[] manaRef, int[] maxHpRef, int[] maxManaRef, int[] hungerRef,
                           String[] log, int logSize) {
        if (invSize == 0) {
            System.out.println("Inventario vazio.");
            return invSize;
        }
        printInventory(inv, invSize);
        System.out.print("Indice do item para usar (0 cancela): ");
        int idx = safeInt(sc.nextLine().trim(), -1);
        if (idx <= 0 || idx > invSize) return invSize;

        String item = inv[idx - 1];
        if (item.startsWith("POTION|")) {
            String[] p = item.split("\\|");
            int heal = parseSignedValue(p[2], "HP");
            hpRef[0] += heal;
            if (hpRef[0] > maxHpRef[0]) hpRef[0] = maxHpRef[0];
            System.out.println("Usou " + p[1] + ", +" + heal + " HP.");
            if (logSize < log.length) log[logSize++] = "Usou pocao fora de combate.";
            invSize = removeInv(inv, invSize, idx - 1);
        } else if (item.startsWith("FOOD|")) {
            String[] p = item.split("\\|");
            int dh = parseSignedValue(p[2], "H");
            hungerRef[0] += dh;
            if (hungerRef[0] < 0) hungerRef[0] = 0;
            System.out.println("Comeu " + p[1] + ", fome " + dh + ".");
            if (logSize < log.length) log[logSize++] = "Usou comida fora de combate.";
            invSize = removeInv(inv, invSize, idx - 1);
        } else {
            System.out.println("Esse item nao pode ser usado fora de combate.");
        }
        return invSize;
    }

    static void handleShop(Scanner sc, Random rng, int level, String[] inv, int invCap, int gold, String[] log, int logSize) {
        // Monolito: nao retorna nada, nao altera gold no chamador.
        // Loja por itens gerados na hora.
        String[] stock = new String[5];
        for (int i = 0; i < stock.length; i++) stock[i] = generateLoot(rng, level);

        boolean done = false;
        while (!done) {
            System.out.println();
            System.out.println("LOJA. Seu gold: " + gold);
            for (int i = 0; i < stock.length; i++) {
                if (stock[i] == null) continue;
                String[] p = stock[i].split("\\|");
                String price = (p.length >= 4) ? p[3] : "0";
                System.out.println((i + 1) + ") " + prettyItem(stock[i]) + " Preco " + price);
            }
            System.out.println("A) Comprar, B) Vender, S) Sair");
            System.out.print("> ");
            String opt = sc.nextLine().trim().toUpperCase(Locale.ROOT);
            if ("S".equals(opt)) {
                done = true;
            } else if ("A".equals(opt)) {
                System.out.print("Indice para comprar: ");
                int idx = safeInt(sc.nextLine().trim(), -1);
                if (idx < 1 || idx > stock.length || stock[idx - 1] == null) {
                    System.out.println("Invalido.");
                    continue;
                }
                String item = stock[idx - 1];
                String[] p = item.split("\\|");
                int price = (p.length >= 4) ? safeInt(p[3], 0) : 0;

                if (gold < price) {
                    System.out.println("Sem gold.");
                    continue;
                }
                gold -= price;
                if (logSize < log.length) log[logSize++] = "Comprou: " + item + " por " + price;

                // adiciona ao inventario, sem verificar de verdade o retorno
                int invSize = 0;
                for (String s : inv) if (s != null) invSize++;
                if (invSize < invCap) {
                    // encontra primeiro null
                    for (int i = 0; i < inv.length; i++) {
                        if (inv[i] == null) { inv[i] = item; break; }
                    }
                    stock[idx - 1] = null;
                    System.out.println("Comprado.");
                } else {
                    System.out.println("Inventario cheio. Compra perdida, e o gold ja foi.");
                }
            } else if ("B".equals(opt)) {
                // vender: escolhe item do inventario e recebe metade do preco se existir
                int invSize = 0;
                List<Integer> mapIdx = new ArrayList<>();
                System.out.println("Inventario para vender:");
                for (int i = 0; i < inv.length; i++) {
                    if (inv[i] != null) {
                        invSize++;
                        mapIdx.add(i);
                        System.out.println(invSize + ") " + prettyItem(inv[i]) + " raw=" + inv[i]);
                    }
                }
                if (invSize == 0) {
                    System.out.println("Nada para vender.");
                    continue;
                }
                System.out.print("Indice para vender (0 cancela): ");
                int sell = safeInt(sc.nextLine().trim(), -1);
                if (sell <= 0 || sell > invSize) continue;

                int real = mapIdx.get(sell - 1);
                String item = inv[real];
                int price = 0;

                String[] p = item.split("\\|");
                if (item.startsWith("WEAPON|") || item.startsWith("ARMOR|")) {
                    price = (p.length >= 5) ? safeInt(p[4], 0) : 0;
                } else if (item.startsWith("POTION|") || item.startsWith("FOOD|") || item.startsWith("BOMB|")) {
                    price = (p.length >= 4) ? safeInt(p[3], 0) : 0;
                }

                int gain = Math.max(1, price / 2);
                gold += gain;
                inv[real] = null;
                if (logSize < log.length) log[logSize++] = "Vendeu: " + item + " por " + gain;
                System.out.println("Vendido por " + gain + ".");
            } else {
                System.out.println("...");
            }
        }
    }
}
