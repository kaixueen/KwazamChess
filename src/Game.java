// Manage game logic
public class Game {
    private GameBoard gameBoard;
    private GameController gameController;
    private GameView gameView;
    private int currentTurn;
    private String currentPlayer;
    private boolean isGameOver;
    private String player1, player2;

    // Contructor
    public Game() {
        currentTurn = 0;
        currentPlayer = player1 = "RED";
        player2 = "BLUE";
        isGameOver = false;
        gameBoard = new GameBoard();
        gameView = new GameView();
        gameController = new GameController(gameView, gameBoard);
        initializeGame();
    }

    // Initialize game
    public void initializeGame() {
        gameBoard.initializeBoard();
        while (!isGameOver) {
            gameBoard.printBoard();
            System.out.println("Player " + currentPlayer + " turn");
            gameBoard.movePiece(currentPlayer);
            if (gameBoard.checkWin(currentPlayer)) {
                gameBoard.printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                isGameOver = true;
            }
            currentTurn++;
            currentPlayer = currentTurn % 2 == 0 ? player1 : player2;
        }
    }

    public static void main(String[] args) {
        // Create a new game
        Game game = new Game();
    }
}
