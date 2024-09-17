package main.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Music {

    private static Music instance;
    private Clip clip;

    private Music() {
        try {
            File musicPath = new File("src/main.audio.soundfiles/theme.wav");
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            } else {
                System.out.println("File not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
}