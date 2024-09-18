package main.audio;

import main.configuration.Configuration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundEffects {
    private static Clip clip;
    private static final Configuration config = Configuration.getInstance();


    private SoundEffects() {}

    public static void playEffect(Effect soundEffect) {
        if (!config.getSoundOn()) {
            return;
        }
        try {
            File soundPath = new File(soundEffect.getPath());
            if (!soundPath.exists()) {
                System.out.println("Sound file not found");
            }
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Cannot load sound effect.");
        }
    }

}