package main.highscores;

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
        Font scoresFont = new Font("Arial", Font.PLAIN, 16);

        // Create header labels for the table
        createLabel(scoresFont, "#", 0, 0); // Rank column header
        createLabel(scoresFont, "Name", 1, 0); // Name column header
        createLabel(scoresFont, "Score", 2, 0); // Score column header

        // Loop through the scoresList and display rank, name, and score
        for (int i = 0; i < scoresList.size(); i++) {
            HighScores.ScoreEntry entry = scoresList.get(i);
            createLabel(scoresFont, String.valueOf(i + 1), 0, i + 1); // Rank (1-based index)
            createLabel(scoresFont, entry.getName(), 1, i + 1); // Player name
            createLabel(scoresFont, String.valueOf(entry.getScore()), 2, i + 1); // Player score
        }
        revalidate();
        repaint();
    }

    private void createLabel(Font scoresFont, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(scoresFont);
        label.setForeground(Color.WHITE);
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
