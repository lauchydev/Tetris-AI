package main.highscores;

import main.Tetris;
import main.ui.BasicScreen;

import javax.swing.*;
import java.awt.*;


public class HighScoreScreen extends BasicScreen {

    public HighScoreScreen(Tetris parentFrame) {
        super(parentFrame, "");

        this.setBackground(new Color(20, 20, 20));

        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(390, 20, 300, 80);
        this.add(titleLabel);

        displayScores();
    }

    /**
     * Display the scores
     */
    public void displayScores() {
        String [] scoresText = this.parentFrame.highScores.getScores();
        Font scoresFont = new Font("Arial",Font.PLAIN, 16);

        //Displaying the data on the screen
        for(int i = 0; i < 5; i++){
            this.createLabel(scoresFont, scoresText[i], -150, i * 60 + 130);
            this.createLabel(scoresFont, scoresText[i+5], 100, i * 60 + 130);
        }

    }

    /**
     * Create a high score label
     * @param scoresFont Font object to use
     * @param text The text that goes in the label
     * @param xOffset x offset
     * @param yOffset y offset
     */
    private void createLabel(Font scoresFont, String text, int xOffset, int yOffset) {
        JLabel scoresLabel = new JLabel(text);
        scoresLabel.setFont(scoresFont);
        scoresLabel.setForeground(Color.WHITE);
        FontMetrics scoresFontMetrics = scoresLabel.getFontMetrics(scoresFont);
        int scoreLabelWidth = scoresFontMetrics.stringWidth("Testing");
        scoresLabel.setBounds(((Tetris.frameWidth -scoreLabelWidth)/2)+xOffset, yOffset, 100, 40);
        this.add(scoresLabel);
    }

}