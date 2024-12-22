package Model;

// Contain logic of piece creation
public class PieceFactory {
    public static Piece createPiece(String pieceType, String color, int x, int y) {
        switch (pieceType.toLowerCase()) {
            case "Model.Ram":
                return new Ram(color, new Position(x, y));
            case "Model.Biz":
                return new Biz(color, new Position(x, y));
            case "Model.Tor":
                return new Tor(color, new Position(x, y));
            case "Model.Xor":
                return new Xor(color, new Position(x, y));
            case "Model.Sau":
                return new Sau(color, new Position(x, y));
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pieceType);
        }
    }
}