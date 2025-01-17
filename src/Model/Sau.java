package Model;

import Util.Position;

// @author PHANG JUN YUAN
public class Sau extends Piece {
    // Constructor
    public Sau(String color, Position position) {
        super(color, "SAU", position);
    }

    // Determine if a piece can move to a certain position
    @Override
    public boolean isValidMove(GameBoard board, Position to, String player) {
        int fromX = getPosition().getX();
        int fromY = getPosition().getY();
        int toX = to.getX();
        int toY = to.getY();
        int dx = Math.abs(fromX - toX);
        int dy = Math.abs(fromY - toY);

        // Ensure the distance between the two positions is 1
        if ((dx == 1 && dy == 1) || (dx == 0 && dy == 1) || (dx == 1 && dy == 0)) {
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