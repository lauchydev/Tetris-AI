package main;

import main.configuration.Configuration;
import main.configuration.Music;
import main.game.PlayScreen;
import main.highscores.HighScores;
import main.configuration.ConfigurationScreen;
import main.highscores.HighScoreScreen;
import main.ui.MainScreen;
import main.ui.SplashScreen;

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

    private PlayScreen playScreen;


    public Tetris() {
        File directory = new File("data");
        //noinspection ResultOfMethodCallIgnored
        directory.mkdirs();
        this.showSplashScreen();

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

        // Start music if needed
        if (this.config.getMusicOn()) {
            Music.toggleMusic(true);
        }
    }



    private void initWindow() {
        this.setTitle("Tetris");
        this.setSize(frameWidth, frameHeight);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showSplashScreen() {
        main.ui.SplashScreen splash = new SplashScreen(3000);
        splash.showSplashAndWait();
    }

    public void showMainScreen() {
        this.stopGame();
        cardLayout.show(cardPanel, "MainScreen");
    }

    public void showPlayScreen() {
        this.stopGame();
        this.playScreen = new PlayScreen(this);
        cardPanel.add(playScreen, "PlayScreen");
        cardLayout.show(cardPanel, "PlayScreen");
    }

    private void stopGame() {
        if (this.playScreen != null) {
            cardPanel.remove(this.playScreen);
            this.playScreen = null;
        }
    }

    public void showConfigurationScreen() { cardLayout.show(cardPanel, "ConfigurationScreen"); }

    public void showHighScoresScreen() {
        cardLayout.show(cardPanel, "HighScoreScreen");
    }

    public static void main(String[] args) {
        new Tetris();
    }
}