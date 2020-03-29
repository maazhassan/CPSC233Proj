package Launcher;

import Game.GameEventHandler;
import Game.MainGame;
import java.util.Scanner;

/**
 * This class is used to play the command line version of the game. The handler
 * created within this class is passed to a MainGame instance to control it.
 */

public class CommandLineApp {
    private static Scanner input = new Scanner(System.in);

    //Main method, used to actually run the game
    public static void main(String[] args) {

        //Getting information
        char p2Type = askP2Type();
        int aiDifficulty = askAIDifficulty(p2Type == 'c');
        char p1Color = askP1Color();

        //Create handler for command line app
        GameEventHandler handler = new GameEventHandler() {
            private int[] move = new int[4];

            @Override
            public boolean requestShouldPlayAgain(String winMessage) {
                log(winMessage);
                log("\nDo you want to play again? (enter 'y' or 'n'):");

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
                    catch (Exception e) {
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

            @Override
            public void moveLog(String move, boolean left) {
                //Nothing
            }

            @Override
            public void removeFromMoveLog(boolean left, int len) {
                //Nothing
            }
        };

        //Create and start an instance of MainGame with the previously defined handler
        new MainGame(handler, p1Color, p2Type, aiDifficulty).start();
    }

    /**
     * A method that asks the player to input the type of player 2. Must be either 'c' or 'h'.
     * @return The selected type of player 2.
     */

    private static char askP2Type() {
        char p2Type = 'a';
        boolean valid = false;
        while (!valid) {
            try {
                System.out.println("Play against computer or another human? (enter 'c' or 'h'):");
                String p2TypeString = input.next();
                if (p2TypeString.equals("c") || p2TypeString.equals("h")) {
                    valid = true;
                    p2Type = p2TypeString.charAt(0);
                }
            }
            catch (Exception e) {
                input.next();
                continue;
            }
        }
        return p2Type;
    }

    /**
     * A method that asks the player to input the difficulty of the AI, if they
     * choose to play against the computer. Must be either 1, 2, or 3.
     * @param p2IsComp True if player 2 is computer. False otherwise.
     * @return The selected difficulty of the AI.
     */

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
                catch (Exception e) {
                    input.next();
                    continue;
                }
            }
        }
        return difficulty;
    }

    /**
     * A method that asks the player to input the color they want to play as. Must be either 'w' or 'b'.
     * @return The selected color of player 1.
     */

    private static char askP1Color() {
        char p1Color = 'a';
        while (p1Color != 'w' && p1Color != 'b') {
            System.out.println("Do you want to play as white or black? (enter 'w' or 'b'):");
            p1Color = input.next().charAt(0);
        }
        return p1Color;
    }
}
