package main.highscores;


import javax.swing.*;
import java.awt.*;

class HighScoresPanel extends JPanel {
    public HighScoresPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);
    }

    public void updateScores() {
        removeAll();
        String [] scoresText = HighScores.getInstance().getScores();
        Font scoresFont = new Font("Arial",Font.PLAIN, 16);
        for(int i = 0; i < 5; i++){
            createLabel(scoresFont, scoresText[i], 0, i);
            createLabel(scoresFont, scoresText[i+5], 1, i);
        }
    }

    /**
     * Create and add a high score label
     * @param scoresFont Font object to use
     * @param text The text that goes in the label
     * @param x gridx
     * @param y gridy
     */
    private void createLabel(Font scoresFont, String text, int x, int y) {
        JLabel scoresLabel = new JLabel(text);
        scoresLabel.setFont(scoresFont);
        scoresLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, x == 0 ? 5 : 50, 30, x == 0 ? 50 : 5);
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(scoresLabel, gbc);
    }
}