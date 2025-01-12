package Model;

import Util.Position;

// Contain logic of piece creation
public class PieceFactory {
    private static PieceFactory instance;
    // Private constructor to prevent instantiation
    private PieceFactory() {
    }
    // Singleton pattern
    public static PieceFactory getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (PieceFactory.class) {
            if (instance == null) {
                instance = new PieceFactory();
            }
        }
        return instance;
    }
    // Create a piece based on the piece type, color, and position
    public static Piece createPiece(String pieceType, String color, int x, int y) {
        return switch (pieceType.toUpperCase()) {
            case "RAM" -> new Ram(color, new Position(x, y));
            case "BIZ" -> new Biz(color, new Position(x, y));
            case "TOR" -> new Tor(color, new Position(x, y));
            case "XOR" -> new Xor(color, new Position(x, y));
            case "SAU" -> new Sau(color, new Position(x, y));
            default -> throw new IllegalArgumentException("Invalid piece type: " + pieceType);
        };
    }
}