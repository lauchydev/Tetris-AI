package main.ui;

import main.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BasicScreen extends JPanel {

    private static final String FONT_NAME = "Arial";
    private static final int FONT_FLAGS = Font.BOLD;
    private static final int HEADER_FONT_SIZE = 20;
    private static final int BACK_FONT_SIZE = 20;
    private static final String BACK_BUTTON_TEXT = "Back";
    protected final JButton backButton;
    private final MainScreenListener listener;

    public BasicScreen(MainScreenListener listener, String title) {
        this.listener = listener;
        this.setLayout(null);
        this.createHeader(title);
        this.backButton = this.createBackButton();
        this.add(this.backButton);
    }

    protected void onBackButtonClicked(ActionEvent e) {
        this.listener.showMainScreen();
    }

    private JButton createBackButton() {
        Font buttonFont = new Font(FONT_NAME, FONT_FLAGS, BACK_FONT_SIZE);
        JButton backButton = new JButton(BACK_BUTTON_TEXT);
        backButton.setFont(buttonFont);
        backButton.setBounds(350, Tetris.frameHeight - 100, 200, 30);
        backButton.setBackground(new Color(144, 238, 144));
        backButton.addActionListener(e -> this.onBackButtonClicked(e));
        return backButton;
    }

    private void createHeader(String title) {
        JLabel headerLabel = new JLabel(title);
        Font labelFont = new Font(FONT_NAME, FONT_FLAGS, HEADER_FONT_SIZE);
        headerLabel.setFont(labelFont);

        // Calculate the width of the label string
        FontMetrics metrics = headerLabel.getFontMetrics(labelFont);
        int labelWidth = metrics.stringWidth(title);

        /*
         *  Using a public frameWidth variable from the {@link Tetris.frameWidth} to center the label
         */
        int panelWidth = Tetris.frameWidth;
        int xPosition = (panelWidth - labelWidth) / 2;
        headerLabel.setBounds(xPosition, 50, 200, 30);
        this.add(headerLabel);
    }
}
