package main.configuration;

import main.Tetris;
import main.ui.BasicScreen;

import javax.swing.*;
import java.awt.*;

public class ConfigurationScreen extends BasicScreen {

    private final ConfigurationPanel configurationPanel;

    public ConfigurationScreen() {
        super( "Configuration");
        configurationPanel = new ConfigurationPanel();
        centerPanel.add(configurationPanel, BorderLayout.CENTER);
        centerPanel.setOpaque(false);
    }


    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
            configurationPanel.setVisible(true);// annoying hack for pack() to work
        } else {
            setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            configurationPanel.setVisible(false);// annoying hack for pack() to work
        }
        super.setVisible(visible);
    }

}