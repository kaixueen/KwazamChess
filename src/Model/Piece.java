package Model;

// Abstract class for all pieces
public abstract class Piece {
    private String color;
    private String type;
    // private Move moves[];
    private Position position;
    private boolean isMovingForward; // Only for Ram
    private boolean isTransformable; // Only for Tor and Xor

    // Constructor
    public Piece(String color, String type, Position position) {
        this.color = color;
        this.type = type;
        this.position = position;
        isMovingForward = true;
    }

    // Getter methods
    public String getColor() {
        return color;
    }
    public String getType() {
        return type;
    }
    public Position getPosition() {
        return position;
    }

    // Determine if a piece can move to a certain position
    public abstract boolean isValidMove(GameBoard board, Position to);

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

    public void setMovingForward(boolean isMovingForward) {
        this.isMovingForward = isMovingForward;
    }
    public boolean isMovingForward() {
        return isMovingForward;
    }

    public void setTransformable(boolean isTransformable) {
        this.isTransformable = isTransformable;
    }
    public boolean isTransformable() {
        return isTransformable;
    }
}
