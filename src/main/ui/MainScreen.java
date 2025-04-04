package main.ui;

import main.Tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainScreen extends BasePanel {
    private final Image backgroundImage = UI.BACKGROUND_IMG;

    public MainScreen() {
        super();
        setLayout(new GridBagLayout());
        createButtons();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) { Tetris.instance.setDefaultSize(); }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void createButtons() {
        createButton("PLAY",          1, e -> Tetris.instance.showPlayScreen());
        createButton("CONFIGURATION", 2, e -> Tetris.instance.showConfigurationScreen());
        createButton("HIGH SCORES",   3, e -> Tetris.instance.showHighScoresScreen());
        createButton("EXIT",          4, e -> Tetris.instance.showExitConfirmation());
    }

    private void createButton(String text, int row, ActionListener action) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 30, 0);
        gbc.gridy = row;
        JButton button = UI.createMenuButton(text);
        button.addActionListener(action);
        add(button, gbc);
    }
}