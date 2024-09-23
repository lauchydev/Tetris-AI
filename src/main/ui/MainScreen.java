package main.ui;

import main.Tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainScreen extends JPanel {

    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 25);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 12);
    private static final Color BUTTON_BACKGROUND_COLOUR = new Color(144, 238, 144);
    private static final Dimension BUTTON_DIMENSION = new Dimension(200, 30);

    public MainScreen() {
        setLayout(new GridBagLayout());
        setBackground(new Color(20, 20, 20));
        createHeader();
        createButtons();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) { Tetris.instance.setDefaultSize(); }
    }

    private void createHeader() {
        JLabel menuLabel = new JLabel("Main Menu", JLabel.CENTER);
        menuLabel.setFont(HEADER_FONT);
        menuLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 95, 0);
        add(menuLabel, gbc);
    }

    public void createButtons() {
        createButton("Play",          1, e -> Tetris.instance.showPlayScreen());
        createButton("Configuration", 2, e -> Tetris.instance.showConfigurationScreen());
        createButton("High Scores",   3, e -> Tetris.instance.showHighScoresScreen());
        createButton("Exit",          4, e -> Tetris.instance.showExitConfirmation());
    }

    private void createButton(String text, int row, ActionListener action) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 50, 0);
        gbc.gridy = row;
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setPreferredSize(BUTTON_DIMENSION);
        button.setBackground(BUTTON_BACKGROUND_COLOUR);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addActionListener(action);
        add(button, gbc);
    }

}
