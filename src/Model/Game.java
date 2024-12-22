package Model;

import Controller.GameController;

// Manage game logic
public class Game {
    private GameBoard gameBoard;
    private GameController gameController;
    private GameView gameView;

    // Contructor
    public Game() {
        gameBoard = new GameBoard();
        gameView = new GameView();
        gameController = new GameController(gameView, gameBoard);
        initializeGame();
    }

    // Initialize game
    public void initializeGame() {
        gameBoard.initializeBoard();

    }

    public static void main(String[] args) {
        // Create a new game
        Game game = new Game();
    }
}
