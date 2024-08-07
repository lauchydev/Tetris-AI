package main;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JPanel {

    /**
     The {@link parentFrame} is used to modify which screen is showing using methods the Tetris class (e.g {@link Tetris#showHighScoresScreen()}).
     */
    private Tetris parentFrame;


    public MainScreen(Tetris parentFrame) {
        this.parentFrame = parentFrame;
        this.setLayout(null);
        JLabel menuLabel = new JLabel("Main Menu", JLabel.CENTER);
        Font labelFont = new Font("Arial", Font.BOLD, 20);

        menuLabel.setBounds(400, 80, 105, 30);
        menuLabel.setFont(labelFont);

        this.add(menuLabel);

        CreateButtons();
    }

    public void CreateButtons() {
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        JButton playButton = new JButton("Play");
        playButton.setFont(buttonFont);
        playButton.setBounds(350, 200, 200, 30);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show play screen (to be implemented)
            }
        });

        JButton configButton = new JButton("Configuration");
        configButton.setFont(buttonFont);
        configButton.setBounds(350, 275, 200, 30);
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showConfigurationScreen();
            }
        });

        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setFont(buttonFont);
        highScoresButton.setBounds(350, 350, 200, 30);
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showHighScoresScreen();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(buttonFont);
        exitButton.setBounds(350, 425, 200, 30);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(playButton);
        this.add(configButton);
        this.add(highScoresButton);
        this.add(exitButton);
    }
}