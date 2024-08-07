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

        splashImage = new ImageIcon(getClass().getResource("/path/to/image.jpg")).getImage();
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
        g.drawImage(splashImage, 0, 0, Tetris.frameWidth, Tetris.frameHeight, this);
    }
}
