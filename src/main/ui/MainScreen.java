package main.ui;

import main.Tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainScreen extends BasePanel {

    public MainScreen() {
        super();
        setLayout(new GridBagLayout());
        createHeader();
        createButtons();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) { Tetris.instance.setDefaultSize(); }
    }

    private void createHeader() {
        JLabel menuLabel = UI.createHeaderLabel("Main Menu");
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
        JButton button = UI.createMenuButton(text);
        button.addActionListener(action);
        add(button, gbc);
    }

}
