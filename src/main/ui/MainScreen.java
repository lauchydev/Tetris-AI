package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainScreen extends JPanel {

    private static final String HEADER_TITLE = "Main Menu";
    private static final String HEADER_FONT = "Arial";
    private static final int HEADER_FONT_FLAGS = Font.BOLD;
    private static final int HEADER_FONT_SIZE = 25;
    private static final String BUTTON_FONT = "Arial";
    private static final int BUTTON_FONT_FLAGS = Font.BOLD;
    private static final int BUTTON_FONT_SIZE = 12;
    private final MainScreenListener listener;

    public MainScreen(MainScreenListener listener) {
        this.listener = listener;
        this.setLayout(null);
        this.setBackground(new Color(20, 20, 20));
        this.createHeader();
        this.createButtons();
    }

    private void createHeader() {
        JLabel menuLabel = new JLabel(HEADER_TITLE, JLabel.CENTER);
        Font labelFont = new Font(HEADER_FONT, HEADER_FONT_FLAGS, HEADER_FONT_SIZE);
        menuLabel.setBounds(373, 80, 150, 30);
        menuLabel.setFont(labelFont);
        menuLabel.setForeground(Color.WHITE);
        this.add(menuLabel);
    }

    public void createButtons() {
        this.createButton("Play",          350, 200, 200, 30, e -> this.listener.showPlayScreen());
        this.createButton("Configuration", 350, 275, 200, 30, e -> this.listener.showConfigurationScreen());
        this.createButton("High Scores",   350, 350, 200, 30, e -> this.listener.showHighScoresScreen());
        this.createButton("Exit",          350, 425, 200, 30, e -> showExitConfirmation());
    }

    private void createButton(String text, int x, int y, int width, int height, ActionListener action) {
        Font buttonFont = new Font(BUTTON_FONT, BUTTON_FONT_FLAGS, BUTTON_FONT_SIZE);
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(144, 238, 144));
        button.addActionListener(action);
        this.add(button);
    }

    private void showExitConfirmation() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
