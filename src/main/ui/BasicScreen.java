package main.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BasicScreen extends JPanel {

    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 20);
    protected final JButton backButton;
    private final MainScreenListener listener;

    public BasicScreen(MainScreenListener listener, String title) {
        this.listener = listener;
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 20));
        if (title != null) {
            createHeader(title);
        }
        
        backButton = createBackButton();
        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    protected void onBackButtonClicked(ActionEvent e) {
        listener.showMainScreen();
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setFont(BUTTON_FONT);
        backButton.setPreferredSize(new Dimension(200, 30));
        backButton.setBackground(new Color(144, 238, 144));
        backButton.addActionListener(this::onBackButtonClicked);
        return backButton;
    }

    private void createHeader(String title) {
        JLabel headerLabel = new JLabel(title, JLabel.CENTER);
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(new EmptyBorder(30, 0, 30, 0));
        add(headerLabel, BorderLayout.NORTH);
    }
}
