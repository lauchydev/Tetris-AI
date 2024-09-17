package main.highscores;

import main.ui.BasicScreen;
import main.ui.MainScreenListener;

import javax.swing.*;
import java.awt.*;


public class HighScoreScreen extends BasicScreen {

    private final HighScoresPanel highScoresPanel;

    public HighScoreScreen(MainScreenListener listener) {
        super(listener, "High Scores");
        highScoresPanel = new HighScoresPanel();
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // Padding
        add(highScoresPanel, BorderLayout.CENTER);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        highScoresPanel.updateScores();
    }

}