package main.highscores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class HighScores {
    private final String filename;
    private final static String EMPTY_TEXT = "Empty : 0";

    public HighScores(String filename) {
        this.filename = filename;
    }
    public String[] getScores() {
        String [] scoresText = new String[10];
        try{
            File fileObject = new File(this.filename);
            Scanner fileReader = new Scanner(fileObject);

            for (int i = 0; i < 10; i++){
                if (fileReader.hasNextLine()) {
                    scoresText[i] = fileReader.nextLine();
                } else {
                    scoresText[i] = EMPTY_TEXT;
                }
            }
        } catch (FileNotFoundException e){
            this.createScoresFile();
            for (int i = 0; i < 10; i++){
                scoresText[i] = EMPTY_TEXT;
            }
        }

        return scoresText;
    }

    public void createScoresFile(){
        try {
            File scores = new File (this.filename);
            if (!scores.createNewFile()) {
                System.out.println("Couldn't create scores file.");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
