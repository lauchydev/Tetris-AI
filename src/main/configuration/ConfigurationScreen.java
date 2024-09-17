package main.configuration;

import main.Tetris;
import main.audio.Music;
import main.ui.BasicScreen;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ConfigurationScreen extends BasicScreen {
    private final Configuration config = Configuration.getInstance();
    private final Music music = Music.getInstance();

    public ConfigurationScreen(Tetris parentFrame) {
        super(parentFrame, "Configuration");

        // Sliders
        this.createSlider("Field Width (No. of cells):", 5, 15, 10, 0);
        this.createSlider("Field Height (No. of cells):", 15, 30, 20, 1);
        this.createSlider("Game Level:", 1, 10, 1, 2);

        // Check Boxes
        this.createCheckbox("Music (On|Off):", 0);
        this.createCheckbox("Sound Effect (On|Off):", 1);
        this.createCheckbox("AI Play (On|Off):", 2);
        this.createCheckbox("Extend Mode (On|Off):", 3);

    }


    // Modify the createCheckbox method in the ConfigurationScreen class
    private void createCheckbox(String title, int pos) {
        JCheckBox checkbox = new JCheckBox();
        int y = 100 + 60 * (3 + pos);
        JLabel titleLabel = new JLabel(title);
        JLabel valueLabel = new JLabel("Off");

        // Set the initial state of the checkbox based on the configuration
        boolean isSelected = false;
        if (title.equals("Music (On|Off):")) {
            isSelected = config.getMusicOn();
        }


        checkbox.setSelected(isSelected);
        valueLabel.setText(isSelected ? "On" : "Off");

        checkbox.setBounds(250, y, 200, 50);
        valueLabel.setBounds(550, y, 20, 50);
        titleLabel.setBounds(50, y, 200, 50);
        this.add(checkbox);
        this.add(valueLabel);
        this.add(titleLabel);
        checkbox.addItemListener(e -> {
            boolean selected = checkbox.isSelected();
            valueLabel.setText(selected ? "On" : "Off");

            switch (title) {
                case "Music (On|Off):":
                    config.setMusicOn(selected);
                    if (selected) {
                        music.start();
                    } else {
                        music.stop();
                    }
                    break;
                case "Sound Effect (On|Off):":
                    config.setSoundOn(selected);
                    break;
                case "AI Play (On|Off):":
                    config.setAIPlayOn(selected);
                    break;
                case "Extend Mode (On|Off):":
                    config.setExtendModeOn(selected);
                    break;
            }
        });
    }

    private void createSlider(String title, int min, int max, int value, int pos) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
        JLabel titleLabel = new JLabel(title);
        JLabel valueLabel = new JLabel(Integer.toString(slider.getValue()));
        int y = 100 + 60 * pos;
        slider.setBounds(250, y, 300, 50);
        valueLabel.setBounds(550, y, 20, 50);
        titleLabel.setBounds(50, y, 200, 50);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                valueLabel.setText(Integer.toString(slider.getValue()));
            }
        });
        this.add(titleLabel);
        this.add(valueLabel);
        this.add(slider);
    }

}