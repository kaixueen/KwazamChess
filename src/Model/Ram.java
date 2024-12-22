package Model;

public class Ram extends Piece {
    public Ram(String color, int x, int y) {
        super(color, "Model.Ram", x, y);
    }

    @Override
    public boolean isValidMove(GameBoard board, int toX, int toY) {
        int fromX = getPositionX();
        int currentY = getPositionY();
        String currentColor = getColor();
        int step = toX-fromX;

        //Check if it's the same location.
        if (fromX == toX && currentY == toY){
            return false;
        }

        if (currentColor == "red"){
            if(fromX == 7){
                if (step == -1 || currentY == toY) {
                    move(toX, toY);
                    return true;
                }
            }
            if (step == 1 || currentY == toY){
                move(toX, toY);
                return true;
            }

            //Capture not done yet

        }

        if (currentColor == "blue"){
            if(fromX == 7){
                if (step == 1 || currentY == toY) {
                    move(toX, toY);
                    return true;
                }
            }
            if (step == -1 || currentY == toY){
                move(toX, toY);
                return true;
            }

            //Capture not done yet

        }
        return false;
    }
}
