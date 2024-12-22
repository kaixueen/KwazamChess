package Model;

public class Ram extends Piece {
    public Ram(String color, Position position) {
        super(color, "Ram", position);
    }

    @Override
    public boolean isValidMove(GameBoard board, Position to) {
        int fromX = getPosition().getX();
        int fromY = getPosition().getY();
        int toX = to.getX();
        int toY = to.getY();
        String currentColor = getColor();
        if (fromY == 0 || fromY == board.ROWS - 1) {
            setMovingForward(!isMovingForward());
        }

        // Ensure the vertical distance between the two positions is 1
        if ((toY - fromY == 1 && toX == fromX && isMovingForward()) ||
                (toY - fromY == -1 && toX == fromX && !isMovingForward())) {
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
