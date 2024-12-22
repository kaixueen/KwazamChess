package Model;

public class Biz extends Piece {
    public Biz(String color, int x, int y) {
        super(color, "Model.Biz", x, y);
    }

    @Override
    public boolean isValidMove(GameBoard board, int toX, int toY) {
        return false;
    }
}
