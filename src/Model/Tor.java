package Model;

import Util.Position;

public class Tor extends Piece{
    // Constructor
    public Tor(String color, Position position) {
        super(color, "TOR", position);
    }

    // Determine if a piece can move to a certain position
    @Override
    public boolean isValidMove(GameBoard board, Position to, String player) {
        int fromX = getPosition().getX();
        int fromY = getPosition().getY();
        int toX = to.getX();
        int toY = to.getY();
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);

        // Ensure orthogonal move
        if (!board.isStraightPathBlocked(getPosition(), to) && ((dx == 0 && dy != 0) || (dy == 0 && dx != 0))) {
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
