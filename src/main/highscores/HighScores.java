package main.highscores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class HighScores {
    private static final HighScores instance = new HighScores("data/scores.json");

    private final String filename;
    private final static int MAX_SCORES = 10;
    private final static String EMPTY_TEXT = "Empty : 0";

    private HighScores(String filename) {
        this.filename = filename;
    }

    public static HighScores getInstance() {
        return instance;
    }

    // Fetch the high scores from the JSON file
    public List<ScoreEntry> getScores() {
        List<ScoreEntry> scoresList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            File file = new File(this.filename);

            // Check if the file exists, if not create it and initialize with empty scores
            if (!file.exists()) {
                createScoresFile(); // Create the file with empty scores
            }

            // Read from the JSON file
            Reader reader = new FileReader(file);

            // Check if the file is empty or invalid and handle it
            Type listType = new TypeToken<ArrayList<ScoreEntry>>() {}.getType();
            scoresList = gson.fromJson(reader, listType);

            // If the file is empty (null list), initialize with empty scores
            if (scoresList == null) {
                scoresList = new ArrayList<>();
                for (int i = 0; i < MAX_SCORES; i++) {
                    scoresList.add(new ScoreEntry("Empty", 0));
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            createScoresFile(); // Create the file with empty scores if not found
            for (int i = 0; i < MAX_SCORES; i++) {
                scoresList.add(new ScoreEntry("Empty", 0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scoresList;
    }

    // Save the high scores to the JSON file
    public void saveScores(List<ScoreEntry> scoresList) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = new FileWriter(this.filename);
            gson.toJson(scoresList, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create a new high score file if it doesn't exist
    public void createScoresFile() {
        try {
            File scoresFile = new File(this.filename);
            if (scoresFile.createNewFile()) {
                // Initialize with empty scores
                List<ScoreEntry> emptyScores = new ArrayList<>();
                for (int i = 0; i < MAX_SCORES; i++) {
                    emptyScores.add(new ScoreEntry("Empty", 0));
                }
                saveScores(emptyScores);
            } else {
                System.out.println("Couldn't create scores file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Class to represent each score entry
    public static class ScoreEntry {
        String name;
        int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public String getName() {
            return name;
        }
    }

    // Method to add a new high score
    public void addNewHighScore(String playerName, int score) {
        List<ScoreEntry> scoresList = getScores();
        scoresList.add(new ScoreEntry(playerName, score));

        // Sort using Comparator (descending order by score)
        scoresList.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());

        // Keep only top 10 scores
        if (scoresList.size() > MAX_SCORES) {
            scoresList.remove(scoresList.size() - 1); // Remove the lowest score
        }

        // Save the updated list back to the JSON file
        saveScores(scoresList);
    }
}