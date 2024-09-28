package main.configuration;

import main.ui.BasicScreen;
import java.awt.*;

public class ConfigurationScreen extends BasicScreen {

    public ConfigurationScreen() {
        super( "CONFIGURATION");
        ConfigurationPanel configurationPanel = new ConfigurationPanel();
        centerPanel.add(configurationPanel, BorderLayout.CENTER);
        centerPanel.setOpaque(false);
    }

}