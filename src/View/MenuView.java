package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Util.Consts.*;

// Manage Menu GUI
public class MenuView extends JFrame {
    private JButton saveButton, loadButton, restartButton;
    private JPanel topPanel, bottomPanel;

    // Constructor
    public MenuView() {
        // Set up the JFrame
        setTitle("Game Menu");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout()); // Main layout

        // Create top panel for the image
        topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        // Add image to top panel
        try {
            BufferedImage image = ImageIO.read(new File(IMAGE_PATH + "MENU.jpg"));
            Image scaledImage = image.getScaledInstance(300, 150, Image.SCALE_SMOOTH); // Resize to fit
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            topPanel.add(picLabel);
        } catch (IOException ex) {
            System.out.println("Image not found");
        }

        // Create bottom panel for the buttons
        bottomPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // Three buttons in one row
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin around the buttons
        bottomPanel.setBackground(Color.WHITE);

        // Create buttons with styles
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        restartButton = new JButton("Restart");

        saveButton.setFont(MENU_FONT);
        loadButton.setFont(MENU_FONT);
        restartButton.setFont(MENU_FONT);

        saveButton.setBackground(new Color(102, 205, 170));   // Medium aquamarine
        loadButton.setBackground(new Color(135, 206, 250));   // Sky blue
        restartButton.setBackground(new Color(240, 128, 128)); // Light coral

        saveButton.setForeground(Color.WHITE);
        loadButton.setForeground(Color.WHITE);
        restartButton.setForeground(Color.WHITE);

        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loadButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        restartButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add buttons to bottom panel
        bottomPanel.add(saveButton);
        bottomPanel.add(loadButton);
        bottomPanel.add(restartButton);

        // Add panels to the frame
        add(topPanel, BorderLayout.CENTER);  // Image at the top
        add(bottomPanel, BorderLayout.SOUTH); // Buttons at the bottom

        // Make the frame visible
        setVisible(true);
    }

    public void addButtonsListener(ActionListener sListener, ActionListener lListener, ActionListener rListener) {
        saveButton.addActionListener(sListener);
        loadButton.addActionListener(lListener);
        restartButton.addActionListener(rListener);
    }
}
