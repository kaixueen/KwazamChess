package Model;

public class Sau extends Piece {
    public Sau(String color, int x, int y) {
        super(color, "Model.Sau", x, y);
    }

    @Override
    public boolean isValidMove(GameBoard board, int toX, int toY) {
        return false;
    }
}
