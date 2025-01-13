package Util;

// Position class to hold the x and y coordinates of a point
public class Position {
    private int x;
    private int y;

    // Constructor
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getter methods
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // Setter methods
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
