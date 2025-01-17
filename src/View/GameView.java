package View;


import Util.Position;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Util.Consts.*;

// @author WOON WEN TAO, NG KAI XUEN
// This class is responsible for the game view once user runs the program
// Manage game GUI (board, pieces)

public class GameView {
    private JFrame frame = new JFrame("Kwazam Chess");
    private JPanel headerPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel boardPanel = new JPanel();
    private JPanel footerPanel = new JPanel(new BorderLayout());

    private JButton menu;
    private JLabel turnLabel;
    private JLabel turnNumberLabel;
    private JButton[][] board = new JButton[ROWS][COLUMNS];

    private boolean isFlipped;
    private boolean isEnlarged;
    private Position enlargedPosition;

    // @author WOON WEN TAO
    // Constructor
    public GameView() {
        frame.setVisible(true);
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JLabel textLabel = new JLabel();
        textLabel.setBackground(Color.BLACK);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(TITLE_FONT);
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Kwazam Chess");
        textLabel.setOpaque(true);

        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(BOARD_WIDTH, HEADER_HEIGHT));
        headerPanel.add(textLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Center Panel to encapsulate the Board Panel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 200, 10, 200));
        frame.add(centerPanel, BorderLayout.CENTER);

        // Board Panel
        boardPanel.setLayout(new GridLayout(ROWS, COLUMNS, 1, 1));
        int buttonSize = 640 / ROWS; // Ensure buttons are square
        boardPanel.setPreferredSize(new Dimension(buttonSize * COLUMNS - COLUMNS, buttonSize * ROWS - ROWS));
        boardPanel.setBackground(Color.BLACK);
        centerPanel.add(boardPanel, BorderLayout.CENTER);
        centerPanel.setBackground(Color.darkGray);

        // Create the board with alternating colors
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                JButton square = new JButton();
                square.setPreferredSize(new Dimension(buttonSize, buttonSize));
                if ((r + c) % 2 == 0) {
                    square.setBackground(new Color(236, 235, 222));
                } else {
                    square.setBackground(new Color(168, 205, 137));
                }
                board[r][c] = square;
                boardPanel.add(square);
            }
        }

        // Footer Panel
        menu = new JButton("Menu");
        menu.setFont(MENU_FONT);
        menu.setBackground(Color.WHITE);

        // To make the button has margin and not too close to the edge
        JPanel menuWrapper = new JPanel(new BorderLayout());
        menuWrapper.setBackground(Color.BLACK);
        menuWrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add margin to the wrapper
        menuWrapper.add(menu, BorderLayout.CENTER);
        footerPanel.add(menuWrapper, BorderLayout.WEST);

        // Turn label
        turnNumberLabel = new JLabel("Turn: 1");
        turnNumberLabel.setFont(TURN_FONT);
        turnNumberLabel.setForeground(Color.WHITE);
        turnNumberLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footerPanel.add(turnNumberLabel, BorderLayout.CENTER);

        turnLabel = new JLabel("Now is Blue Turn!");
        turnLabel.setForeground(Color.BLUE);
        turnLabel.setFont(TURN_FONT);
        turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footerPanel.add(turnLabel, BorderLayout.EAST);
        footerPanel.setPreferredSize(new Dimension(BOARD_WIDTH, FOOTER_HEIGHT));
        footerPanel.setBackground(Color.BLACK);
        frame.add(footerPanel, BorderLayout.SOUTH);

        initPosition();
    }

    // @author WOON WEN TAO
    // Initialize the board with pieces
    public void initPosition() {
        turnLabel.setText("Now is Blue Turn!");
        turnLabel.setForeground(Color.BLUE);
        isFlipped = false;
        isEnlarged = false;
        enlargedPosition = new Position(-1, -1);

        // Initialize board with RAM pieces
        for (int col = 0; col < COLUMNS; col++) {
            setIcon(board[1][col], "RRAM");
            setIcon(board[ROWS-2][col], "BRAM");
        }

        // Initialize board with other pieces
        String[] firstRowPieces = "TOR BIZ SAU BIZ XOR".split(" ");
        for (int col = 0; col < COLUMNS; col++) {
            setIcon(board[0][col], "R" + firstRowPieces[col]);
            setIcon(board[ROWS-1][col], "B" + firstRowPieces[COLUMNS-col-1]);
        }
    }

    // @author WOON WEN TAO
    // Set the icon of a button based on the description
    public void setIcon(JButton button, String description) {
        if (description.equals("null")) {
            button.setIcon(null);
            button.setActionCommand(null);
            return;
        }
        ImageIcon icon = new ImageIcon(new ImageIcon(IMAGE_PATH + description + ".png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        button.setIcon(icon);
        button.setActionCommand(description);
    }

    // @author WOON WEN TAO
    // Move a piece from one position to another
    public void movePiece(Position from, Position to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        board[toY][toX].setIcon(board[fromY][fromX].getIcon());
        board[toY][toX].setActionCommand(board[fromY][fromX].getActionCommand());

        setIcon(board[fromY][fromX], "null");
    }

    // @author WOON WEN TAO
    // Flip the board
    public void flipBoard() {
        boardPanel.removeAll(); // Clear the board panel

        if (!isFlipped) {
            // Flip: Display bottom-to-top and right-to-left
            for (int r = ROWS-1; r >= 0; r--) {
                for (int c = COLUMNS-1; c >= 0; c--) {
                    rotateIcon(new Position(c, r));
                    boardPanel.add(board[r][c]); // Add buttons in flipped order
                }
            }
            isFlipped = true; // Mark board as flipped
        } else {
            // Restore: Display top-to-bottom and left-to-right
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLUMNS; c++) {
                    rotateIcon(new Position(c, r));
                    boardPanel.add(board[r][c]); // Add buttons in original order
                }
            }
            isFlipped = false; // Mark board as restored
        }

        boardPanel.revalidate(); // Refresh the layout
        boardPanel.repaint();    // Repaint the panel
    }
    // Check if the board is flipped
    public boolean isFlipped() {
        return isFlipped;
    }

    // @author WOON WEN TAO
    // Rotate the icon of a button by 180 degrees
    public void rotateIcon(Position position){
        int x = position.getX();
        int y = position.getY();
        ImageIcon icon = (ImageIcon) board[y][x].getIcon();;
        if (icon == null) {
            return;
        }
        Image image = icon.getImage();
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage rotated = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();

        // rotate 180 degrees
        g2d.rotate(Math.PI, width / 2, height / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        board[y][x].setIcon(new ImageIcon(rotated));
    }

    // @author WOON WEN TAO
    // Update the turn label
    public void updateTurn(String player, int turn) {
        turnLabel.setText("Now is " + player + " Turn!");
        turnLabel.setForeground(player.equals("RED") ? Color.RED : Color.BLUE);
        turnNumberLabel.setText("Turn: " + turn);
    }

    // @author WOON WEN TAO
    // Clear the board
    public void clearBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                setIcon(board[i][j], "null");
            }
        }
        turnNumberLabel.setText("Turn: 1");
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    // @author NG KAI XUEN
    public void transformPieceAt(Position position) {
        int x = position.getX();
        int y = position.getY();
        String icon = board[y][x].getActionCommand();

        if (icon.equals("RTOR")) {
            setIcon(board[y][x], "RXOR");
        } else if (icon.equals("RXOR")) {
            setIcon(board[y][x], "RTOR");
        } else if (icon.equals("BTOR")) {
            setIcon(board[y][x], "BXOR");
        } else if (icon.equals("BXOR")) {
            setIcon(board[y][x], "BTOR");
        }
    }

    // @author NG KAI XUEN
    // Find the position of a button
    public Position findButtonPosition(JButton btn) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j] == btn) {
                    return new Position(j, i);
                }
            }
        }
        return null;
    }

    // @author NG KAI XUEN
    // Highlight the hovered piece
    public void pieceOnHover(Position position, String player) {
        int x = position.getX();
        int y = position.getY();
        JButton square = board[y][x];
        Color hoverColor = player.equals("RED") ? RED_TURN_HOVER : BLUE_TURN_HOVER;
        square.setBackground(hoverColor);
    }
    // Unhighlight the hovered piece
    public void pieceOffHover(Position position) {
        int x = position.getX();
        int y = position.getY();
        JButton square = board[y][x];
        if ((x + y) % 2 == 0) {
            square.setBackground(new Color(236, 235, 222));
        } else {
            square.setBackground(new Color(168, 205, 137));
        }
    }

    // @author WOON WEN TAO
    // Enlarge the clicked piece
    public void pieceOnClick(Position position) {
        int x = position.getX();
        int y = position.getY();
        JButton square = board[y][x];

        // Check if there's a previously enlarged button
        if (isEnlarged) {
            JButton enlargedButton = board[enlargedPosition.getY()][enlargedPosition.getX()];
            ImageIcon icon = (ImageIcon) enlargedButton.getIcon();
            if (icon != null) {
                // Restore original size
                Image originalImage = icon.getImage();
                Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                enlargedButton.setIcon(new ImageIcon(resizedImage));
            }
            isEnlarged = false;

            // If clicking the same button, return without enlarging again
            if (enlargedPosition.getX() == x && enlargedPosition.getY() == y) {
                enlargedPosition.setX(-1);
                enlargedPosition.setY(-1);
                return;
            }
        }

        // Enlarge the clicked button
        ImageIcon icon = (ImageIcon) square.getIcon();
        if (icon != null) {
            Image image = icon.getImage();
            Image enlargedImage = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Enlarge by 10px
            square.setIcon(new ImageIcon(enlargedImage));
            isEnlarged = true;
            enlargedPosition.setX(x);
            enlargedPosition.setY(y);
        }
    }

    // @author WOON WEN TAO
    // Add listeners to the piece
    public void addPieceListener(MouseAdapter listener) {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j].addMouseListener(listener);
            }
        }
    }
    // Remove listeners from the piece
    public void removePieceListener() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                JButton button = board[i][j];
                // Iterate over all listeners attached to the button
                for (MouseListener listener : button.getMouseListeners()) {
                    if (listener instanceof MouseAdapter) {
                        button.removeMouseListener(listener); // Remove only PieceListener
                    }
                }
            }
        }
    }

    // @author NG KAI XUEN
    // Highlight the possible moves
    public void highlightPossibleMoves(ArrayList<Position> possibleMoves, String player) {
        for (Position pos : possibleMoves) {
            int x = pos.getX();
            int y = pos.getY();
            JButton square = board[y][x];
            Color possibleMoveColor = player.equals("RED") ? RED_TURN_POSSIBLE_MOVE : BLUE_TURN_POSSIBLE_MOVE;
            square.setBackground(possibleMoveColor);
        }
    }
    // Unhighlight the possible moves
    public void unhighlightPossibleMoves(ArrayList<Position> possibleMoves) {
        for (Position pos : possibleMoves) {
            int x = pos.getX();
            int y = pos.getY();
            JButton square = board[y][x];
            if ((x + y) % 2 == 0) {
                square.setBackground(new Color(236, 235, 222));
            } else {
                square.setBackground(new Color(168, 205, 137));
            }
        }
    }

    // @author WOON WEN TAO
    // Add listener to the possible moves of a selected piece
    public void addMoveListener(ArrayList<Position> positions, MouseAdapter newListener) {
        for (Position position : positions) {
            int x = position.getX();
            int y = position.getY();
            JButton button = board[y][x];

            // Remove all MoveListener instances
            for (MouseListener listener : button.getMouseListeners()) {
                button.removeMouseListener(listener);
            }
            // Add the new listener
            button.addMouseListener(newListener);
        }
    }
    // Remove listener from the possible moves of a selected piece
    public void removeMoveListener(ArrayList<Position> positions, MouseAdapter oriListener) {
        for (Position position : positions) {
            int x = position.getX();
            int y = position.getY();
            JButton button = board[y][x];

            // Remove all MoveListener instances
            for (MouseListener listener : button.getMouseListeners()) {
                button.removeMouseListener(listener);
            }
            // Add the new listener
            board[y][x].addMouseListener(oriListener);
        }
    }

    // @author WOON WEN TAO
    // Add listener to the menu button
    public void addMenuListener(ActionListener listener) {
        menu.addActionListener(listener);
    }
    public void clickMenu(ActionListener slistener, ActionListener llistener, ActionListener rlistener) {
        MenuView menu = new MenuView();
        menu.addButtonsListener(slistener, llistener, rlistener);
    }

    // @author WOON WEN TAO
    // Load the game state to the board
    public void loadGame(String[][] state) {
        clearBoard();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (state[i][j].equals("____")) {
                    setIcon(board[i][j], "null");
                } else {
                    setIcon(board[i][j], state[i][j]);
                }
            }
        }
        turnNumberLabel.setText("Turn: " + state[state.length-2][2]);
        if (state[state.length-1][2].equals("RED")) {
            isFlipped = false;
            flipBoard();
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }
}
