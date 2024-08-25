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

    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;

    private final Configuration config = new Configuration();
    private final HighScores highScores = new HighScores("data/scores.txt");

    private PlayScreen playScreen;


    public Tetris() {
        File directory = new File("data");
        //noinspection ResultOfMethodCallIgnored
        directory.mkdirs();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        initWindow();
        add(cardPanel);

        // Start music if needed
        if (config.getMusicOn()) {
            Music.toggleMusic(true);
        }
    }



    private void initWindow() {
        setTitle("Tetris");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void showMainScreen() {
        stopGame();
        MainScreen mainScreen = new MainScreen(this);
        cardPanel.add(mainScreen, "MainScreen");
        cardLayout.show(cardPanel, "MainScreen");
    }

    @Override
    public void showPlayScreen() {
        stopGame();
        playScreen = new PlayScreen(this, config);
        cardPanel.add(playScreen, "PlayScreen");
        cardLayout.show(cardPanel, "PlayScreen");
    }

    private void stopGame() {
        if (playScreen != null) {
            cardPanel.remove(playScreen);
            playScreen = null;
        }
    }

    @Override
    public void showConfigurationScreen() {
        ConfigurationScreen configurationScreen = new ConfigurationScreen(this, config);
        cardPanel.add(configurationScreen, "ConfigurationScreen");
        cardLayout.show(cardPanel, "ConfigurationScreen");
    }

    @Override
    public void showHighScoresScreen() {
        SwingUtilities.invokeLater(() -> {
            HighScoreScreen highScoreScreen = new HighScoreScreen(this, highScores);
            cardPanel.add(highScoreScreen, "HighScoreScreen");
            cardLayout.show(cardPanel, "HighScoreScreen");
        });
    }

    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen(3000);
        splash.showSplashAndWait();
        SwingUtilities.invokeLater(() -> {
            Tetris tetris = new Tetris();
            tetris.showMainScreen();
        });
    }
}