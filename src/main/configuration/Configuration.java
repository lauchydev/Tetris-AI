package main.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Configuration {
    private static Configuration instance;
    private static final String filename = "data/TetrisConfig.json";
    private final ArrayList<ConfigObserver> observers = new ArrayList<>();
    private final static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Expose
    private int fieldWidth = 10;
    @Expose
    private int fieldHeight = 20;
    @Expose
    private int gameLevel = 1;
    @Expose
    private boolean music = false;
    @Expose
    private boolean sound = false;
    @Expose
    private boolean aiPlay = false;
    @Expose
    private boolean extendMode = false;

    public static Configuration getInstance() {
        if (instance == null) {
            Path filePath = Paths.get(filename);
            try {
                String json = Files.readString(filePath);
                instance = gson.fromJson(json, Configuration.class);
            } catch (IOException e) {
                // If anything goes wrong at all, create a default object
                instance = new Configuration();
            }
        }
        return instance;
    }

    public void propertyChanged() {
        saveConfig();
        notifyObservers();
    }

    private void saveConfig() {
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
        this.observers.add(obs);
    }

    public void removeObserver(ConfigObserver obs) {
        this.observers.remove(obs);
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

    public void setMusicOn(Boolean value) { music = value; propertyChanged(); }
    public void setSoundOn(Boolean value) { sound = value; propertyChanged(); }
    public void setAIPlayOn(Boolean value) { aiPlay = value; propertyChanged(); }
    public void setExtendModeOn(Boolean value) { extendMode = value; propertyChanged(); }

}