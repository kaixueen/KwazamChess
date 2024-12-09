import javax.swing.*;
import java.awt.*;

// Manage game GUI (board, pieces)
public class GameView {
    final int ROWS = 8;
    final int COLUMNS = 5;
    final int headerHeight = 70;
    final int resultHeight = 70;
    final int boardWidth = 800;
    final int boardHeight = 800;

    JFrame frame = new JFrame("Kwazam Chess");
    JLabel textLabel = new JLabel();
    JPanel headerPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel resultPanel = new JPanel();

    JButton[][] board = new JButton[ROWS][COLUMNS];

    GameView() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Header Panel
        textLabel.setBackground(Color.BLACK);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 50));
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
        boardPanel.setLayout(new GridLayout(ROWS, COLUMNS, 1, 1));
        int buttonSize = 640 / ROWS; // Ensure buttons are square
        boardPanel.setPreferredSize(new Dimension(buttonSize * COLUMNS - COLUMNS, buttonSize * ROWS - ROWS));
        boardPanel.setBackground(Color.BLACK);
        centerPanel.add(boardPanel, BorderLayout.CENTER);
        centerPanel.setBackground(Color.darkGray);

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

        // Result Panel
        resultPanel.setPreferredSize(new Dimension(boardWidth, resultHeight));
        resultPanel.setBackground(Color.BLACK);
        frame.add(resultPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new GameView();
    }
}
