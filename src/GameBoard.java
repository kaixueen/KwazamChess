// Manage game board
public class GameBoard {
    public static final int ROWS = 8;
    public static final int COLUMNS = 5;
    
    private Piece[][] board;
    private PieceFactory factory;

    public GameBoard() {
        board = new Piece[ROWS][COLUMNS];
        factory = new PieceFactory();

        // Initialize board with empty pieces
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = null;
            }
        }

        // Initialize board with RAM pieces
        for (int col = 0; col < COLUMNS; col++) {
            board[1][col] = factory.createPiece("RAM", "RED");
            board[ROWS - 2][col] = factory.createPiece("RAM", "BLUE");
        }

        // Initialize board with other pieces
        String[] firstRowPieces = "TOR BIZ SAU BIZ XOR".split(" ");
        for (int col = 0; col < COLUMNS; col++) {
            board[0][col] = factory.createPiece(firstRowPieces[col], "RED");
            board[ROWS - 1][col] = factory.createPiece(firstRowPieces[col], "BLUE");
        }


    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public boolean isEmpty(int row, int col) {
        return getPieceAt(row, col) == null;
    }

    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
    }

    public Piece getPieceAt(int row, int col) {
        if (isInBounds(row, col) && !isEmpty(row, col)) {
            Piece piece = board[row][col];
            return piece;
        }
        return null;
    }

    public void removePieceAt(int row, int col) {
        if (isInBounds(row, col)) {
            board[row][col] = null;
        }
    }

    public void movePieceTo(int fromRow, int fromCol, int toRow, int toCol) {
        if (isInBounds(fromRow, fromCol) && isInBounds(toRow, toCol)) {
            Piece piece = getPieceAt(fromRow, fromCol);
            if (isEmpty(toRow, toCol) && piece != null) {
                board[toRow][toCol] = piece;
            }
        }
    }

    public void resetBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = null;
            }
        }
    }
}
