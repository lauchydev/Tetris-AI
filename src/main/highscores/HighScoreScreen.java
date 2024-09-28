package main.highscores;

import main.ui.BasicScreen;
import main.ui.UI;

import javax.swing.*;
import java.awt.*;


public class HighScoreScreen extends BasicScreen {

    private final HighScoresPanel highScoresPanel;

    public HighScoreScreen() {
        super("HIGH SCORES");
        highScoresPanel = new HighScoresPanel();
        JPanel midPanel = new JPanel(new BorderLayout());
        midPanel.setOpaque(false);
        midPanel.add(highScoresPanel, BorderLayout.CENTER);
        midPanel.add(createClearButtonPanel(), BorderLayout.SOUTH);
        add(midPanel, BorderLayout.CENTER);
    }

    private Component createClearButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton clearButton = UI.createSecondaryButton("CLEAR HIGH SCORES");
        clearButton.addActionListener((event) -> showClearConfirmationDialog());
        buttonPanel.add(clearButton);
        return buttonPanel;
    }

    private void showClearConfirmationDialog() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "You are about to clear all high scores! Are you sure?",
                "Clear High Scores Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            HighScores.getInstance().clear();
            refreshHighScores();
        }
    }

    private void refreshHighScores() {
        highScoresPanel.updateScores();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) { refreshHighScores(); }
    }

}