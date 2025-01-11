package Model;

// Contain logic of piece creation
public class PieceFactory {
    private static PieceFactory instance;

    private PieceFactory() {
    }

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