package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static View.GameView.IMAGE_PATH;

public class BlueWinView extends JFrame {
    private Font menuFont = new Font("Lucida Calligraphy", Font.BOLD, 20);
    private JButton restartButton;

    public BlueWinView(ActionListener restartListener) {
        setTitle("Congratulations! Blue wins!");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout()); // Main layout

        // Create top panel for the image
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        try {
            BufferedImage image = ImageIO.read(new File(IMAGE_PATH + "BLUE_WIN.jpg"));
            Image scaledImage = image.getScaledInstance(300, 150, Image.SCALE_SMOOTH); // Resize to fit
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            topPanel.add(picLabel);
        } catch (IOException ex) {
            System.out.println("Image not found");
        }

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin around the buttons
        bottomPanel.setBackground(Color.WHITE);

        restartButton = new JButton("Restart");
        restartButton.setFont(menuFont);
        restartButton.setBackground(Color.BLACK);
        restartButton.setForeground(Color.WHITE);
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

