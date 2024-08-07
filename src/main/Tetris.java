package main;

import main.configuration.ConfigurationScreen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Tetris extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Public variables to change the frame size
    public static int frameWidth = 900;
    public static int frameHeight = 600;

    public Tetris() {
        createScoresAndConfig();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        MainScreen mainScreen = new MainScreen(this);
        PlayScreen playScreen = new PlayScreen(this);
        HighScoreScreen highScoreScreen = new HighScoreScreen(this);
        ConfigurationScreen configurationScreen = new ConfigurationScreen(this);

        cardPanel.add(mainScreen, "MainScreen");
        cardPanel.add(playScreen, "PlayScreen");
        cardPanel.add(highScoreScreen, "HighScoreScreen");
        cardPanel.add(configurationScreen, "ConfigurationScreen");

        this.add(cardPanel);

        initWindow();

    }

    private void initWindow() {
        this.setTitle("Tetris");
        this.setSize(frameWidth, frameHeight);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void createScoresAndConfig(){
        try{

            File directory = new File("data");
            directory.mkdirs();

            File scores = new File ("data/scores.txt");
            scores.createNewFile();

            File config = new File("data/config.txt");
            config.createNewFile();


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void showMainScreen() {
        cardLayout.show(cardPanel, "MainScreen");
    }

    public void showPlayScreen() {
        cardLayout.show(cardPanel, "PlayScreen");
    }

    public void showConfigurationScreen() { cardLayout.show(cardPanel, "ConfigurationScreen"); }

    public void showHighScoresScreen() {
        cardLayout.show(cardPanel, "HighScoreScreen");
    }

    public static void main(String[] args) {
        new Tetris();
    }
}