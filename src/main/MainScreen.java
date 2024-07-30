package main;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainScreen extends JPanel {

    public MainScreen(Tetris tetris) {
        setLayout(null);
        JLabel menuLabel = new JLabel("Main Menu", JLabel.CENTER);
        menuLabel.setBounds(400, 80, 105, 30);
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        menuLabel.setFont(labelFont);
        add(menuLabel);

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

            }
        });


        JButton configButton = new JButton("Configuration");
        configButton.setFont(buttonFont);
        configButton.setBounds(350, 275, 200, 30);
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show configuration screen (to be implemented)
            }
        });


        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setFont(buttonFont);
        highScoresButton.setBounds(350, 350, 200, 30);
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show high scores screen (to be implemented)
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

        add(playButton);
        add(configButton);
        add(highScoresButton);
        add(exitButton);

    }

}