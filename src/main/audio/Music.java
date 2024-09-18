package main.audio;

import main.configuration.ConfigObserver;
import main.configuration.Configuration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Music implements ConfigObserver {

    private static Music instance;
    private Clip clip;
    private final Configuration config = Configuration.getInstance();

    private Music() {
        try {
            File musicPath = new File("src/sound/theme.wav");
            if (!musicPath.exists()) {
                System.out.println("Music file not found");
            }
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            config.addObserver(this);
            configChanged(); // Hack to make music setting apply initially
        } catch (Exception ex) {
            System.out.println("Cannot load music.");
        }
    }

    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

    public void start() {
        if (clip != null && !clip.isRunning()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Clip is null or already running");
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    @Override
    public void configChanged() {
        if (config.getMusicOn()) {
            start();
        } else {
            stop();
        }
    }
}