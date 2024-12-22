package Model;

public class Sau extends Piece {
    public Sau(String color, Position position) {
        super(color, "Ram", position);
    }

    @Override
    public boolean isValidMove(GameBoard board, Position to) {
        int fromX = getPosition().getX();
        int fromY = getPosition().getY();
        int toX = to.getX();
        int toY = to.getY();
        int dx = Math.abs(fromX - toX);
        int dy = Math.abs(fromY - toY);
        String currentColor = getColor();

        // Ensure the distance between the two positions is 1
        if ((dx == 1 && dy == 1) || (dx == 0 && dy == 1) || (dx == 1 && dy == 0)) {
            // Ensure the destination position is empty or contains an opponent's piece
            if (board.isEmpty(toX, toY)) {
                return true;
            } else if (!board.isEmpty(toX, toY) && !board.getPieceAt(toX, toY).getColor().equals(currentColor)) {
                return true;
            }
        }
        return false;
    }
}