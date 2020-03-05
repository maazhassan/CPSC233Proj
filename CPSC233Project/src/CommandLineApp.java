import Game.Game;

import java.util.Scanner;

public class CommandLineApp {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        char p2Type = askP2Type();
        int aiDifficulty = askAIDifficulty(p2Type == 'c');
        char p1Color = askP1Color();

        new Game(p1Color, p2Type, aiDifficulty).start();
    }

    private static char askP2Type() {
        char p2Type = 'a';
        while (p2Type != 'c' && p2Type != 'h') {
            System.out.println("Play against computer or another human? (enter 'c' or 'h'):");
            p2Type = input.nextLine().charAt(0);
        }
        return p2Type;
    }

    private static int askAIDifficulty(boolean p2IsComp) {
        int difficulty = 0;
        if (p2IsComp) {
            while (difficulty != 1 && difficulty != 2 && difficulty != 3) {
                System.out.println("Select computer difficulty (1,2,3):");
                difficulty = input.nextInt();
            }
        }
        return difficulty;
    }

    private static char askP1Color() {
        char p1Color = 'a';
        while (p1Color != 'w' && p1Color != 'b') {
            System.out.println("Do you want to play as white or black? (enter 'w' or 'b'):");
            p1Color = input.next().charAt(0);
        }
        return p1Color;
    }
}
