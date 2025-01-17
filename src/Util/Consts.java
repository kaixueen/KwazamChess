package Util;

import java.awt.*;

// @author NG KAI XUEN
// This class contains all the constants used in the game for ease of access and modification
public final class Consts {
    // Constructor
    private Consts(){
        //this prevents even the native class from
        //calling this ctor as well :
        throw new AssertionError();
    }

    public static final int ROWS = 8;
    public static final int COLUMNS = 5;
    public static final int TURN_LIMIT = 100;

    public static final int HEADER_HEIGHT = 70;
    public static final int FOOTER_HEIGHT = 70;
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;

    public static String PLAYER1 = "BLUE";
    public static String PLAYER2 = "RED";

    public static final Color BLUE_TURN_HOVER = new Color(64, 223, 239);
    public static final Color RED_TURN_HOVER = new Color(231, 142, 169);
    public static final Color BLUE_TURN_POSSIBLE_MOVE = new Color(205, 193, 255);
    public static final Color RED_TURN_POSSIBLE_MOVE = new Color(243, 158, 96);

    // Image path that will changed according to the location of the image
    public static final String IMAGE_PATH="src/Images/";

    public static final Font TITLE_FONT = new Font("Lucida Calligraphy", Font.BOLD, 50);
    public static final Font TURN_FONT = new Font("Lucida Calligraphy", Font.BOLD, 30);
    public static final Font MENU_FONT = new Font("Lucida Calligraphy", Font.BOLD, 20);
}
