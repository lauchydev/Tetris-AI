    package main.configuration;

    import main.ui.BasicScreen;
    import main.ui.MainScreenListener;

    import javax.swing.*;
    import java.awt.*;

    public class ConfigurationScreen extends BasicScreen {

        private final Configuration config;
        private final JPanel configPanel = new JPanel();

        public ConfigurationScreen(MainScreenListener listener, Configuration config) {
            super(listener, "Configuration");
            this.config = config;
            configPanel.setLayout(null);
            configPanel.setOpaque(false);

            // Sliders
            createSlider("Field Width (No. of cells):", 5, 15, 10, 0);
            createSlider("Field Height (No. of cells):", 15, 30, 20, 1);
            createSlider("Game Level:", 1, 10, 1, 2);

            // Check Boxes
            createCheckbox("Music (On|Off):", 0);
            createCheckbox("Sound Effect (On|Off):", 1);
            createCheckbox("AI Play (On|Off):", 2);
            createCheckbox("Extend Mode (On|Off):", 3);
            add(configPanel, BorderLayout.CENTER);
            configPanel.setVisible(true);
        }



        // Modify the createCheckbox method in the ConfigurationScreen class
        private void createCheckbox(String title, int pos) {
            JCheckBox checkbox = new JCheckBox();
            int y = 60 * (3 + pos);
            JLabel titleLabel = new JLabel(title);
            JLabel valueLabel = new JLabel("Off");

            // Set the initial state of the checkbox based on the configuration
            boolean isSelected = false;
            if (title.equals("Music (On|Off):")) {
                isSelected = config.getMusicOn();
            }
            checkbox.setSelected(isSelected);
            valueLabel.setText(isSelected ? "On" : "Off");

            checkbox.setBackground(new Color(20, 20, 20));
            checkbox.setForeground(Color.WHITE);
            titleLabel.setForeground(Color.WHITE);
            valueLabel.setForeground(Color.WHITE);

            checkbox.setBounds(400, y, 50, 50);
            valueLabel.setBounds(450, y, 20, 50);
            titleLabel.setBounds(150, y, 200, 50);
            configPanel.add(checkbox);
            configPanel.add(valueLabel);
            configPanel.add(titleLabel);
            checkbox.addItemListener(e -> {
                boolean selected = checkbox.isSelected();
                valueLabel.setText(selected ? "On" : "Off");
                if (title.equals("Music (On|Off):")) {
                    Music.toggleMusic(selected);
                    config.setMusicOn(selected);
                }
            });
        }

        private void createSlider(String title, int min, int max, int value, int pos) {
            JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
            JLabel titleLabel = new JLabel(title);
            JLabel valueLabel = new JLabel(Integer.toString(slider.getValue()));

            int y = 60 * pos;
            slider.setBounds(400, y, 300, 50);
            valueLabel.setBounds(720, y, 20, 50);
            titleLabel.setBounds(150, y, 200, 50);
            slider.setMajorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);

            slider.setBackground(new Color(20, 20, 20));
            slider.setForeground(Color.WHITE);
            slider.setOpaque(true);
            titleLabel.setForeground(Color.WHITE);
            valueLabel.setForeground(Color.WHITE);

            slider.addChangeListener((e) -> valueLabel.setText(Integer.toString(slider.getValue())));
            configPanel.add(titleLabel);
            configPanel.add(valueLabel);
            configPanel.add(slider);
        }

    }
