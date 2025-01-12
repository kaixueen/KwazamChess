import Controller.GameController;
import Model.GameBoard;
import View.GameView;

// Starting file
public class KwazamChess {
    private GameBoard gameBoard;
    private GameController gameController;
    private GameView gameView;

    // Contructor
    public KwazamChess() {
        gameBoard = GameBoard.getInstance();
        gameView = new GameView();
        gameController = new GameController(gameView, gameBoard);
    }

    public static void main(String[] args) {
        // Create a new game
        KwazamChess game = new KwazamChess();
    }
}
