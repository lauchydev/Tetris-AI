    package main.configuration;

    import main.ui.BasicScreen;
    import main.ui.MainScreenListener;

    import javax.swing.*;
    import java.awt.*;

    public class ConfigurationScreen extends BasicScreen {

        private final Configuration config;

        public ConfigurationScreen(MainScreenListener listener, Configuration config) {
            super(listener, "");
            this.config = config;
            this.setBackground(new Color(20, 20, 20));

            JLabel titleLabel = new JLabel("Configuration");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBounds(390, 20, 300, 80);
            this.add(titleLabel);


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
                isSelected = this.config.getMusicOn();
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
            this.add(checkbox);
            this.add(valueLabel);
            this.add(titleLabel);
            checkbox.addItemListener(e -> {
                boolean selected = checkbox.isSelected();
                valueLabel.setText(selected ? "On" : "Off");
                if (title.equals("Music (On|Off):")) {
                    Music.toggleMusic(selected);
                    this.config.setMusicOn(selected);
                }
            });
        }

        private void createSlider(String title, int min, int max, int value, int pos) {
            JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
            JLabel titleLabel = new JLabel(title);
            JLabel valueLabel = new JLabel(Integer.toString(slider.getValue()));

            int y = 100 + 60 * pos;
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
            this.add(titleLabel);
            this.add(valueLabel);
            this.add(slider);
        }

    }
