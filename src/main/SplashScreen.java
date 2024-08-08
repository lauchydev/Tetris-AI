package main;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends JPanel {
    private Tetris parentFrame;
    private Image splashImage;

    public SplashScreen(Tetris parentFrame) {
        this.parentFrame = parentFrame;

        splashImage = new ImageIcon(getClass().getResource("/Images/Splash_Screen.jpg")).getImage();
        this.setPreferredSize(new Dimension(Tetris.frameWidth, Tetris.frameHeight));


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                parentFrame.showMainScreen();
            }
        }, 3000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Calculate the aspect ratio of the image and the panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int imageWidth = splashImage.getWidth(this);
        int imageHeight = splashImage.getHeight(this);

        double panelAspectRatio = (double) panelWidth / panelHeight;
        double imageAspectRatio = (double) imageWidth / imageHeight;

        int drawWidth, drawHeight;
        if (imageAspectRatio > panelAspectRatio) {
            // Scale image to fit width
            drawWidth = panelWidth;
            drawHeight = (int) (panelWidth / imageAspectRatio);
        } else {
            // Scale image to fit height
            drawHeight = panelHeight;
            drawWidth = (int) (panelHeight * imageAspectRatio);
        }

        // Calculate the x and y positions to center the image
        int x = (panelWidth - drawWidth) / 2;
        int y = (panelHeight - drawHeight) / 2;

        // Draw the scaled image
        g.drawImage(splashImage, x, y, drawWidth, drawHeight, this);
    }
}