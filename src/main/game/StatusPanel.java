package main.game;

import main.configuration.ConfigObserver;
import main.configuration.Configuration;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel implements ConfigObserver {
    private static final Font FONT = new Font("Arial", Font.PLAIN, 22);
    private final JLabel music = new JLabel();
    private final JLabel sound = new JLabel();
    private final Configuration config = Configuration.getInstance();

    public StatusPanel() {
        super();
        setOpaque(false);
        add(music);
        add(sound);
        config.addObserver(this);
        music.setForeground(Color.WHITE);
        music.setFont(FONT);
        sound.setForeground(Color.WHITE);
        sound.setFont(FONT);
        configChanged();
    }


    @Override
    public void configChanged() {
        music.setText("Music: " + (config.getMusicOn() ? "ON" : "OFF"));
        sound.setText("Sound: " + (config.getSoundOn() ? "ON" : "OFF"));
    }
}
