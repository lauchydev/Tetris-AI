package main;

import javax.swing.*;
import java.awt.*;

public class HighScoreScreen extends JPanel {

    private Tetris parentFrame;

    public HighScoreScreen(Tetris parentFrame) {
        this.parentFrame = parentFrame;
        this.setLayout(null);
        JLabel highScoreLabel = new JLabel("High Scores", JLabel.CENTER);
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        highScoreLabel.setFont(labelFont);

        // Calculate the width of the label string
        FontMetrics metrics = highScoreLabel.getFontMetrics(labelFont);
        int labelWidth = metrics.stringWidth("High Scores");


        /**
         *  Using a public frameWidth variable from the {@link Tetris.frameWidth} to center the label
         */
        int panelWidth = Tetris.frameWidth;
        int xPosition = (panelWidth - labelWidth) / 2;

        highScoreLabel.setBounds(xPosition, 80, labelWidth, 30);

        this.add(highScoreLabel);

        CreateButtons();
    }

    public void CreateButtons() {
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setBounds(350, 200, 200, 30);
        backButton.addActionListener(e -> {
            parentFrame.showMainScreen();
        });

        this.add(backButton);
    }
}