package main;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    private final int duration;

    public SplashScreen(int duration) {
        this.duration = duration;
    }

    public void showSplashAndWait() {
        JPanel content = (JPanel) getContentPane();
        // Set the window's bounds, centering the window
        int width = 512;
        int height = 512;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
        // Build the splash screen
        ImageIcon imageIcon = new ImageIcon("src/Images/Splash_Screen.jpg");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        JLabel label = new JLabel(imageIcon);
        content.add(label, BorderLayout.CENTER);
        // Display it
        setVisible(true);
        // Wait a little while, maybe showing a progress bar
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setVisible(false);
    }

}