// Abstract class for all pieces
public class Piece {
    private String color;
    private String type;
    // private Move moves[];
    private int positionX;
    private int positionY;

    // Constructor
    public Piece(String color, String type, int positionX, int positionY) {
        this.color = color;
        this.type = type;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    // Getter methods
    public String getColor() {
        return color;
    }
    public String getType() {
        return type;
    }
    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }

    // Determine if a piece can move to a certain position
    public boolean isValidMove(GameBoard board, int toX, int toY) {
        return false;
    }

    // Return a string representation of the piece
    public String toString() {
        return color + " " + type;
    }

    // Move the piece to a new position
    public void move(int toX, int toY) {
        return;
    }

    // Capture a piece
    public void capture(Piece piece) {
        return;
    }
}
