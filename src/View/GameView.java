// Todo:
// 1. Add flip board feature (done)
// 2. Add effect when a piece is chosen, e.g. highlight the piece (done)
// 3. Add effect to show all the moves that a piece can make, e.g. change the valid position background color
// 4. Maybe enhance a bit menu interface?
// 5. Some effects or message when the game is over (red win/blue win/draw)
// 6. Add a Restart button when the game is over (done)
// 7. Add removePiece() method to remove a piece from the board (done)
// 8. Add addPiece() method to add a piece to the board (done)
// 9. Add a surrender button?
package View;

import Controller.GameController;
import Model.Position;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
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
    // private String[] options = { "Save Game", "Load Game", "Restart" };
    private JButton menu;
    private JLabel turnLabel;
    private JButton[][] board = new JButton[8][5];
    private static String ICONPATH="src/Images/";
    private JButton saveButton;
    private JButton loadButton;
    private JButton restartButton;

    // new
    private boolean isFlipped = false;
    private boolean isEnlarged = false;
    private int enlargedButtonX = -1, enlargedButtonY = -1;

    // Font setup
    private Font titleFont = new Font("Lucida Calligraphy", Font.BOLD, 50);
    private Font turnFont = new Font("Lucida Calligraphy", Font.BOLD, 30);
    private Font menuFont = new Font("Lucida Calligraphy", Font.BOLD, 20);

    // Image icon for pieces
    private ImageIcon BlueBiz = new ImageIcon(
            new ImageIcon(ICONPATH + "Blue_Biz.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "BlueBiz");
    private ImageIcon RedBiz = new ImageIcon(
            new ImageIcon(ICONPATH + "Red_Biz.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "RedBiz");
    private ImageIcon BlueRam = new ImageIcon(
            new ImageIcon(ICONPATH + "Blue_Ram.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "BlueRam");
    private ImageIcon RedRam = new ImageIcon(
            new ImageIcon(ICONPATH + "Red_Ram.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "RedRam");
    private ImageIcon BlueSau = new ImageIcon(
            new ImageIcon(ICONPATH + "Blue_Sau.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "BlueSau");
    private ImageIcon RedSau = new ImageIcon(
            new ImageIcon(ICONPATH + "Red_Sau.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "RedSau");
    private ImageIcon BlueTor = new ImageIcon(
            new ImageIcon(ICONPATH + "Blue_Tor.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "BlueTor");
    private ImageIcon RedTor = new ImageIcon(
            new ImageIcon(ICONPATH + "Red_Tor.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "RedTor");
    private ImageIcon BlueXor = new ImageIcon(
            new ImageIcon(ICONPATH + "Blue_Xor.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "BlueXor");
    private ImageIcon RedXor = new ImageIcon(
            new ImageIcon(ICONPATH + "Red_Xor.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH), "RedXor");

    // Game Over dialog
    private JDialog gameOverDialog;
    private JLabel topGOLabel;
    private JLabel bottomGOLabel;

    // Constructor
    public GameView() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        // frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Header Panel
        textLabel.setBackground(Color.BLACK);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(titleFont);
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

        initPosition();

        // Footer Panel
        menu = new JButton("Menu");
        menu.setFont(menuFont);
        menu.setBackground(Color.WHITE);
        // menu.addActionListener(e -> Menu());
        // To make the button has margin and not too close to the edge
        menuWrapper.setBackground(Color.BLACK);
        menuWrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add margin to the wrapper
        menuWrapper.add(menu, BorderLayout.CENTER);
        footerPanel.add(menuWrapper, BorderLayout.WEST);

        turnLabel = new JLabel("Now is Blue Turn!");
        turnLabel.setFont(turnFont);
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // footerPanel.add(menu, BorderLayout.WEST);
        footerPanel.add(turnLabel, BorderLayout.EAST);
        footerPanel.setPreferredSize(new Dimension(boardWidth, footerHeight));
        footerPanel.setBackground(Color.BLACK);
        frame.add(footerPanel, BorderLayout.SOUTH);
    }

    public void initPosition() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                switch (i) {
                    case 0:
                        switch (j) {

                            case 0:
                                board[i][j].setIcon(RedTor);
                                break;

                            case 1:
                                board[i][j].setIcon(RedBiz);
                                break;

                            case 2:
                                board[i][j].setIcon(RedSau);
                                break;

                            case 3:
                                board[i][j].setIcon(RedBiz);
                                break;

                            case 4:
                                board[i][j].setIcon(RedXor);
                                break;
                        }

                        break;

                    case 1:
                        board[i][j].setIcon(RedRam);
                        break;

                    case 6:
                        board[i][j].setIcon(BlueRam);
                        break;

                    case 7:
                        switch (j) {

                            case 0:
                                board[i][j].setIcon(BlueXor);
                                break;

                            case 1:
                                board[i][j].setIcon(BlueBiz);
                                break;

                            case 2:
                                board[i][j].setIcon(BlueSau);
                                break;

                            case 3:
                                board[i][j].setIcon(BlueBiz);
                                break;

                            case 4:
                                board[i][j].setIcon(BlueTor);
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

//    public int showMenu() {
//        int response = JOptionPane.showOptionDialog(
//                null,
//                "choose the option: ",
//                "choose frame",
//                JOptionPane.DEFAULT_OPTION,
//                JOptionPane.INFORMATION_MESSAGE,
//                null,
//                options,
//                options[0]);
//        return response;
////        if (response == 0) {
////            // Save Game
////            saveGame();
////        } else if (response == 1) {
////            // Load Game
////            loadGame("RedTor RedBiz RedSau RedBiz RedXor RedRam RedRam RedRam null null null null null null null null null null RedRam RedRam null null null null null null null null null null BlueRam BlueRam BlueRam BlueRam BlueRam BlueXor BlueBiz BlueSau BlueBiz BlueTor");
////        } else if (response == 2) {
////            // Restart
////            initPosition();
////        }
//    }

    public void movePiece(Position from, Position to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();
        JButton fromSquare = board[fromY][fromX];
        JButton toSquare = board[toY][toX];
        toSquare.setIcon(fromSquare.getIcon());
        fromSquare.setIcon(null);
    }
    // Maybe not neccessary, but still keep it
    public void addPiece(int x, int y, ImageIcon icon) {
        board[y][x].setIcon(icon);
    }
    public void removePiece(int x, int y) {
        board[y][x].setIcon(null);
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

        return new ImageIcon(rotated, icon.getDescription());
    }

    public void loadGame(String[][] state) {
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                switch (state[i][j]) {
                    case "RTOR":
                        board[i][j].setIcon(RedTor);
                        break;
                    case "RBIZ":
                        board[i][j].setIcon(RedBiz);
                        break;
                    case "RSAU":
                        board[i][j].setIcon(RedSau);
                        break;
                    case "RRAM":
                        board[i][j].setIcon(RedRam);
                        break;
                    case "RXOR":
                        board[i][j].setIcon(RedXor);
                        break;
                    case "BTOR":
                        board[i][j].setIcon(BlueTor);
                        break;
                    case "BBIZ":
                        board[i][j].setIcon(BlueBiz);
                        break;
                    case "BSAU":
                        board[i][j].setIcon(BlueSau);
                        break;
                    case "BRAM":
                        board[i][j].setIcon(BlueRam);
                        break;
                    case "BXOR":
                        board[i][j].setIcon(BlueXor);
                        break;
                    default:
                        board[i][j].setIcon(null);
                        break;
                }
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public void gameOver(){
        gameOverDialog = new JDialog(frame, "Game Over", true);
        gameOverDialog.setSize(300, 200);
        gameOverDialog.setLocationRelativeTo(frame);
        gameOverDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        topGOLabel = new JLabel("Game Over");
        topGOLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topGOLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 20));

        bottomGOLabel = new JLabel();
        bottomGOLabel.setHorizontalAlignment(SwingConstants.CENTER);
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Lucida Calligraphy", Font.BOLD, 12));
        restartButton.setBackground(new Color(236, 235, 222));

//        restartButton.addActionListener(e -> {
//            frame.dispose();
//            new GameView();
//        });
        bottomGOLabel.setLayout(new BorderLayout());
        bottomGOLabel.add(restartButton, BorderLayout.CENTER);
        bottomGOLabel.setVisible(true);

        gameOverDialog.setLayout(new BorderLayout());
        gameOverDialog.add(topGOLabel, BorderLayout.NORTH);
        gameOverDialog.add(bottomGOLabel, BorderLayout.CENTER);
        gameOverDialog.setVisible(true);
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

    public void addRestartListener(ActionListener listener) {
        restartButton.addActionListener(listener);
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

    private class Menu extends JFrame {
        public Menu() {
            // Set up the JFrame
            setTitle("Game Menu");
            setSize(400, 200);
            setLocationRelativeTo(null); // Center the frame
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            // Create panels for left and right sections
            JPanel leftPanel = new JPanel(new GridLayout(1, 2, 5, 5)); // Two buttons stacked vertically
            JPanel rightPanel = new JPanel(new BorderLayout());         // One button centered
            JPanel centerPanel = new JPanel();                          // Spacer for aesthetics

            // Add margins (gap) between buttons and JFrame border
            leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Top, Left, Bottom, Right
            rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add consistent gaps
            centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Set background colors
            leftPanel.setBackground(Color.WHITE);
            rightPanel.setBackground(Color.WHITE);
            centerPanel.setBackground(Color.WHITE);

            // Create buttons with styles
            saveButton = createStyledButton("Save", new Color(102, 205, 170));   // Medium aquamarine
            loadButton = createStyledButton("Load", new Color(135, 206, 250));   // Sky blue
            restartButton = createStyledButton("Restart", new Color(240, 128, 128)); // Light coral

            // Add buttons to panels
            leftPanel.add(saveButton);
            leftPanel.add(loadButton);
            rightPanel.add(restartButton, BorderLayout.CENTER);

            // Add panels to the frame
            add(leftPanel, BorderLayout.WEST);
            add(rightPanel, BorderLayout.EAST);
            add(centerPanel, BorderLayout.CENTER); // Spacer to balance layout

            // Make the frame visible
            setVisible(true);
        }

        // Helper method to create a styled button
        private JButton createStyledButton(String text, Color color) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(color);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return button;
        }
    }

    public void clickMenu(ActionListener slistener, ActionListener llistener, ActionListener rlistener) {
        new Menu();
        saveButton.addActionListener(slistener);
        loadButton.addActionListener(llistener);
        restartButton.addActionListener(rlistener);
    }

    public void transformPiece(ArrayList<Position> transformedPieces) {
        for (Position pos : transformedPieces) {
            int x = pos.getX();
            int y = pos.getY();
            String icon = ((ImageIcon) board[y][x].getIcon()).getDescription();

            if (icon.equals("RedTor")) {
                board[y][x].setIcon(RedXor);
            } else if (icon.equals("RedXor")) {
                board[y][x].setIcon(RedTor);
            } else if (icon.equals("BlueTor")) {
                board[y][x].setIcon(BlueXor);
            } else if (icon.equals("BlueXor")) {
                board[y][x].setIcon(BlueTor);
            }
        }
    }
}
