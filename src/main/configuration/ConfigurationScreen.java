package main.configuration;

import main.Tetris;
import main.ui.BasicScreen;

import javax.swing.*;
import java.awt.*;

public class ConfigurationScreen extends BasicScreen {

    public ConfigurationScreen(Tetris parentFrame) {
        super(parentFrame, "Configuration");
        ConfigurationPanel configurationPanel = new ConfigurationPanel();
        add(configurationPanel, BorderLayout.CENTER);
    }


    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        } else {
            setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        }
        super.setVisible(visible);
    }

}