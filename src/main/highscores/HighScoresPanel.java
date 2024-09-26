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

        // Loop through the scoresList and display rank, name, and score
        for (int i = 0; i < scoresList.size(); i++) {
            HighScores.ScoreEntry entry = scoresList.get(i);
            createLabel(String.valueOf(i + 1), 0, i + 1); // Rank (1-based index)
            createLabel(entry.getName(), 1, i + 1); // Player name
            createLabel(String.valueOf(entry.getScore()), 2, i + 1); // Player score
        }
        revalidate();
        repaint();
    }

    private void createLabel(String text, int x, int y) {
        JLabel label = UI.createHighScoresLabel(text);
        GridBagConstraints gbc = new GridBagConstraints();

        // Add more space between the columns by increasing the horizontal padding
        gbc.insets = new Insets(10, x == 0 ? 10 : 120, 5, x == 2 ? 10 : 20); // Adjust insets based on column position
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;  // Aligns labels to the left
        add(label, gbc);
    }
}
