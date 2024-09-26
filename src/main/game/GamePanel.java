package main.game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel(Game game) {
        super();
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createBevelBorder(1));

        TetrisFieldComponent tetrisField = new TetrisFieldComponent(game, game.getBoard());
        game.setComponent(tetrisField);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        // needed a blank panel because trying to center tetris with 2 panels was being stupid
        JPanel blankPanel = new JPanel();
        blankPanel.setOpaque(false);
        centerPanel.setOpaque(false);
        // Using gridbag now to center the tetris game panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        centerPanel.add(tetrisField, gbc);
        blankPanel.setPreferredSize(new Dimension(200, 500));
        add(blankPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(new InfoPanel(game), BorderLayout.EAST);
    }
}