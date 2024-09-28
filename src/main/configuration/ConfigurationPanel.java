package main.configuration;

import main.ui.UI;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel extends JPanel implements ConfigObserver {

    Configuration config = Configuration.getInstance();
    JPanel playerTwoPanel;
    private int currentRow = 0;

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
        setLayout(new GridBagLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(700, 400));

        // Sliders
        createSlider("Field Width:", 5, 15, config::getFieldWidth, config::setFieldWidth);
        createSlider("Field Height:", 15, 30, config::getFieldHeight, config::setFieldHeight);
        createSlider("Game Level:", 1, 10, config::getGameLevel, config::setGameLevel);

        // Check Boxes
        createCheckbox("Music:", config::getMusicOn, config::setMusicOn);
        createCheckbox("Sound Effect:", config::getSoundOn, config::setSoundOn);
        createCheckbox("Extend Mode:", config::getExtendModeOn, config::setExtendModeOn);

        // Radio buttons
        createRadioButton("Player One Type:", () -> config.getPlayerType(1), type -> config.setPlayerType(1, type));
        playerTwoPanel = createRadioButton("Player Two Type:", () -> config.getPlayerType(2), type -> config.setPlayerType(2, type));
        applyExtendedMode();
    }

    private JLabel createTitleLabel(String title) {
        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setFont(UI.getFont(12));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
        return titleLabel;
    }

    private JLabel createValueLabel() {
        JLabel valueLabel = new JLabel();
        valueLabel.setFont(UI.getFont(12));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        return valueLabel;
    }

    private void updateCheckboxLabel(boolean val, JLabel valueLabel) {
        valueLabel.setText(val ? "ON" : "OFF");
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

        GridBagConstraints gbc = getBasicGBC();
        add(titleLabel, gbc);
        gbc.gridx = 1;
        add(checkbox, gbc);
        gbc.gridx = 2;
        add(valueLabel, gbc);
        currentRow++;
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
        slider.setFont(UI.getFont(7));
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
        GridBagConstraints gbc = getBasicGBC();
        add(titleLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 2;
        add(slider, gbc);
        gbc.gridx = 2;
        gbc.weightx = 1;
        add(valueLabel, gbc);
        currentRow++;
    }

    private JPanel createRadioButton(String title, GetConfigValueAction<PlayerType> getFn, ValueUpdateAction<PlayerType> updateAction) {
        var initValue = getFn.getConfigValue();
        JLabel titleLabel = createTitleLabel(title);
        JPanel options = new JPanel();
        options.setOpaque(false);
        ButtonGroup group = new ButtonGroup();
        for (PlayerType type : PlayerType.values()) {
            JRadioButton radio = new JRadioButton(type.toString());
            radio.setFont(UI.getFont(12));
            radio.setSelected(initValue == type);
            radio.setOpaque(false);
            radio.setForeground(Color.WHITE);
            radio.addActionListener(e -> updateAction.update(type));
            group.add(radio);
            options.add(radio);
        }
        GridBagConstraints gbc = getBasicGBC();
        add(titleLabel, gbc);
        gbc.gridx = 1;
        add(options, gbc);
        currentRow++;
        return options;
    }

    private GridBagConstraints getBasicGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = currentRow;
        gbc.weightx = 1.0;
        gbc.ipady = 10;
        return gbc;
    }

}
