package main;

import main.configuration.Configuration;
import main.configuration.Music;
import main.game.PlayScreen;
import main.highscores.HighScores;
import main.configuration.ConfigurationScreen;
import main.highscores.HighScoreScreen;
import main.ui.MainScreen;
import main.ui.SplashScreen;
import main.ui.MainScreenListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Tetris extends JFrame implements MainScreenListener {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    // Public variables to change the frame size
    public static int frameWidth = 900;
    public static int frameHeight = 600;

    private final Configuration config = new Configuration();
    private final HighScores highScores = new HighScores("data/scores.txt");

    private PlayScreen playScreen;


    public Tetris() {
        File directory = new File("data");
        //noinspection ResultOfMethodCallIgnored
        directory.mkdirs();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        MainScreen mainScreen = new MainScreen(this);
        HighScoreScreen highScoreScreen = new HighScoreScreen(this, this.highScores);
        ConfigurationScreen configurationScreen = new ConfigurationScreen(this, this.config);

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

    @Override
    public void showMainScreen() {
        this.stopGame();
        cardLayout.show(cardPanel, "MainScreen");
    }

    @Override
    public void showPlayScreen() {
        this.stopGame();
        this.playScreen = new PlayScreen(this, this.config);
        cardPanel.add(playScreen, "PlayScreen");
        cardLayout.show(cardPanel, "PlayScreen");
    }

    private void stopGame() {
        if (this.playScreen != null) {
            cardPanel.remove(this.playScreen);
            this.playScreen = null;
        }
    }

    @Override
    public void showConfigurationScreen() { cardLayout.show(cardPanel, "ConfigurationScreen"); }

    @Override
    public void showHighScoresScreen() {
        cardLayout.show(cardPanel, "HighScoreScreen");
    }

    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen(3000);
        splash.showSplashAndWait();
        Tetris tetris = new Tetris();
        tetris.showMainScreen();
    }
}