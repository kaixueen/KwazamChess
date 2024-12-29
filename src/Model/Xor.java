package Model;

public class Xor extends Piece {
    public Xor(String color, Position position) {
        super(color, "XOR", position);
    }

    @Override
    public boolean isValidMove(GameBoard board, Position to, String player) {
        int fromX = getPosition().getX();
        int fromY = getPosition().getY();
        int toX = to.getX();
        int toY = to.getY();
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        String currentColor = getColor();

        // Ensure orthogonal move
        if (!board.isDiagonalPathBlocked(getPosition(), to) && (dx == dy) && (dx != 0 && dy != 0)) {
            // Ensure the destination position is empty or contains an opponent's piece
            if (board.isEmpty(toX, toY)) {
                return true;
            } else if (!board.isEmpty(toX, toY) && !board.getPieceAt(new Position(toX, toY)).getColor().equals(currentColor)) {
                return true;
            }
        }
        return false;
    }
}
