package Model;

// Contain logic of piece creation
public class PieceFactory {
    public static Piece createPiece(String pieceType, String color, int x, int y) {
        switch (pieceType.toUpperCase()) {
            case "RAM":
                return new Ram(color, new Position(x, y));
            case "BIZ":
                return new Biz(color, new Position(x, y));
            case "TOR":
                return new Tor(color, new Position(x, y));
            case "XOR":
                return new Xor(color, new Position(x, y));
            case "SAU":
                return new Sau(color, new Position(x, y));
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pieceType);
        }
    }
}