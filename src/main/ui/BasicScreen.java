package main.ui;

import main.Tetris;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BasicScreen extends BasePanel {

    protected final JButton backButton;
    protected final JPanel centerPanel = new JPanel();


    public BasicScreen(String title) {
        super();
        setLayout(new BorderLayout());
        createHeader(title);
        backButton = createBackButton();
        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    protected void onBackButtonClicked(ActionEvent e) {
        Tetris.instance.showMainScreen();
    }

    private JButton createBackButton() {
        JButton backButton = UI.createButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        backButton.addActionListener(this::onBackButtonClicked);
        return backButton;
    }

    private void createHeader(String title) {
        JLabel headerLabel = UI.createHeaderLabel(title);
        headerLabel.setBorder(new EmptyBorder(30, 0, 30, 0));
        add(headerLabel, BorderLayout.NORTH);
    }
}
