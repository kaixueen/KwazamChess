package Model;

import Controller.GameController;
import View.GameView;

// Manage game logic
public class Game {
    private GameBoard gameBoard;
    private GameController gameController;
    private GameView gameView;

    // Contructor
    public Game() {
        gameBoard = GameBoard.getInstance();
        gameView = new GameView();
        gameController = new GameController(gameView, gameBoard);
    }

    public static void main(String[] args) {
        // Create a new game
        Game game = new Game();
    }
}
