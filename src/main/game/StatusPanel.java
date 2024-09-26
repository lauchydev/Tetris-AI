package main.game;

import main.configuration.ConfigObserver;
import main.configuration.Configuration;
import main.ui.UI;

import javax.swing.*;

public class StatusPanel extends JPanel implements ConfigObserver {
    private final JLabel music = UI.createGameInfoHeaderLabel();
    private final JLabel sound = UI.createGameInfoHeaderLabel();
    private final Configuration config = Configuration.getInstance();

    public StatusPanel() {
        super();
        setOpaque(false);
        add(music);
        add(sound);
        config.addObserver(this);
        configChanged();
    }


    @Override
    public void configChanged() {
        music.setText("Music: " + (config.getMusicOn() ? "ON" : "OFF"));
        sound.setText("Sound: " + (config.getSoundOn() ? "ON" : "OFF"));
    }
}
