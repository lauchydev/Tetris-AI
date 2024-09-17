package main.configuration;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private static Configuration instance;
    private final Map<String, Integer> intMap = new HashMap<>();
    private final Map<String, Boolean> booleanMap = new HashMap<>();

    private Configuration() {
        this.intMap.put("fieldWidth", 10);
        this.intMap.put("fieldHeight", 20);
        this.intMap.put("gameLevel", 1);

        this.booleanMap.put("music", getMusicOn());
        this.booleanMap.put("sound", getSoundOn());
        this.booleanMap.put("aiPlay", getAIPlayOn());
        this.booleanMap.put("extendMode", getExtendModeOn());
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public int getFieldWidth() { return this.getInt("fieldWidth", 10); }
    public int getFieldHeight() { return this.getInt("fieldHeight", 20); }
    public int getGameLevel() { return this.getInt("gameLevel", 1); }

    public boolean getMusicOn() { return this.getBoolean("music", false); }
    public boolean getSoundOn() { return this.getBoolean("sound", false); }
    public boolean getAIPlayOn() { return this.getBoolean("aiPlay", false); }
    public boolean getExtendModeOn() { return this.getBoolean("extendMode", false); }

    public void setMusicOn(Boolean value) { this.setBoolean("music", value); }
    public void setSoundOn(Boolean value) { this.setBoolean("sound", value); }
    public void setAIPlayOn(Boolean value) { this.setBoolean("aiPlay", value); }
    public void setExtendModeOn(Boolean value) { this.setBoolean("extendMode", value); }

    private int getInt(String key, int defaultValue) { return this.intMap.getOrDefault(key, defaultValue); }
    private void setInt(String key, int value) {  this.intMap.put(key, value);  }
    private boolean getBoolean(String key, Boolean defaultValue) { return this.booleanMap.getOrDefault(key, defaultValue); }
    private void setBoolean(String key, Boolean value) {  this.booleanMap.put(key, value);  }
}