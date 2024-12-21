import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.io.*;

// Manage game GUI (board, pieces)
public class GameView {
    final int headerHeight = 70;
    final int footerHeight = 70;
    final int boardWidth = 800;
    final int boardHeight = 800;

    private JFrame frame = new JFrame("Kwazam Chess");
    private JLabel textLabel = new JLabel();
    private JPanel headerPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel boardPanel = new JPanel();
    private JPanel footerPanel = new JPanel(new BorderLayout());
    private JPanel menuWrapper = new JPanel(new BorderLayout());
    private String[] options = { "Save Game", "Load Game", "Restart" };
    private JButton menu;
    private JLabel turnLabel;
    private JButton[][] board = new JButton[8][5];
    private static String ICONPATH="src/img/";

    // Font setup
    private Font titleFont = new Font("Lucida Calligraphy", Font.BOLD, 50);
    private Font turnFont = new Font("Lucida Calligraphy", Font.BOLD, 30);
    private Font menuFont = new Font("Lucida Calligraphy", Font.BOLD, 20);

    // Image icon for pieces
    private ImageIcon BlueBiz = new ImageIcon(
            new ImageIcon(ICONPATH + "Blue_Biz.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon RedBiz = new ImageIcon(
            new ImageIcon(ICONPATH + "Red_Biz.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon BlueRam = new ImageIcon(
            new ImageIcon(ICONPATH + "Blue_Ram.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon RedRam = new ImageIcon(
            new ImageIcon(ICONPATH + "Red_Ram.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon BlueSau = new ImageIcon(
            new ImageIcon(ICONPATH + "Blue_Sau.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon RedSau = new ImageIcon(
            new ImageIcon(ICONPATH + "Red_Sau.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon BlueTor = new ImageIcon(
            new ImageIcon(ICONPATH + "Blue_Tor.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon RedTor = new ImageIcon(
            new ImageIcon(ICONPATH + "Red_Tor.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon BlueXor = new ImageIcon(
            new ImageIcon(ICONPATH + "Blue_Xor.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    private ImageIcon RedXor = new ImageIcon(
            new ImageIcon(ICONPATH + "Red_Xor.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

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
        menu.addActionListener(e -> Menu());
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
                                board[i][j].setIcon(BlueTor);
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
                                board[i][j].setIcon(BlueXor);
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

    public void Menu() {
        int response = JOptionPane.showOptionDialog(
                null,
                "choose the option: ",
                "choose frame",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (response == 0) {
            // Save Game
            saveGame();
        } else if (response == 1) {
            // Load Game
            loadGame();
        } else if (response == 2) {
            // Restart
            initPosition();
        }
    }

    public void saveGame() {
        try (PrintWriter writer = new PrintWriter("gameState.txt")) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 5; j++) {
                    ImageIcon icon = (ImageIcon) board[i][j].getIcon();
                    if (icon == RedTor) {
                        writer.print("RedTor");
                    } else if (icon == RedBiz) {
                        writer.print("RedBiz");
                    } else if (icon == RedSau) {
                        writer.print("RedSau");
                    } else if (icon == RedRam) {
                        writer.print("RedRam");
                    } else if (icon == RedXor) {
                        writer.print("RedXor");
                    } else if (icon == BlueTor) {
                        writer.print("BlueTor");
                    } else if (icon == BlueBiz) {
                        writer.print("BlueBiz");
                    } else if (icon == BlueSau) {
                        writer.print("BlueSau");
                    } else if (icon == BlueRam) {
                        writer.print("BlueRam");
                    } else if (icon == BlueXor) {
                        writer.print("BlueXor");
                    } else {
                        writer.print("null");
                    }
                    if (j < 5 - 1) {
                        writer.print(",");
                    }
                }
                writer.println();
            }
            JOptionPane.showMessageDialog(frame, "Game saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Erroe saving game: " + e.getMessage());
        }
    }

    public void loadGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader("gameState.txt"))) {
            for (int i = 0; i < 8; i++) {
                String[] line = reader.readLine().split(",");
                for (int j = 0; j < 5; j++) {
                    switch (line[j]) {
                        case "RedTor":
                            board[i][j].setIcon(RedTor);
                            break;
                        case "RedBiz":
                            board[i][j].setIcon(RedBiz);
                            break;
                        case "RedSau":
                            board[i][j].setIcon(RedSau);
                            break;
                        case "RedRam":
                            board[i][j].setIcon(RedRam);
                            break;
                        case "RedXor":
                            board[i][j].setIcon(RedXor);
                            break;
                        case "BlueTor":
                            board[i][j].setIcon(BlueTor);
                            break;
                        case "BlueBiz":
                            board[i][j].setIcon(BlueBiz);
                            break;
                        case "BlueSau":
                            board[i][j].setIcon(BlueSau);
                            break;
                        case "BlueRam":
                            board[i][j].setIcon(BlueRam);
                            break;
                        case "BlueXor":
                            board[i][j].setIcon(BlueXor);
                            break;
                        default:
                            board[i][j].setIcon(null);
                            break;
                    }
                }
            }
            JOptionPane.showMessageDialog(frame, "Game loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error loading game: " + e.getMessage());
        }
    }
}
