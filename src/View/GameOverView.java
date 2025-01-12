package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Util.Consts.*;

public class GameOverView extends JFrame {
    private JButton restartButton;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel picLabel;
    private JLabel winnerLabel;

    public GameOverView(String winner, ActionListener restartListener) {
        // Set title based on winner
        if (winner.equals("DRAW")) {
            setTitle("It's a draw!");
        } else {
            setTitle("Congratulations! " + winner + " wins!");
        }
        setSize(400, 350);
        setLocationRelativeTo(null); // Center the frame
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout()); // Main layout

        // Create top panel for the image and winner label
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Add a winner label above the image
        winnerLabel = new JLabel();
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setFont(TITLE_FONT);
        if (winner.equals("DRAW")) {
            winnerLabel.setText("It's a draw!");
        } else {
            winnerLabel.setText(winner + " Wins!");
        }
        winnerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding
        topPanel.add(winnerLabel, BorderLayout.NORTH);

        // Add image below the label
        try {
            BufferedImage image = ImageIO.read(new File(IMAGE_PATH + winner + ".jpg"));
            Image scaledImage = image.getScaledInstance(300, 150, Image.SCALE_SMOOTH); // Resize to fit
            picLabel = new JLabel(new ImageIcon(scaledImage));
            picLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(picLabel, BorderLayout.CENTER);
        } catch (IOException ex) {
            System.out.println("Image not found");
        }

        // Create bottom panel for the Restart button
        bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin around the buttons
        bottomPanel.setBackground(Color.WHITE);

        restartButton = new JButton("Restart");
        restartButton.setFont(MENU_FONT);
        restartButton.setBackground(Color.BLACK);
        restartButton.setForeground(Color.WHITE);
        restartButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        restartButton.addActionListener(restartListener);
        bottomPanel.add(restartButton);

        // Add panels to the frame
        add(topPanel, BorderLayout.CENTER);  // Image and text at the top
        add(bottomPanel, BorderLayout.SOUTH); // Buttons at the bottom

        // Make the frame visible
        setVisible(true);
    }
}
