package Launcher;

import Game.GameEventHandler;
import Game.MainGame;
import Game.Move;

import java.util.Scanner;

public class CommandLineApp {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        char p2Type = askP2Type();
        int aiDifficulty = askAIDifficulty(p2Type == 'c');
        char p1Color = askP1Color();

        GameEventHandler handler = new GameEventHandler() {
            private int[] move = new int[4];

            @Override
            public boolean requestShouldPlayAgain() {
                log("\nDo you want to play again? (enter 'y' or 'n'):");
                log("\n");

                char playAgainChar = 'a';
                while (playAgainChar != 'y' && playAgainChar != 'n') {
                    playAgainChar = input.next().charAt(0);
                }
                return playAgainChar == 'y';
            }

            @Override
            public int[] createMove() {
                boolean valid = false;

                while (!valid) {
                    try {
                        System.out.println("Type 'u' to undo, 'r' to redo.\n");
                        System.out.print("Starting coordinate: ");
                        String input1 = input.next().toLowerCase();

                        // Verify undo/redo
                        if (input1.equals("u") || input1.equals("r")) {
                            move[0] = input1.equals("u") ? 8 : 9;
                            return move;
                        }

                        System.out.print("Ending coordinate: ");
                        String input2 = input.next().toLowerCase();

                        if (input1.length() != 2 || input2.length() != 2) {
                            System.out.println("Unknown coordinate!");
                            continue;
                        }

                        move[0] = input1.charAt(0) - 'a';
                        move[1] = 8 - (int)(input1.charAt(1) - '0');
                        move[2] = input2.charAt(0) - 'a';
                        move[3] = 8 - (int)(input2.charAt(1) - '0');

                        valid = true;
                    }
                    catch (java.util.InputMismatchException e) {
                        input.next();
                        continue;
                    }
                }
                return move;
            }

            @Override
            public void log(String out) {
                System.out.println(out);
            }
        };

        new MainGame(handler, p1Color, p2Type, aiDifficulty).start();
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
            boolean valid = false;
            while (!valid) {
                try {
                    System.out.println("Select computer difficulty (1,2,3):");
                    difficulty = input.nextInt();
                    if (difficulty == 1 || difficulty == 2 || difficulty == 3) valid = true;
                }
                catch (java.util.InputMismatchException e) {
                    input.next();
                    continue;
                }
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
