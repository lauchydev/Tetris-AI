package main.configuration;

import main.Tetris;
import main.ui.BasicScreen;

import javax.swing.*;
import java.awt.*;

public class ConfigurationScreen extends BasicScreen {

    public ConfigurationScreen(Tetris parentFrame) {
        super(parentFrame, "Configuration");
        ConfigurationPanel  configurationPanel = new ConfigurationPanel();
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // Padding
        add(configurationPanel, BorderLayout.CENTER);
    }



}