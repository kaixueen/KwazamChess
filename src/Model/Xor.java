package Model;

public class Xor extends Piece {
    public Xor(String color, int x, int y) {
        super(color, "Model.Xor", x, y);
    }

    @Override
    public boolean isValidMove(GameBoard board, int toX, int toY) {
        return false;
    }
}
