import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends Panel {

    public MainScreen(Tetris tetris) {
        setLayout(null);

        add(new Label("Menu"));

        CreateButtons();


    }

    public void CreateButtons() {


        Button playButton = new Button("Play");
        playButton.setBounds(400, 100, 100, 30);


        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        Button configButton = new Button("Configuration");
        configButton.setBounds(400, 200, 100, 30);

        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show configuration screen (to be implemented)
            }
        });

        Button highScoresButton = new Button("High Scores");
        highScoresButton.setBounds(400, 300, 100, 30);

        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show high scores screen (to be implemented)
            }
        });

        Button exitButton = new Button("Exit");
        exitButton.setBounds(400, 400, 100, 30);


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