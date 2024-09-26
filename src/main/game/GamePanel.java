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
        centerPanel.setOpaque(false);
        // Using gridbag now to center the tetris game panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        centerPanel.add(tetrisField, gbc);
        add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(new InfoPanel(game));
    }
}