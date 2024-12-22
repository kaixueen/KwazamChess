// Contain logic of piece creation
public class PieceFactory {
    public static Piece createPiece(String pieceType, String color, int x, int y) {
        switch (pieceType.toLowerCase()) {
            case "Ram":
                return new Ram(color, x, y);
            case "Biz":
                return new Biz(color, x, y);
            case "Tor":
                return new Tor(color, x, y);
            case "Xor":
                return new Xor(color, x, y);
            case "Sau":
                return new Sau(color, x, y);
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pieceType);
        }
    }
}