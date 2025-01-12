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

    public GameOverView(String winner, ActionListener restartListener) {
        if (winner.equals("DRAW")) {
            setTitle("It's a draw!");
        } else {
            setTitle("Congratulations! " + winner + " wins!");
        }
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout()); // Main layout

        // Create top panel for the image
        topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        try {
            BufferedImage image = ImageIO.read(new File(IMAGE_PATH + winner + ".jpg"));
            Image scaledImage = image.getScaledInstance(300, 150, Image.SCALE_SMOOTH); // Resize to fit
            picLabel = new JLabel(new ImageIcon(scaledImage));
            topPanel.add(picLabel);
        } catch (IOException ex) {
            System.out.println("Image not found");
        }

        bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin around the buttons
        bottomPanel.setBackground(Color.WHITE);

        restartButton = new JButton("Restart");
        restartButton.setFont(MENU_FONT);
        restartButton.setBackground(Color.WHITE);
        restartButton.setForeground(Color.BLACK);
        restartButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        restartButton.addActionListener(restartListener);
        bottomPanel.add(restartButton);

        // Add panels to the frame
        add(topPanel, BorderLayout.CENTER);  // Image at the top
        add(bottomPanel, BorderLayout.SOUTH); // Buttons at the bottom

        // Make the frame visible
        setVisible(true);
    }
}
