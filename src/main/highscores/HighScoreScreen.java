package main.highscores;

import main.ui.BasicScreen;

import javax.swing.*;
import java.awt.*;


public class HighScoreScreen extends BasicScreen {

    private final HighScoresPanel highScoresPanel;

    public HighScoreScreen() {
        super("High Scores");
        highScoresPanel = new HighScoresPanel();
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // Padding
        add(highScoresPanel, BorderLayout.CENTER);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) { highScoresPanel.updateScores(); }
    }

}