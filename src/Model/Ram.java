package Model;

import static Model.GameBoard.ROWS;

public class Ram extends Piece {
    public Ram(String color, Position position) {
        super(color, "RAM", position);
    }

    @Override
    public boolean isValidMove(GameBoard board, Position to, String player) {
        int fromX = getPosition().getX();
        int fromY = getPosition().getY();
        int toX = to.getX();
        int toY = to.getY();
        String currentColor = getColor();

        // Ensure the vertical distance between the two positions is 1
        if ((((toY - fromY == -1 && toX == fromX && isMovingForward()) || (toY - fromY == 1 && toX == fromX && !isMovingForward()))
                && player.equals("BLUE")) ||
                (((toY - fromY == 1 && toX == fromX && isMovingForward()) || (toY - fromY == -1 && toX == fromX && !isMovingForward()))
                        && player.equals("RED"))){
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
