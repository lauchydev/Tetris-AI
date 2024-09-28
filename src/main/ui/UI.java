package main.ui;

import main.utils.TextUtils;

import javax.swing.*;
import java.awt.*;

public class UI {
    private static final Font HEADER_FONT = TextUtils.getFont(25);
    private static final Font BUTTON_FONT = TextUtils.getFont(20);
    private static final Color BUTTON_BACKGROUND_COLOUR = new Color(25, 103, 165);
    private static final Font SECONDARY_BUTTON_FONT = TextUtils.getFont(12);
    private static final Color SECONDARY_BUTTON_BACKGROUND_COLOUR = new Color(238, 144, 144);

    private static final Font MENU_BUTTON_FONT = TextUtils.getFont(13);
    private static final Dimension MENU_BUTTON_DIMENSION = new Dimension(213, 30);

    private static final Font GAME_INFO_HEADER_FONT = TextUtils.getFont(12);

    private static final Font SCORES_FONT = TextUtils.getFont(16);

    public static JButton createSecondaryButton(String text) {
        JButton button = createButton(text, SECONDARY_BUTTON_FONT);
        button.setBackground(SECONDARY_BUTTON_BACKGROUND_COLOUR);
        return button;
    }

    public static JButton createButton(String text) {
        return createButton(text, BUTTON_FONT);
    }
    public static JButton createButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(BUTTON_BACKGROUND_COLOUR);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    public static JButton createMenuButton(String text) {
        JButton button = createButton(text, MENU_BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(MENU_BUTTON_DIMENSION);
        return button;
    }

    public static JLabel createGameInfoHeaderLabel() {
        return createGameInfoHeaderLabel("");
    }
    public static JLabel createGameInfoHeaderLabel(String text) {
        return createHeaderLabel(text, GAME_INFO_HEADER_FONT);
    }


    public static JLabel createHeaderLabel(String text) {
        return createHeaderLabel(text, HEADER_FONT);
    }
    public static JLabel createHeaderLabel(String text, Font font) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        return label;
    }

    public static JLabel createHighScoresLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SCORES_FONT);
        label.setForeground(Color.WHITE);
        return label;
    }
}
