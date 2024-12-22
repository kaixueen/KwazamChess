package Model;

public class Tor extends Piece{
    public Tor(String color, Position position) {
        super(color, "Tor", position);
    }

    @Override
    public boolean isValidMove(GameBoard board, Position to) {
        int fromX = getPosition().getX();
        int fromY = getPosition().getY();
        int toX = to.getX();
        int toY = to.getY();
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        String currentColor = getColor();

        // Ensure orthogonal move
        if (!board.isStraightPathBlocked(getPosition(), to) && (dx == 0 && dy != 0) || (dy == 0 && dx != 0)) {
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
