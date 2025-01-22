package Model;

import java.awt.*;

// @author PHANG JUN YUAN, NG KAI XUEN
public class Ram extends Piece {
    // Constructor
    public Ram(String color, Point position) {
        super(color, "RAM", position);
    }

    // Determine if a piece can move to a certain position
    @Override
    public boolean isValidMove(GameBoard board, Point to, String player) {
        int fromX = (int) getPosition().getX();
        int fromY = (int) getPosition().getY();
        int toX = (int) to.getX();
        int toY = (int) to.getY();

        // Ensure the vertical distance between the two positions is 1
        if ((((toY - fromY == -1 && toX == fromX && isMovingForward()) || (toY - fromY == 1 && toX == fromX && !isMovingForward()))
                && player.equals("BLUE")) ||
                (((toY - fromY == 1 && toX == fromX && isMovingForward()) || (toY - fromY == -1 && toX == fromX && !isMovingForward()))
                        && player.equals("RED"))){
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
