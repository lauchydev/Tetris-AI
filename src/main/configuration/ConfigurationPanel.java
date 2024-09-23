package main.configuration;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel extends JPanel implements ConfigObserver {

    Configuration config = Configuration.getInstance();
    JPanel playerTwoPanel;

    @Override
    public void configChanged() {
        applyExtendedMode();
    }

    private void applyExtendedMode() {
        boolean extOn = config.getExtendModeOn();
        playerTwoPanel.setEnabled(extOn);
        for (Component comp : playerTwoPanel.getComponents()) {
            comp.setEnabled(extOn);
        }
    }

    @FunctionalInterface
    private interface ValueUpdateAction<T> {
        void update(T value);
    }

    @FunctionalInterface
    private interface GetConfigValueAction<T> {
        T getConfigValue();
    }

    ConfigurationPanel() {
        config.addObserver(this);
        setLayout(new GridLayout(9, 3));
        setOpaque(false);

        // Sliders
        createSlider("Field Width (No. of cells):", 5, 15, config::getFieldWidth, config::setFieldWidth);
        createSlider("Field Height (No. of cells):", 15, 30, config::getFieldHeight, config::setFieldHeight);
        createSlider("Game Level:", 1, 10, config::getGameLevel, config::setGameLevel);

        // Check Boxes
        createCheckbox("Music (On|Off):", config::getMusicOn, config::setMusicOn);
        createCheckbox("Sound Effect (On|Off):", config::getSoundOn, config::setSoundOn);
        createCheckbox("AI Play (On|Off):", config::getAIPlayOn, config::setAIPlayOn);
        createCheckbox("Extend Mode (On|Off):", config::getExtendModeOn, config::setExtendModeOn);

        // Radio buttons
        createRadioButton("Player One Type", config::getPlayerOneType, config::setPlayerOneType);
        playerTwoPanel = createRadioButton("Player Two Type", config::getPlayerTwoType, config::setPlayerTwoType);
        applyExtendedMode();
    }

    private JLabel createTitleLabel(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        return titleLabel;
    }

    private JLabel createValueLabel() {
        JLabel titleLabel = new JLabel();
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        return titleLabel;
    }

    private void updateCheckboxLabel(boolean val, JLabel valueLabel) {
        valueLabel.setText(val ? "On" : "Off");
    }

    // Modify the createCheckbox method in the ConfigurationScreen class
    private void createCheckbox(String title, GetConfigValueAction<Boolean> getFn, ValueUpdateAction<Boolean> updateAction) {
        JCheckBox checkbox = new JCheckBox();
        checkbox.setOpaque(false);
        JLabel titleLabel = createTitleLabel(title);
        JLabel valueLabel = createValueLabel();

        // Set the initial state of the checkbox based on the configuration
        boolean isSelected = getFn.getConfigValue();
        checkbox.setSelected(isSelected);
        updateCheckboxLabel(isSelected, valueLabel);

        add(titleLabel);
        add(checkbox);
        add(valueLabel);
        checkbox.addItemListener(e -> {
            boolean selected = checkbox.isSelected();
            updateAction.update(selected);
            updateCheckboxLabel(selected, valueLabel);
        });
    }

    private void updateSliderLabel(int val, JLabel valueLabel) {
        valueLabel.setText(Integer.toString(val));
    }

    private void createSlider(String title, int min, int max, GetConfigValueAction<Integer> getFn, ValueUpdateAction<Integer> updateAction) {
        var initValue = getFn.getConfigValue();
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initValue);
        slider.setForeground(Color.WHITE);
        slider.setOpaque(false);
        JLabel titleLabel = createTitleLabel(title);
        JLabel valueLabel = createValueLabel();
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
        add(slider);
        add(valueLabel);
    }

    private JPanel createRadioButton(String title, GetConfigValueAction<PlayerType> getFn, ValueUpdateAction<PlayerType> updateAction) {
        var initValue = getFn.getConfigValue();
        JLabel titleLabel = createTitleLabel(title);
        JPanel options = new JPanel();
        options.setOpaque(false);
        ButtonGroup group = new ButtonGroup();
        for (PlayerType type : PlayerType.values()) {
            JRadioButton radio = new JRadioButton(type.getFriendlyName());
            radio.setSelected(initValue == type);
            radio.setOpaque(false);
            radio.setForeground(Color.WHITE);
            radio.addActionListener(e -> updateAction.update(type));
            group.add(radio);
            options.add(radio);
        }
        add(titleLabel);
        add(options);
        add(Box.createGlue());
        return options;
    }

}
