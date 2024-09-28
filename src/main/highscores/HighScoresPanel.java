package main.highscores;

import main.ui.UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class HighScoresPanel extends JPanel {
    public HighScoresPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);
    }

    public void updateScores() {
        removeAll();
        List<HighScores.ScoreEntry> scoresList = HighScores.getInstance().getScores();

        // Create header labels for the table
        createLabel("#", 0, 0); // Rank column header
        createLabel("Name", 1, 0); // Name column header
        createLabel("Score", 2, 0); // Score column header
        createLabel("Config", 3, 0); // Score column header

        for (int i = 0; i < scoresList.size(); i++) {
            HighScores.ScoreEntry entry = scoresList.get(i);
            createLabel(String.valueOf(i + 1), 0, i + 1); // Rank (1-based index)
            createLabel(entry.name(), 1, i + 1); // Player name
            createLabel(String.valueOf(entry.score()), 2, i + 1); // Player score
            createLabel(entry.config(), 3, i + 1);
        }
        revalidate();
        repaint();
    }

    private void createLabel(String text, int x, int y) {
        JLabel label = UI.createHighScoresLabel(text.toUpperCase());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;  // Aligns labels to the left
        add(label, gbc);
    }
}
