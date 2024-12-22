package Model;

// Contain logic of piece creation
public class PieceFactory {
    public static Piece createPiece(String pieceType, String color, int x, int y) {
        switch (pieceType.toLowerCase()) {
            case "Model.Ram":
                return new Ram(color, x, y);
            case "Model.Biz":
                return new Biz(color, x, y);
            case "Model.Tor":
                return new Tor(color, x, y);
            case "Model.Xor":
                return new Xor(color, x, y);
            case "Model.Sau":
                return new Sau(color, x, y);
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pieceType);
        }
    }
}