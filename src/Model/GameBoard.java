package Model;

import java.util.ArrayList;

// Manage game board
public class GameBoard {
    public static final int ROWS = 8;
    public static final int COLUMNS = 5;
    private static final int TURN_LIMIT = 100;
    private PieceFactory factory;
    private Position redSauPosition, blueSauPosition;
    private int remainingRedPieces, remainingBluePieces;
    private String winner;

    private int currentTurn;
    private String currentPlayer;
    private boolean isGameOver;
    private String player1, player2;
    
    private Piece[][] board;

    public GameBoard() {
        board = new Piece[ROWS][COLUMNS];
        remainingBluePieces = remainingRedPieces = 10;
        factory = new PieceFactory();
        initializeBoard();
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
            board[ROWS - 1][col] = factory.createPiece(firstRowPieces[COLUMNS-col-1], "BLUE", col, ROWS - 1);
        }

        redSauPosition = new Position(2, 0);
        blueSauPosition = new Position(2, ROWS - 1);
        winner = "";
        currentTurn = 0;
        currentPlayer = player1 = "BLUE";
        player2 = "RED";
        isGameOver = false;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isEmpty(int x, int y) {
        if (!isInBounds(x, y)) {
            return false; // Out of bounds
        }
        return board[y][x] == null;
    }

    public boolean isInBounds(int x, int y) {
        return y >= 0 && y < ROWS && x >= 0 && x < COLUMNS;
    }

    public Piece getPieceAt(Position position) {
        int x = position.getX();
        int y = position.getY();
        if (isInBounds(x, y) && !isEmpty(x, y)) {
            Piece piece = board[y][x];
            return piece;
        }
        return null;
    }

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
                if (piece.getType().equals("SAU")) {
                    setSauPosition(to, piece.getColor());
                }
            }
        }
    }

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

    // Reset board with empty pieces
    public void resetBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = null;
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
        return !board[y][x].getType().equals("SAU");
    }

    public void setWinner(String w) {
        winner = w;
    }

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
                }
            }
        }
    }

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

    public boolean isGameOver() {
        return isGameOver;
    }

    // Switch turns between players
    public void switchTurn() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
        currentTurn++;
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

    public ArrayList<Position> transformPiece() {
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
}
