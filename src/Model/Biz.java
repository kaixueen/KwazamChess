package Model;

public class Biz extends Piece {
    public Biz(String color, Position position) {
        super(color, "BIZ", position);
    }


    @Override
    public boolean isValidMove(GameBoard board, Position to, String player) {
        int fromX = getPosition().getX();
        int fromY = getPosition().getY();
        int toX = to.getX();
        int toY = to.getY();
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);

        // Ensure only L-shape move
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2))  {
            // Ensure the destination position is empty or contains an opponent's piece
            if (board.isEmpty(toX, toY)) {
                return true;
            } else if (!board.isEmpty(toX, toY) && !board.getPieceAt(new Position(toX, toY)).getColor().equals(player)) {
                return true;
            }
        }
        return false;
    }
}
