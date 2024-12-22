public class Tor extends Piece{
    public Tor(String color, int x, int y) {
        super(color, "Tor", x, y);
    }

    @Override
    public boolean isValidMove(GameBoard board, int toX, int toY) {
        return false;
    }
}
