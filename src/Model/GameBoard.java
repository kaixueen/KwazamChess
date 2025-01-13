package Model;

import Util.Position;

import java.util.ArrayList;
import static Util.Consts.*;

// Manage game board
public class GameBoard {
    private PieceFactory factory;
    private Piece[][] board;
    private int currentTurn;
    private String currentPlayer;
    private boolean isGameOver;
    private Position redSauPosition, blueSauPosition;
    private int remainingRedPieces, remainingBluePieces;
    private ArrayList<Piece> selectedPieces;

    // Singleton instance
    private static GameBoard instance;
    public static GameBoard getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (PieceFactory.class) {
            if (instance == null) {
                instance = new GameBoard();
            }
        }
        return instance;
    }

    // Constructor
    private GameBoard() {
        board = new Piece[ROWS][COLUMNS];
        remainingBluePieces = remainingRedPieces = 10;
        factory = PieceFactory.getInstance();
        selectedPieces = new ArrayList<>();
        initializeBoard();
    }

    // Initialize the board with pieces
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
            board[ROWS - 1][col] = factory.createPiece(firstRowPieces[COLUMNS-col-1], "BLUE", col, ROWS - 1);
        }

        redSauPosition = new Position(2, 0);
        blueSauPosition = new Position(2, ROWS - 1);
        currentTurn = 1;
        currentPlayer = PLAYER1;
        isGameOver = false;
    }

    // Getters methods
    public String getCurrentPlayer() {
        return currentPlayer;
    }
    public boolean isGameOver() {
        return isGameOver;
    }
    public int getCurrentTurn() {
        return currentTurn;
    }
    public ArrayList<Piece> getSelectedPieces() {
        return selectedPieces;
    }

    // Check if a position is empty
    public boolean isEmpty(int x, int y) {
        if (!isInBounds(x, y)) {
            return false; // Out of bounds
        }
        return board[y][x] == null;
    }

    // Check if a position is within the board
    public boolean isInBounds(int x, int y) {
        return y >= 0 && y < ROWS && x >= 0 && x < COLUMNS;
    }

    // Get piece at a certain position
    public Piece getPieceAt(Position position) {
        int x = position.getX();
        int y = position.getY();
        if (isInBounds(x, y) && !isEmpty(x, y)) {
            return board[y][x];
        }
        return null;
    }
    // Remove piece at a certain position
    public void removePieceAt(int x, int y) {
        if (isInBounds(x, y)) {
            if (board[y][x].getColor().equals("RED")) {
                remainingRedPieces--;
            } else {
                remainingBluePieces--;
            }
            board[y][x] = null;
        }
    }
    // Move piece to a certain position
    public void movePieceTo(Position from, Position to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();
        if (isInBounds(fromX, fromY) && isInBounds(toX, toY)) {
            Piece piece = getPieceAt(from);
            if (isEmpty(toX, toY) && piece != null) {
                board[toY][toX] = piece;
                board[fromY][fromX] = null;

                piece.setPosition(to);
                // Check if the piece is a Sau (for determining game over)
                if (piece.getType().equals("SAU")) {
                    setSauPosition(to, piece.getColor());
                }
                // Check if the piece is a Ram (to detect the moving direction)
                if (piece.getType().equals("RAM") && ((toY == 0 && piece.getColor().equals("BLUE")) || (toY == ROWS - 1 && piece.getColor().equals("RED")))) {
                    piece.setMovingForward(false);
                    piece.setIconNeedToFlip(true);
                } else if (piece.getType().equals("RAM") && ((toY == ROWS - 1 && piece.getColor().equals("BLUE")) || (toY == 0 && piece.getColor().equals("RED")))) {
                    piece.setMovingForward(true);
                    piece.setIconNeedToFlip(true);
                }
            }
        }
    }
    // Capture piece at a certain position
    public void capturePiece(Position from, Position to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();
        if (isInBounds(fromX, fromY) && isInBounds(toX, toY)) {
            removePieceAt(toX, toY);
            movePieceTo(from, to);
        }
    }

    // List all valid moves for a piece
    public ArrayList<Position> getPossibleMoves(Piece piece) {
        ArrayList<Position> validMoves = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (isInBounds(col, row) && piece.isValidMove(this, new Position(col, row), currentPlayer)) {
                    // Add to list of valid moves
                    validMoves.add(new Position(col, row));
                }
            }
        }
        return validMoves;
    }

    // Check if the straight path is blocked
    public boolean isStraightPathBlocked(Position from, Position to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        // Horizontal movement (same row)
        if (fromY == toY) {
            int step = (toX > fromX) ? 1 : -1; // Determine direction
            for (int x = fromX + step; x != toX; x += step) {
                if (!isEmpty(x, fromY)) {
                    return true; // Path is blocked
                }
            }
        }
        // Vertical movement (same column)
        else if (fromX == toX) {
            int step = (toY > fromY) ? 1 : -1; // Determine direction
            for (int y = fromY + step; y != toY; y += step) {
                if (!isEmpty(fromX, y)) {
                    return true; // Path is blocked
                }
            }
        }
        return false;
    }

    // Check if the diagonal path is blocked
    public boolean isDiagonalPathBlocked(Position from, Position to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        int stepX = (toX > fromX) ? 1 : -1; // Determine horizontal direction
        int stepY = (toY > fromY) ? 1 : -1; // Determine vertical direction

        int x = fromX + stepX;
        int y = fromY + stepY;

        while (x != toX && y != toY) {
            if (!isEmpty(x, y)) {
                return true; // Path is blocked
            }
            x += stepX;
            y += stepY;
        }
        return false;
    }

    // Logic for transforming Tor and Xor pieces
    // Get all Tor and Xor pieces
    public ArrayList<Position> getTorXorPosition() {
        ArrayList<Position> transformedPieces = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] != null && (board[row][col].getType().equals("TOR") || board[row][col].getType().equals("XOR"))) {
                    transformedPieces.add(new Position(col, row));
                }
            }
        }
        return transformedPieces;
    }

    // Tranform Tor and Xor pieces
    public void transformPieceAt(Position position) {
        int x = position.getX();
        int y = position.getY();
        if (isInBounds(x, y)) {
            Piece piece = getPieceAt(position);
            if (piece.getType().equals("TOR")) {
                board[y][x] = factory.createPiece("XOR", piece.getColor(), x, y);
            } else if (piece.getType().equals("XOR")) {
                board[y][x] = factory.createPiece("TOR", piece.getColor(), x, y);
            }
        }
    }

    // Reset board with empty pieces
    public void resetBoard() {
        selectedPieces.clear();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = null;
            }
        }
    }

    // Logic for determining the winner
    // Keep track of Sau piece position
    public void setSauPosition(Position position, String color) {
        if (color.equals("RED")) {
            redSauPosition = position;
        } else {
            blueSauPosition = position;
        }
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
        return !board[y][x].getType().equals("SAU") || !board[y][x].getColor().equals(color);
    }

    // Determine the winner based on the win conditions
    public String determineWinConditions() {
        isGameOver = true;
        if (currentTurn >= TURN_LIMIT) {
            if (remainingRedPieces > remainingBluePieces) {
                return "RED";
            } else if (remainingBluePieces > remainingRedPieces) {
                return "BLUE";
            } else {
                return "DRAW";
            }
        }
        if (isSauCaptured("RED")) {
            return "BLUE";
        } else if (isSauCaptured("BLUE")) {
            return "RED";
        }

        isGameOver = false;
        return "";
    }

    // Switch turns between players
    public void switchTurn() {
        currentPlayer = currentPlayer.equals(PLAYER1) ? PLAYER2 : PLAYER1;
        currentTurn++;
    }

    //Load and save board state
    // Save board state as a 2D String array
    public ArrayList<String> saveBoardState() {
        ArrayList<String> boardState = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (isEmpty(col, row)) {
                    boardState.add("____ ");
                } else {
                    // Store piece as RRAM, BSAU, etc.
                    boardState.add(board[row][col].getColor().charAt(0) + board[row][col].getType() + " ");
                }
            }
            boardState.add("\n");
        }
        boardState.add("Current Turn: " + currentTurn + "\n");
        boardState.add("Current Player: " + currentPlayer + "\n");
        return boardState;
    }

    // Load board state from a file
    public void loadBoardState(String[][] boardState) {
        resetBoard();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (!boardState[row][col].equals("____")) {
                    String color = boardState[row][col].substring(0, 1).equals("R") ? "RED" : "BLUE";
                    String type = boardState[row][col].substring(1);
                    board[row][col] = factory.createPiece(type, color, col, row);
                    if (boardState[row][col].equals("RSAU") || boardState[row][col].equals("BSAU")) {
                        setSauPosition(new Position(col, row), color);
                    }
                }
            }
        }
        currentTurn = Integer.parseInt(boardState[boardState.length - 2][2]);
        currentPlayer = boardState[boardState.length - 1][2];
    }
}
