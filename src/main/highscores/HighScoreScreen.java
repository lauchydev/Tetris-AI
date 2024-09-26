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
        JPanel midPanel = new JPanel(new BorderLayout());
        midPanel.setOpaque(false);
        midPanel.add(highScoresPanel, BorderLayout.CENTER);
        midPanel.add(createClearButtonPanel(), BorderLayout.SOUTH);
        add(midPanel, BorderLayout.CENTER);
    }

    private Component createClearButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton clearButton = new JButton("Clear High Scores");
        clearButton.addActionListener((event) -> showClearConfirmationDialog());
        buttonPanel.add(clearButton);
        return buttonPanel;
    }

    private void showClearConfirmationDialog() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to clear the high scores?",
                "Clear High Scores Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
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