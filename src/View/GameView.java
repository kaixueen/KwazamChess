package View;


import Model.Position;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// Manage game GUI (board, pieces)
public class GameView {
    final int headerHeight = 70;
    final int footerHeight = 70;
    final int boardWidth = 800;
    final int boardHeight = 800;
    final Color blueTurnHover = new Color(64, 223, 239);
    final Color redTurnHover = new Color(231, 142, 169);
    final Color redTurnPossibleMove = new Color(243, 158, 96);
    final Color blueTurnPossibleMove = new Color(205, 193, 255);

    private JFrame frame = new JFrame("Kwazam Chess");
    private JLabel textLabel = new JLabel();
    private JPanel headerPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel boardPanel = new JPanel();
    private JPanel footerPanel = new JPanel(new BorderLayout());
    private JPanel menuWrapper = new JPanel(new BorderLayout());
    private JButton menu;
    private JLabel turnLabel;
    private JButton[][] board = new JButton[8][5];
    public static final String IMAGE_PATH="src/Images/";

    private boolean isFlipped;
    private boolean isEnlarged;
    private int enlargedButtonX, enlargedButtonY;

    // Font setup
    public static final Font TITLE_FONT = new Font("Lucida Calligraphy", Font.BOLD, 50);
    public static final Font TURN_FONT = new Font("Lucida Calligraphy", Font.BOLD, 30);
    public static final Font MENU_FONT = new Font("Lucida Calligraphy", Font.BOLD, 20);

    // Constructor
    public GameView() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Header Panel
        textLabel.setBackground(Color.BLACK);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(TITLE_FONT);
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Kwazam Chess");
        textLabel.setOpaque(true);

        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(boardWidth, headerHeight));
        headerPanel.add(textLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Center Panel to encapsulate the Board Panel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 200, 10, 200));
        frame.add(centerPanel, BorderLayout.CENTER);

        // Board Panel
        boardPanel.setLayout(new GridLayout(8, 5, 1, 1));
        int buttonSize = 640 / 8; // Ensure buttons are square
        boardPanel.setPreferredSize(new Dimension(buttonSize * 5 - 5, buttonSize * 8 - 8));
        boardPanel.setBackground(Color.BLACK);
        centerPanel.add(boardPanel, BorderLayout.CENTER);
        centerPanel.setBackground(Color.darkGray);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 5; c++) {
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
        menuWrapper.setBackground(Color.BLACK);
        menuWrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add margin to the wrapper
        menuWrapper.add(menu, BorderLayout.CENTER);
        footerPanel.add(menuWrapper, BorderLayout.WEST);

        turnLabel = new JLabel("Now is Blue Turn!");
        turnLabel.setForeground(Color.BLUE);
        turnLabel.setFont(TURN_FONT);
        turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footerPanel.add(turnLabel, BorderLayout.EAST);
        footerPanel.setPreferredSize(new Dimension(boardWidth, footerHeight));
        footerPanel.setBackground(Color.BLACK);
        frame.add(footerPanel, BorderLayout.SOUTH);

        initPosition();
    }

    public void initPosition() {
        turnLabel.setText("Now is Blue Turn!");
        turnLabel.setForeground(Color.BLUE);
        isFlipped = false;
        isEnlarged = false;
        enlargedButtonX = enlargedButtonY = -1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                switch (i) {
                    case 0:
                        switch (j) {
                            case 0:
                                // board[i][j].setIcon(RedTor);
                                setIcon(board[i][j], "RTOR");
                                break;

                            case 1:
                                // board[i][j].setIcon(RedBiz);
                                setIcon(board[i][j], "RBIZ");
                                break;

                            case 2:
                                // board[i][j].setIcon(RedSau);
                                setIcon(board[i][j], "RSAU");
                                break;

                            case 3:
                                // board[i][j].setIcon(RedBiz);
                                setIcon(board[i][j], "RBIZ");
                                break;

                            case 4:
                                // board[i][j].setIcon(RedXor);
                                setIcon(board[i][j], "RXOR");
                                break;
                        }

                        break;

                    case 1:
                        // board[i][j].setIcon(RedRam);
                        setIcon(board[i][j], "RRAM");
                        break;

                    case 6:
                        // board[i][j].setIcon(BlueRam);
                        setIcon(board[i][j], "BRAM");
                        break;

                    case 7:
                        switch (j) {

                            case 0:
                                // board[i][j].setIcon(BlueXor);
                                setIcon(board[i][j], "BXOR");
                                break;

                            case 1:
                                // board[i][j].setIcon(BlueBiz);
                                setIcon(board[i][j], "BBIZ");
                                break;

                            case 2:
                                // board[i][j].setIcon(BlueSau);
                                setIcon(board[i][j], "BSAU");
                                break;

                            case 3:
                                // board[i][j].setIcon(BlueBiz);
                                setIcon(board[i][j], "BBIZ");
                                break;

                            case 4:
                                // board[i][j].setIcon(BlueTor);
                                setIcon(board[i][j], "BTOR");
                                break;
                        }
                        break;

                    default:
                        board[i][j].setIcon(null);
                        break;
                }
            }
        }
    }

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

    public void movePiece(Position from, Position to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        board[toY][toX].setIcon(board[fromY][fromX].getIcon());
        board[toY][toX].setActionCommand(board[fromY][fromX].getActionCommand());

        setIcon(board[fromY][fromX], "null");
    }

    public void flipBoard() {
        boardPanel.removeAll(); // Clear the board panel

        if (!isFlipped) {
            // Flip: Display bottom-to-top and right-to-left
            for (int r = 7; r >= 0; r--) {
                for (int c = 4; c >= 0; c--) {
                    board[r][c].setIcon(rotateIcon((ImageIcon) board[r][c].getIcon()));
                    boardPanel.add(board[r][c]); // Add buttons in flipped order
                }
            }
            isFlipped = true; // Mark board as flipped
        } else {
            // Restore: Display top-to-bottom and left-to-right
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 5; c++) {
                    board[r][c].setIcon(rotateIcon((ImageIcon) board[r][c].getIcon()));
                    boardPanel.add(board[r][c]); // Add buttons in original order
                }
            }
            isFlipped = false; // Mark board as restored
        }

        boardPanel.revalidate(); // Refresh the layout
        boardPanel.repaint();    // Repaint the panel
    }

    public ImageIcon rotateIcon(ImageIcon icon){
        if (icon == null) {
            return null;
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

        return new ImageIcon(rotated);
    }

    public void loadGame(String[][] state) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                switch (state[i][j]) {
                    case "RTOR":
                        setIcon(board[i][j], "RTOR");
                        break;
                    case "RBIZ":
                        setIcon(board[i][j], "RBIZ");
                        break;
                    case "RSAU":
                        setIcon(board[i][j], "RSAU");
                        break;
                    case "RRAM":
                        setIcon(board[i][j], "RRAM");
                        break;
                    case "RXOR":
                        setIcon(board[i][j], "RXOR");
                        break;
                    case "BTOR":
                        setIcon(board[i][j], "BTOR");
                        break;
                    case "BBIZ":
                        setIcon(board[i][j], "BBIZ");
                        break;
                    case "BSAU":
                        setIcon(board[i][j], "BSAU");
                        break;
                    case "BRAM":
                        setIcon(board[i][j], "BRAM");
                        break;
                    case "BXOR":
                        setIcon(board[i][j], "BXOR");
                        break;
                    default:
                        setIcon(board[i][j], "null");
                        break;
                }
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public void pieceOnHover(Position position, String player) {
        int x = position.getX();
        int y = position.getY();
        JButton square = board[y][x];
        Color hoverColor = player.equals("RED") ? redTurnHover : blueTurnHover;
        square.setBackground(hoverColor);
    }
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
    public void pieceOnClick(Position position) {
        int x = position.getX();
        int y = position.getY();
        JButton square = board[y][x];

        // Check if there's a previously enlarged button
        if (isEnlarged) {
            JButton enlargedButton = board[enlargedButtonY][enlargedButtonX];
            ImageIcon icon = (ImageIcon) enlargedButton.getIcon();
            if (icon != null) {
                // Restore original size
                Image originalImage = icon.getImage();
                Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                enlargedButton.setIcon(new ImageIcon(resizedImage));
            }
            isEnlarged = false;

            // If clicking the same button, return without enlarging again
            if (enlargedButtonX == x && enlargedButtonY == y) {
                enlargedButtonX = -1;
                enlargedButtonY = -1;
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
            enlargedButtonX = x;
            enlargedButtonY = y;
        }
    }

    public void updateTurn(String player) {
        turnLabel.setText("Now is " + player + " Turn!");
        turnLabel.setForeground(player.equals("RED") ? Color.RED : Color.BLUE);
    }

    public void addPieceListener(MouseAdapter listener) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j].addMouseListener(listener);
            }
        }
    }

    public void removePieceListener() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
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

    public void addMenuListener(ActionListener listener) {
        menu.addActionListener(listener);
    }

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

    public void highlightPossibleMoves(ArrayList<Position> possibleMoves, String player) {
        for (Position pos : possibleMoves) {
            int x = pos.getX();
            int y = pos.getY();
            JButton square = board[y][x];
            Color possibleMoveColor = player.equals("RED") ? redTurnPossibleMove : blueTurnPossibleMove;
            square.setBackground(possibleMoveColor);
        }
    }
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


    public void removeMoveListener(ArrayList<Position> positions, MouseAdapter oriListener) {
        for (Position position : positions) {
            int x = position.getX();
            int y = position.getY();
            board[y][x].removeMouseListener(board[y][x].getMouseListeners()[0]);
            board[y][x].addMouseListener(oriListener);
        }
    }


    public void clickMenu(ActionListener slistener, ActionListener llistener, ActionListener rlistener) {
        MenuView menu = new MenuView();
        menu.addButtonsListener(slistener, llistener, rlistener);
    }

    public void transformPiece(Position transformedPiece) {
        int x = transformedPiece.getX();
        int y = transformedPiece.getY();
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

    public boolean isFlipped() {
        return isFlipped;
    }
}
