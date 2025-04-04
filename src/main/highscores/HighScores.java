package main.highscores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class HighScores {
    private static final HighScores instance = new HighScores("data/scores.json");

    private final String filename;
    private final static int MAX_SCORES = 10;
    private final static int MAX_NAME_LENGTH = 20;

    public static HighScores getInstance() { return instance; }

    private HighScores(String filename) {
        this.filename = filename;
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
                scoresList = getDefaultList();
            }

            reader.close();
        } catch (FileNotFoundException e) {
            createScoresFile(); // Create the file with empty scores if not found
            scoresList = getDefaultList();
        } catch (IOException e) {
            // silently fail, nobody needs to know
        }

        return scoresList;
    }

    private List<ScoreEntry> getDefaultList() {
        List<ScoreEntry> scoresList = new ArrayList<>();
        for (int i = 0; i < MAX_SCORES; i++) {
            scoresList.add(new ScoreEntry("----", 0, "----"));
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
            // silently fail, nobody needs to know
        }
    }

    // Create a new high score file if it doesn't exist
    public void createScoresFile() {
        try {
            File scoresFile = new File(this.filename);
            if (scoresFile.createNewFile()) {
                clear();
            } else {
                System.out.println("Couldn't create scores file.");
            }
        } catch (IOException e) {
            // silently fail, nobody needs to know
        }
    }

    public void clear() {
        // Initialize with empty scores
        saveScores(getDefaultList());
    }

    // Class to represent each score entry
    public record ScoreEntry(String name, int score, String config) { }

    // Method to add a new high score
    public void addNewHighScore(String playerName, int score, String config) {
        List<ScoreEntry> scoresList = getScores();
        scoresList.add(new ScoreEntry(playerName, score, config));

        // Sort using Comparator (descending order by score)
        scoresList.sort(Comparator.comparingInt(ScoreEntry::score).reversed());

        // Keep only top 10 scores
        if (scoresList.size() > MAX_SCORES) {
            scoresList.removeLast(); // Remove the lowest score
        }

        // Save the updated list back to the JSON file
        saveScores(scoresList);
    }

    public static synchronized void checkScore(int playerScore, String config) {
        for (var entry : HighScores.getInstance().getScores()) {
            if (playerScore > entry.score()) {
                String name = JOptionPane.showInputDialog(
                        null,
                        "You made it to the high scores list! Enter your name (Only Alphanumeric Characters):",
                        "High Score!",
                        JOptionPane.PLAIN_MESSAGE
                );
                // Some String manipulation to ensure the name doesn't contain weird stuff
                name = name.strip()
                        .substring(0, Math.min(name.length(), MAX_NAME_LENGTH))
                        .replaceAll("[^a-zA-Z0-9]", "");

                if (name.isEmpty()) {
                    name = "Anonymous";
                }
                HighScores.getInstance().addNewHighScore(name, playerScore, config);
                break;
            }
        }
    }
}