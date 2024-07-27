package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class MainScreen extends Panel {

    public MainScreen(Tetris tetris) {
        setLayout(null);


        Label menuLabel = new Label("Main Menu", Label.CENTER);
        menuLabel.setBounds(400, 80, 105, 30);
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        menuLabel.setFont(labelFont);
        add(menuLabel);

        CreateButtons();


    }

    public void CreateButtons() {

        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        Button playButton = new Button("Play");
        playButton.setFont(buttonFont);
        playButton.setBounds(350, 200, 200, 30);


        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        Button configButton = new Button("Configuration");
        configButton.setFont(buttonFont);
        configButton.setBounds(350, 275, 200, 30);

        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show configuration screen (to be implemented)
            }
        });

        Button highScoresButton = new Button("High Scores");
        highScoresButton.setFont(buttonFont);
        highScoresButton.setBounds(350, 350, 200, 30);

        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show high scores screen (to be implemented)
            }
        });

        Button exitButton = new Button("Exit");
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