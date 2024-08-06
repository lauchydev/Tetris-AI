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


        /*
         *  Using a public frameWidth variable from the {@link Tetris.frameWidth} to center the label
         */
        int panelWidth = Tetris.frameWidth;
        int xPosition = (panelWidth - labelWidth) / 2;

        highScoreLabel.setBounds(xPosition, 80, labelWidth, 30);

        this.add(highScoreLabel);

        displayScores();
        createButtons();
    }

    public void displayScores(){
        JLabel [] scoresLabel = new JLabel[10];
        Font scoresFont = new Font("Arial",Font.PLAIN, 16);

        for(int i = 0; i < 5; i++){
            scoresLabel[i] = new JLabel("Testing");
            scoresLabel[i].setFont(scoresFont);
            FontMetrics scoresFontMetrics = scoresLabel[i].getFontMetrics(scoresFont);
            int scoreLabelWidth = scoresFontMetrics.stringWidth("Testing");
            scoresLabel[i].setBounds(((Tetris.frameWidth-scoreLabelWidth)/2)-100, (i*60)+130, 100, 40);

        }

        for(int i = 0; i < 5; i++){
            scoresLabel[i+5] = new JLabel("Testing");
            scoresLabel[i+5].setFont(scoresFont);
            FontMetrics scoresFontMetrics = scoresLabel[i].getFontMetrics(scoresFont);
            int scoreLabelWidth = scoresFontMetrics.stringWidth("Testing");
            scoresLabel[i+5].setBounds(((Tetris.frameWidth-scoreLabelWidth)/2)+100, (i*60)+130, 100, 40);
        }

        for(int i = 0; i < 10; i++){
            this.add(scoresLabel[i]);
        }

    }

    public void createButtons() {
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setBounds(350, Tetris.frameHeight-175, 200, 30);
        backButton.addActionListener(e -> {
            parentFrame.showMainScreen();
        });

        this.add(backButton);
    }
}