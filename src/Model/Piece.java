package Model;

import java.awt.*;

// @author PHANG JUN YUAN
// Abstract class for all pieces
public abstract class Piece {
    private String color;
    private String type;
    private Point position;
    private boolean isMovingForward; // Only for Ram
    private boolean isIconNeedToFlip; // Only for Ram

    // Constructor
    public Piece(String color, String type, Point position) {
        this.color = color;
        this.type = type;
        this.position = position;
        isMovingForward = true;
        isIconNeedToFlip = false;
    }

    // Determine if a piece can move to a certain position
    // Abstract method to be implemented by subclasses
    // Polymorphism
    public abstract boolean isValidMove(GameBoard board, Point to, String player);

    // Return a string representation of the piece
    public String toString() {
        return color.charAt(0) + type;
    }

    // Setter methods
    public void setMovingForward(boolean isMovingForward) {
        this.isMovingForward = isMovingForward;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
    public void setIconNeedToFlip(boolean isIconNeedToFlip) {
        this.isIconNeedToFlip = isIconNeedToFlip;
    }

    // Getter methods
    public String getColor() {
        return color;
    }
    public String getType() {
        return type;
    }
    public Point getPosition() {
        return position;
    }
    public boolean isMovingForward() {
        return isMovingForward;
    }
    public boolean isIconNeedToFlip() {
        return isIconNeedToFlip;
    }
}
