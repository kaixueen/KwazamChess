package Model;

import java.awt.*;

// @author PHANG JUN YUAN
public class Biz extends Piece {
    // Constructor
    public Biz(String color, Point position) {
        super(color, "BIZ", position);
    }

    // Determine if a piece can move to a certain position
    @Override
    public boolean isValidMove(GameBoard board, Point to, String player) {
        int fromX = (int) getPosition().getX();
        int fromY = (int) getPosition().getY();
        int toX = (int) to.getX();
        int toY = (int) to.getY();
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);

        // Ensure only L-shape move
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2))  {
            // Ensure the destination position is empty or contains an opponent's piece
            if (board.isEmpty(toX, toY)) {
                return true;
            } else if (!board.isEmpty(toX, toY) && !board.getPieceAt(new Point(toX, toY)).getColor().equals(player)) {
                return true;
            }
        }
        return false;
    }
}
