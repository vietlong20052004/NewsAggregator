package vietlong.app.test;

import vietlong.app.screen.BackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanelTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Background Panel Test");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            // Use the custom BackgroundPanel
            BackgroundPanel backgroundPanel = new BackgroundPanel("ImageIcon/background.jpg");
            frame.setContentPane(backgroundPanel);

            // Adding a sample label to test the background panel
            JLabel sampleLabel = new JLabel("This is a test label");
            sampleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            sampleLabel.setForeground(Color.WHITE);
            backgroundPanel.setLayout(new GridBagLayout()); // Center the label
            backgroundPanel.add(sampleLabel);

            frame.setVisible(true);
        });
    }
}
