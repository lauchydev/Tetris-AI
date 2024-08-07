package main.ui;

import main.Tetris;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;

public class MainScreen extends JPanel {

    private static final String HEADER_TITLE = "Main Menu";
    private static final String HEADER_FONT = "Arial";
    private static final int HEADER_FONT_FLAGS = Font.BOLD;
    private static final int HEADER_FONT_SIZE = 20;
    private static final String BUTTON_FONT = "Arial";
    private static final int BUTTON_FONT_FLAGS = Font.BOLD;
    private static final int BUTTON_FONT_SIZE = 12;

    private final Tetris parentFrame;

    public MainScreen(Tetris parentFrame) {
        this.parentFrame = parentFrame;
        this.setLayout(null);
        this.createHeader();
        this.createButtons();
    }

    private void createHeader() {
        JLabel menuLabel = new JLabel(HEADER_TITLE, JLabel.CENTER);
        Font labelFont = new Font(HEADER_FONT, HEADER_FONT_FLAGS, HEADER_FONT_SIZE);
        menuLabel.setBounds(400, 80, 105, 30);
        menuLabel.setFont(labelFont);
        this.add(menuLabel);
    }

    public void createButtons() {
        this.createButton("Play",          350, 200, 200, 30, e -> {});
        this.createButton("Configuration", 350, 275, 200, 30, e -> parentFrame.showConfigurationScreen());
        this.createButton("High Scores",   350, 350, 200, 30, e -> parentFrame.showHighScoresScreen());
        this.createButton("Exit",          350, 425, 200, 30, e -> System.exit(0));
    }

    private void createButton(String text, int x, int y, int width, int height, ActionListener action) {
        Font buttonFont = new Font(BUTTON_FONT, BUTTON_FONT_FLAGS, BUTTON_FONT_SIZE);
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBounds(x, y, width, height);
        button.addActionListener(action);
        this.add(button);
    }
}