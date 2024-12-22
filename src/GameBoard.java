// Manage game board
public class GameBoard {
    public static final int ROWS = 8;
    public static final int COLUMNS = 5;
    private static final int TURN_LIMIT = 50;
    private PieceFactory factory;
    private Position redSauPosition, blueSauPosition;
    private int remainingRedPieces, remainingBluePieces;
    private String winner;
    
    private Piece[][] board;

    public GameBoard() {
        board = new Piece[ROWS][COLUMNS];
        remainingBluePieces = remainingRedPieces = 10;
        factory = new PieceFactory();
        initializeBoard();
        redSauPosition = new Position(2, 0);
        blueSauPosition = new Position(2, ROWS - 1);
        winner = "";
    }
    
    public void initializeBoard() {
        resetBoard();
        // Initialize board with RAM pieces
        for (int col = 0; col < COLUMNS; col++) {
            board[1][col] = factory.createPiece("RAM", "RED", col, 1);
            board[ROWS - 2][col] = factory.createPiece("RAM", "BLUE", col, ROWS - 2);
        }

        // Initialize board with other pieces
        String[] firstRowPieces = "TOR BIZ SAU BIZ XOR".split(" ");
        for (int col = 0; col < COLUMNS; col++) {
            board[0][col] = factory.createPiece(firstRowPieces[col], "RED", col, 0);
            board[ROWS - 1][col] = factory.createPiece(firstRowPieces[col], "BLUE", col, ROWS - 1);
        }
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public boolean isEmpty(int x, int y) {
        return getPieceAt(x, y) == null;
    }

    public boolean isInBounds(int x, int y) {
        return y >= 0 && y < ROWS && x >= 0 && x < COLUMNS;
    }

    public Piece getPieceAt(int x, int y) {
        if (isInBounds(x, y) && !isEmpty(x, y)) {
            Piece piece = board[y][x];
            return piece;
        }
        return null;
    }

    public void removePieceAt(int x, int y) {
        if (isInBounds(x, y)) {
            board[y][x] = null;
        }
    }

    public void movePieceTo(int fromX, int fromY, int toX, int toY) {
        if (isInBounds(fromX, fromY) && isInBounds(toX, toY)) {
            Piece piece = getPieceAt(fromX, fromY);
            if (isEmpty(toX, toY) && piece != null) {
                board[toY][toX] = piece;
            }
        }
    }

    public void resetBoard() {
        // Reset board with empty pieces
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = null;
            }
        }
    }

    // Tranform Tor and Xor pieces
    public void transformPieceAt(int x, int y) {
        if (isInBounds(x, y)) {
            Piece piece = getPieceAt(x, y);
            if (piece.getType().equals("Tor")) {
                board[y][x] = factory.createPiece("Xor", piece.getColor(), x, y);
            } else if (piece.getType().equals("Xor")) {
                board[y][x] = factory.createPiece("Xor", piece.getColor(), x, y);
            }
        }
    }

    // Keep track of Sau piece position
    public void setSauPosition(Position position, String color) {
        if (color.equals("RED")) {
            redSauPosition = position;
        } else {
            blueSauPosition = position;
        }
    }
    public Position getSauPosition(String color) {
        return color.equals("RED") ? redSauPosition : blueSauPosition;
    }

    // Check if the Sau piece has been captured
    public boolean isSauCaptured(String color) {
        int x, y;
        if (color.equals("RED")) {
            x = redSauPosition.getX();
            y = redSauPosition.getY();
        } else {
            x = blueSauPosition.getX();
            y = blueSauPosition.getY();
        }
        return board[y][x] == null;
    }

    public void setWinner(String w) {
        winner = w;
    }

    // Save board state to a file
    // Load board state from a file

    public String determineWinConditions() {
        if ()
    }
}
