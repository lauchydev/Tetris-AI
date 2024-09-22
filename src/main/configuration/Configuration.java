package main.configuration;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Configuration {
    private static Configuration instance;
    private static final String filename = "data/TetrisConfig.json";
    private static final ArrayList<ConfigObserver> observers = new ArrayList<>();

    // Configuration properties
    private int fieldWidth = 10;
    private int fieldHeight = 20;
    private int gameLevel = 1;
    private boolean music = false;
    private boolean sound = false;
    private boolean aiPlay = false;
    private boolean extendMode = false;
    private PlayerType player1Type = PlayerType.HUMAN;
    private PlayerType player2Type = PlayerType.HUMAN;

    public int getNumberOfPlayers() { return extendMode ? 2 : 1; }

    public static Configuration getInstance() {
        if (instance == null) {
            try {
                instance = load();
            } catch (Exception e) {
                // If anything goes wrong at all, create a default object
                instance = new Configuration();
            }
        }
        return instance;
    }

    public void propertyChanged() {
        save();
        notifyObservers();
    }

    private static Configuration load() throws IOException {
        Gson gson = new Gson();
        Path filePath = Paths.get(filename);
        String json = Files.readString(filePath);
        return gson.fromJson(json, Configuration.class);
    }

    private void save() {
        Gson gson = new Gson();
        String json = gson.toJson(this);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Error while saving config file");
        }
    }

    private void notifyObservers() {
        for (ConfigObserver obs : observers) {
            obs.configChanged();
        }
    }

    public void addObserver(ConfigObserver obs) {
        observers.add(obs);
    }

    public void removeObserver(ConfigObserver obs) {
        observers.remove(obs);
    }

    public int getFieldWidth() { return fieldWidth; }
    public int getFieldHeight() { return fieldHeight; }
    public int getGameLevel() { return gameLevel; }

    public void setFieldWidth(int value) { fieldWidth = value; propertyChanged(); }
    public void setFieldHeight(int value) { fieldHeight = value; propertyChanged(); }
    public void setGameLevel(int value) { gameLevel = value; propertyChanged(); }

    public boolean getMusicOn() { return music; }
    public boolean getSoundOn() { return sound; }
    public boolean getAIPlayOn() { return aiPlay; }
    public boolean getExtendModeOn() { return extendMode; }
    public PlayerType getPlayerOneType() { return player1Type; }
    public PlayerType getPlayerTwoType() { return player2Type; }

    public void setMusicOn(Boolean value) { music = value; propertyChanged(); }
    public void setSoundOn(Boolean value) { sound = value; propertyChanged(); }
    public void setAIPlayOn(Boolean value) { aiPlay = value; propertyChanged(); }
    public void setExtendModeOn(Boolean value) { extendMode = value; propertyChanged(); }
    public void setPlayerOneType(PlayerType type) { this.player1Type = type; propertyChanged(); }
    public void setPlayerTwoType(PlayerType type) { this.player2Type = type; propertyChanged(); }

}