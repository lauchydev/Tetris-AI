package main;

import main.audio.Music;
import main.game.PlayScreen;
import main.configuration.ConfigurationScreen;
import main.highscores.HighScoreScreen;
import main.ui.MainScreen;
import main.ui.SplashScreen;
import main.ui.UI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Tetris extends JFrame {

    public static Tetris instance;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private static final int DEFAULT_FRAME_WIDTH = 900;
    private static final int DEFAULT_FRAME_HEIGHT = 700;

    private PlayScreen playScreen;


    public Tetris() {
        super();
        instance = this;
        File directory = new File("data");
        //noinspection ResultOfMethodCallIgnored
        directory.mkdirs();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        initWindow();
        add(cardPanel);

        // Create and add different cards
        MainScreen mainScreen = new MainScreen();
        cardPanel.add(mainScreen, "MainScreen");
        ConfigurationScreen configurationScreen = new ConfigurationScreen();
        cardPanel.add(configurationScreen, "ConfigurationScreen");
        HighScoreScreen highScoreScreen = new HighScoreScreen();
        cardPanel.add(highScoreScreen, "HighScoreScreen");

        Music.getInstance(); // so that our Music works
    }

    public void centerFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private void initWindow() {
        setTitle("Tetris");
        setDefaultSize();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                showExitConfirmation();
            }
        });
    }

    public void setDefaultSize() {
        setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        setLocationRelativeTo(null);
    }

    public void showExitConfirmation() {
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

    public void showErrorBox(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void showMainScreen() {
        stopGame();
        cardLayout.show(cardPanel, "MainScreen");
    }

    public void showPlayScreen() {
        stopGame();
        playScreen = new PlayScreen();
        cardPanel.add(playScreen, "PlayScreen");
        cardLayout.show(cardPanel, "PlayScreen");
    }

    private void stopGame() {
        if (playScreen != null) {
            cardPanel.remove(playScreen);
            playScreen = null;
        }
    }

    public void showConfigurationScreen() {
        cardLayout.show(cardPanel, "ConfigurationScreen");
    }

    public void showHighScoresScreen() {
        cardLayout.show(cardPanel, "HighScoreScreen");
    }

    public static void main(String[] args) {
        var loadThread = UI.loadImages();
        SplashScreen splash = new SplashScreen(3000);
        splash.showSplashAndWait();
        try {
            loadThread.join();
        } catch (InterruptedException e) {
            // it failed. Oh well, we don't care too much
        }
        SwingUtilities.invokeLater(() -> {
            Tetris tetris = new Tetris();
            tetris.showMainScreen();
        });
    }
}