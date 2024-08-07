package main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ConfigurationScreen extends JPanel {


    private Tetris parentFrame;


    public ConfigurationScreen(Tetris parentFrame) {
        this.parentFrame = parentFrame;
        this.setLayout(null);
        JLabel configurationLabel = new JLabel("Configuration");
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        configurationLabel.setFont(labelFont);

        // Calculate the width of the label string
        FontMetrics metrics = configurationLabel.getFontMetrics(labelFont);
        int labelWidth = metrics.stringWidth("Configuration");


        /**
         *  Using a public frameWidth variable from the {@link Tetris.frameWidth} to center the label
         */
        int panelWidth = Tetris.frameWidth;
        int xPosition = (panelWidth - labelWidth) / 2;
        configurationLabel.setBounds(xPosition, 40, 200, 30);
        this.add(configurationLabel);

        // Sliders
        JSlider fieldWidthSlider = new JSlider(JSlider.HORIZONTAL, 5, 15, 10);
        JSlider fieldHeightSlider = new JSlider(JSlider.HORIZONTAL, 15, 30, 20);
        JSlider gameLevelSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);

        modifySlider(fieldWidthSlider, "Field Width (No. of cells):", "fieldWidthSlider");
        modifySlider(fieldHeightSlider, "Field Height (No. of cells):", "fieldHeightSlider");
        modifySlider(gameLevelSlider, "Game Level:", "gameLevelSlider");

        // Check Boxes
        JCheckBox musicCheckbox = new JCheckBox();
        JCheckBox soundEffectCheckBox = new JCheckBox();
        JCheckBox aiPlayCheckBox = new JCheckBox();
        JCheckBox extendModeCheckBox = new JCheckBox();

        modifyToggle(musicCheckbox, "musicCheckbox (On|Off):", "musicCheckbox");
        modifyToggle(soundEffectCheckBox, "Sound Effect (On|Off):", "soundEffectCheckBox");
        modifyToggle(aiPlayCheckBox, "AI Play (On|Off):", "aiPlayCheckBox");
        modifyToggle(extendModeCheckBox, "Extend Mode (On|Off):", "extendModeCheckBox");

        // TODO: Add actions to each of the above toggles/sliders






        // Back Button
        backButton();
    }




    public void backButton() {
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setBounds(350, Tetris.frameHeight-60, 200, 30);
        backButton.addActionListener(e -> {
            parentFrame.showMainScreen();
        });

        this.add(backButton);
    }



    public void modifySlider(JSlider slider, String name, String id)
    {
        JLabel label = new JLabel(name);
        JLabel value = new JLabel(Integer.toString(slider.getValue()));
        int y = 100;
        int spacer = 60;
        switch (id)
        {
            case "fieldWidthSlider":
                slider.setBounds(250, y, 300, 50);
                value.setBounds(550, y, 20, 50);
                label.setBounds(50, y, 200, 50);
                break;

            case "fieldHeightSlider":
                y += spacer;
                slider.setBounds(250, y, 300, 50);
                value.setBounds(550, y, 20, 50);
                label.setBounds(50, y, 200, 50);
                break;

            case "gameLevelSlider":
                y += spacer * 2;
                slider.setBounds(250, y, 300, 50);
                value.setBounds(550, y, 20, 50);
                label.setBounds(50, y, 200, 50);
                break;
        }

        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                value.setText(Integer.toString(slider.getValue()));
            }
        });

        this.add(label);
        this.add(value);
        this.add(slider);
    }


    public void modifyToggle(JCheckBox button, String name, String id)
    {
        JLabel label = new JLabel(name);
        JLabel value = new JLabel("Off");

        button.addItemListener(e -> {
            value.setText((button.isSelected()) ? "On" : "Off");
        });

        int y = 100;
        int spacer = 60;
        switch (id)
        {
            case "musicCheckbox":
                y += spacer * 3;
                button.setBounds(250, y, 200, 50);
                value.setBounds(550, y, 20, 50);
                label.setBounds(50, y, 200, 50);
                break;

            case "soundEffectCheckBox":
                y += spacer * 4;
                button.setBounds(250, y, 200, 50);
                value.setBounds(550, y, 20, 50);
                label.setBounds(50, y, 200, 50);
                break;

            case "aiPlayCheckBox":
                y += spacer * 5;
                button.setBounds(250, y, 200, 50);
                value.setBounds(550, y, 20, 50);
                label.setBounds(50, y, 200, 50);
                break;

            case "extendModeCheckBox":
                y += spacer * 6;
                button.setBounds(250, y, 200, 50);
                value.setBounds(550, y, 20, 50);
                label.setBounds(50, y, 200, 50);
                break;
        }
        this.add(button);
        this.add(value);
        this.add(label);
    }
}
