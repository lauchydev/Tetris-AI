package main;

import main.configuration.Configuration;
import main.game.PlayScreen;
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

    private static final int MIN_FRAME_WIDTH = 900;
    private static final int MIN_FRAME_HEIGHT = 600;

    private final Configuration config = Configuration.getInstance();

    private PlayScreen playScreen;


    public Tetris() {
        File directory = new File("data");
        //noinspection ResultOfMethodCallIgnored
        directory.mkdirs();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        initWindow();
        add(cardPanel);

        // Create and add different cards
        MainScreen mainScreen = new MainScreen(this);
        cardPanel.add(mainScreen, "MainScreen");
        ConfigurationScreen configurationScreen = new ConfigurationScreen(this);
        cardPanel.add(configurationScreen, "ConfigurationScreen");
        HighScoreScreen highScoreScreen = new HighScoreScreen(this);
        cardPanel.add(highScoreScreen, "HighScoreScreen");
    }



    private void initWindow() {
        setTitle("Tetris");
        setSize(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT);
        setMinimumSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void showMainScreen() {
        stopGame();
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
        cardLayout.show(cardPanel, "ConfigurationScreen");
    }

    @Override
    public void showHighScoresScreen() {
        cardLayout.show(cardPanel, "HighScoreScreen");
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