package main;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;


public class HighScoreScreen extends JPanel {

    private Tetris parentFrame;

    public HighScoreScreen(Tetris parentFrame) {
        this.parentFrame = parentFrame;
        this.setLayout(null);
        JLabel highScoreLabel = new JLabel("High Scores");
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        highScoreLabel.setFont(labelFont);

        // Calculate the width of the label string
        FontMetrics metrics = highScoreLabel.getFontMetrics(labelFont);
        int labelWidth = metrics.stringWidth("High Scores");


        /*
         *  Using a public frameWidth variable from the {@link Tetris.frameWidth} to center the label
         */
        int panelWidth = Tetris.frameWidth;
        int xPosition = (panelWidth - labelWidth) / 2;

        highScoreLabel.setBounds(xPosition, 80, 200, 30);

        this.add(highScoreLabel);

        displayScores();
        createButtons();
    }

    public void displayScores(){
        JLabel [] scoresLabel = new JLabel[10];
        String [] scoresText = new String[10];
        Font scoresFont = new Font("Arial",Font.PLAIN, 16);

        //Reading Score Data from the file
        try{
            File fileObject = new File("data/scores.txt");
            Scanner fileReader = new Scanner(fileObject);

            for(int i = 0; i < 10; i++){
                if(fileReader.hasNextLine()){
                    scoresText[i] = fileReader.nextLine();
                }else{
                    scoresText[i] = "Empty : 0";
                }
            }
        } catch(FileNotFoundException e){
            parentFrame.createScoresAndConfig();
        }


        //Displaying the data on the screen
        for(int i = 0; i < 5; i++){
            scoresLabel[i] = new JLabel(scoresText[i]);
            scoresLabel[i].setFont(scoresFont);
            FontMetrics scoresFontMetrics = scoresLabel[i].getFontMetrics(scoresFont);
            int scoreLabelWidth = scoresFontMetrics.stringWidth("Testing");
            scoresLabel[i].setBounds(((Tetris.frameWidth -scoreLabelWidth)/2)-150, (i*60)+130, 100, 40);

        }
        for(int i = 0; i < 5; i++){
            scoresLabel[i+5] = new JLabel(scoresText[i+5], JLabel.RIGHT);
            scoresLabel[i+5].setFont(scoresFont);
            FontMetrics scoresFontMetrics = scoresLabel[i+5].getFontMetrics(scoresFont);
            int scoreLabelWidth = scoresFontMetrics.stringWidth("Testing");
            scoresLabel[i+5].setBounds(((Tetris.frameWidth-scoreLabelWidth)/2)+100, (i*60)+130, 100, 40);
        }

        //Adding the JPanel data to the JFrame
        for(int i = 0; i < 10; i++){
            this.add(scoresLabel[i]);
        }

    }

    public void createButtons() {
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setBounds(350, Tetris.frameHeight-175, 200, 30);
        backButton.addActionListener(e -> {
            parentFrame.showMainScreen();
        });

        this.add(backButton);
    }
}