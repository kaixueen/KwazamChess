// Manage game logic
public class Game {
    private GameBoard gameBoard;
    private int currentTurn;
    private String currentPlayer;
    private boolean isGameOver;
    private PieceFactory factory;

    // Contructor
    public Game() {
        currentTurn = 0;
        currentPlayer = "RED";
        isGameOver = false;
        factory = new PieceFactory();
        gameBoard = new GameBoard();
        initializeGame();
    }

    // Initialize game
    public void initializeGame() {
        gameBoard.initializeBoard(factory);
    }

    public static void main(String[] args) {
        // Create a new game
        Game game = new Game();
    }
}
