package main.configuration;

import main.Tetris;
import main.ui.BasicScreen;

import javax.swing.*;

public class ConfigurationScreen extends BasicScreen {

    @FunctionalInterface
    private interface ValueUpdateAction<T> {
        void update(T value);
    }

    @FunctionalInterface
    private interface GetConfigValueAction<T> {
        T getConfigValue();
    }

    public ConfigurationScreen(Tetris parentFrame) {
        super(parentFrame, "Configuration");
        Configuration config = Configuration.getInstance();

        // Sliders
        createSlider("Field Width (No. of cells):", 5, 15, 0, config::getFieldWidth, config::setFieldWidth);
        createSlider("Field Height (No. of cells):", 15, 30, 1, config::getFieldHeight, config::setFieldHeight);
        createSlider("Game Level:", 1, 10, 2, config::getGameLevel, config::setGameLevel);

        // Check Boxes
        createCheckbox("Music (On|Off):", 0, config::getMusicOn, config::setMusicOn);
        createCheckbox("Sound Effect (On|Off):", 1, config::getSoundOn, config::setSoundOn);
        createCheckbox("AI Play (On|Off):", 2, config::getAIPlayOn, config::setAIPlayOn);
        createCheckbox("Extend Mode (On|Off):", 3, config::getExtendModeOn, config::setExtendModeOn);

    }

    private void updateCheckboxLabel(boolean val, JLabel valueLabel) {
        valueLabel.setText(val ? "On" : "Off");
    }

    // Modify the createCheckbox method in the ConfigurationScreen class
    private void createCheckbox(String title, int pos, GetConfigValueAction<Boolean> getFn, ValueUpdateAction<Boolean> updateAction) {
        JCheckBox checkbox = new JCheckBox();
        int y = 100 + 60 * (3 + pos);
        JLabel titleLabel = new JLabel(title);
        JLabel valueLabel = new JLabel();

        // Set the initial state of the checkbox based on the configuration
        boolean isSelected = getFn.getConfigValue();
        checkbox.setSelected(isSelected);
        updateCheckboxLabel(isSelected, valueLabel);

        checkbox.setBounds(250, y, 200, 50);
        valueLabel.setBounds(550, y, 20, 50);
        titleLabel.setBounds(50, y, 200, 50);
        add(checkbox);
        add(valueLabel);
        add(titleLabel);
        checkbox.addItemListener(e -> {
            boolean selected = checkbox.isSelected();
            updateAction.update(selected);
            updateCheckboxLabel(selected, valueLabel);
        });
    }

    private void updateSliderLabel(int val, JLabel valueLabel) {
        valueLabel.setText(Integer.toString(val));
    }

    private void createSlider(String title, int min, int max, int pos, GetConfigValueAction<Integer> getFn, ValueUpdateAction<Integer> updateAction) {
        var initValue = getFn.getConfigValue();
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initValue);
        JLabel titleLabel = new JLabel(title);
        JLabel valueLabel = new JLabel();
        int y = 100 + 60 * pos;
        slider.setBounds(250, y, 300, 50);
        valueLabel.setBounds(550, y, 20, 50);
        titleLabel.setBounds(50, y, 200, 50);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        updateSliderLabel(initValue, valueLabel);
        slider.addChangeListener(e -> {
            int val = slider.getValue();
            updateAction.update(val);
            updateSliderLabel(val, valueLabel);
        });
        add(titleLabel);
        add(valueLabel);
        add(slider);
    }

}