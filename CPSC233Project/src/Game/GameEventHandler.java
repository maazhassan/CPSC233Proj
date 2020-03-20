package Game;

/**
 * An interface for methods used in game handlers. These handlers
 * are passed to an instance of MainGame to control the back end
 * execution of the game.
 */

public interface GameEventHandler {

    /**
     * A method that asks the user if they want to play again, after the
     * current game has finished.
     * @return True if the user chooses to play again.
     *    <li> False otherwise. </li>
     */

    boolean requestShouldPlayAgain(String winMessage);

    /**
     * A method that creates an integer array that represents a move.
     * The coordinates in the integer array are used to construct the
     * actual Move object, which is passed to the playMove() method in
     * the MainGame class.
     */

    int[] createMove(); // [x1, y1, x2, y2]

    /**
     * A method that logs the passed String. Can be logged to the console
     * or some other log, depending on the implementation.
     * @param out The string to log.
     */

    void log(String out);
}
