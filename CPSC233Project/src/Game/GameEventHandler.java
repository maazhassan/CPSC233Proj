package Game;

public interface GameEventHandler {
    boolean requestShouldPlayAgain();
    int[] createMove(); // [x1, y1, x2, y2]
    void log(String out);
}
