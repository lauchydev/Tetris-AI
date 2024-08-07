package main;

import main.configuration.Configuration;
import main.highscores.HighScores;
import main.configuration.ConfigurationScreen;
import main.highscores.HighScoreScreen;
import main.ui.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Tetris extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    // Public variables to change the frame size
    public static int frameWidth = 900;
    public static int frameHeight = 600;

    public Configuration config = new Configuration();
    public HighScores highScores = new HighScores("data/scores.txt");

    public Tetris() {
        File directory = new File("data");
        directory.mkdirs();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        MainScreen mainScreen = new MainScreen(this);
        HighScoreScreen highScoreScreen = new HighScoreScreen(this);
        ConfigurationScreen configurationScreen = new ConfigurationScreen(this);

        cardPanel.add(mainScreen, "MainScreen");
        cardPanel.add(highScoreScreen, "HighScoreScreen");
        cardPanel.add(configurationScreen, "ConfigurationScreen");

        this.add(cardPanel);

        initWindow();

    }

    private void initWindow() {
        this.setTitle("Tetris");
        this.setSize(frameWidth, frameHeight);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showMainScreen() {
        cardLayout.show(cardPanel, "MainScreen");
    }

    public void showPlayScreen() {
        // To be implemented
    }

    public void showConfigurationScreen() { cardLayout.show(cardPanel, "ConfigurationScreen"); }

    public void showHighScoresScreen() {
        cardLayout.show(cardPanel, "HighScoreScreen");
    }

    public static void main(String[] args) {
        new Tetris();
    }
}