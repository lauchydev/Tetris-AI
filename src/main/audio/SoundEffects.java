package main.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundEffects {
    private static Clip clip;

    private SoundEffects() {}

    public static void playEffect(Effect soundEffect) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(soundEffect.toString())));
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}