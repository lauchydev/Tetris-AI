package main.highscores;

import main.ui.BasicScreen;
import main.ui.MainScreenListener;

import javax.swing.*;
import java.awt.*;


public class HighScoreScreen extends BasicScreen {

    private final HighScores highScores;

    public HighScoreScreen(MainScreenListener listener, HighScores highScores) {
        super(listener, "High Scores");
        this.highScores = highScores;
        displayScores();
    }

    /**
     * Display the scores
     */
    public void displayScores() {
        String [] scoresText = highScores.getScores();
        Font scoresFont = new Font("Arial",Font.PLAIN, 16);
        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(null);

        //Displaying the data on the screen
        for(int i = 0; i < 5; i++){
            scoresPanel.add(createLabel(scoresFont, scoresText[i], -150, i * 60 + 30));
            scoresPanel.add(createLabel(scoresFont, scoresText[i+5], 100, i * 60 + 30));
        }
        add(scoresPanel, BorderLayout.CENTER);
        scoresPanel.setVisible(true);
        scoresPanel.setOpaque(false);
    }

    /**
     * Create a high score label
     * @param scoresFont Font object to use
     * @param text The text that goes in the label
     * @param xOffset x offset
     * @param yOffset y offset
     * @return JLabel
     */
    private JLabel createLabel(Font scoresFont, String text, int xOffset, int yOffset) {
        JLabel scoresLabel = new JLabel(text);
        scoresLabel.setFont(scoresFont);
        scoresLabel.setForeground(Color.WHITE);
        FontMetrics scoresFontMetrics = scoresLabel.getFontMetrics(scoresFont);
        int scoreLabelWidth = scoresFontMetrics.stringWidth("Testing");
        SwingUtilities.invokeLater(() -> {
            Rectangle r = new Rectangle( ((getWidth() - scoreLabelWidth) / 2) + xOffset, yOffset, 100, 40);
            scoresLabel.setBounds(r);
        });
        return scoresLabel;
    }

}