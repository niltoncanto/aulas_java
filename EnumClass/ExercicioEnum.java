/*
Crie um programa em Java chamado ExercicioEnum.java que utilize um enum para representar 
os níveis de prioridade de uma tarefa: BAIXA, MEDIA e ALTA.
O programa deve:
Exibir todas as prioridades possíveis;
Ler do usuário o nome de uma prioridade (por exemplo, ALTA);
Exibir uma mensagem diferente dependendo da prioridade escolhida.
*/

import java.util.Scanner;

public class ExercicioEnum {

    // Enum representando os níveis de prioridade
    enum Prioridade {
        BAIXA,
        MEDIA,
        ALTA
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Exibir todas as prioridades
        System.out.println("Níveis de prioridade disponíveis:");
        for (Prioridade p : Prioridade.values()) {
            System.out.println("- " + p);
        }

        // 2. Ler o nome da prioridade digitada pelo usuário
        System.out.print("\nDigite a prioridade desejada (BAIXA, MEDIA ou ALTA): ");
        String entrada = sc.nextLine().toUpperCase();

        try {
            Prioridade prioridade = Prioridade.valueOf(entrada);

            // 3. Mostrar mensagem conforme a prioridade
            switch (prioridade) {
                case BAIXA:
                    System.out.println("Prioridade baixa: pode ser feita depois.");
                    break;
                case MEDIA:
                    System.out.println("Prioridade média: planeje para esta semana.");
                    break;
                case ALTA:
                    System.out.println("Prioridade alta: faça o quanto antes!");
                    break;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Valor inválido! Use BAIXA, MEDIA ou ALTA.");
        }
        sc.close();
    }
}
