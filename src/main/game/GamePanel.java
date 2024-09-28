package main.game;

import main.configuration.Configuration;
import main.game.core.TetrisBoard;
import main.game.player.Player;
import main.game.player.PlayerFactory;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final static Configuration config = Configuration.getInstance();
    private final Game game;
    private final Player player;

    public GamePanel(PlayerFactory playerFactory, int playerNo, long seed) {
        super();
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createBevelBorder(1));

        var board = new TetrisBoard(config.getFieldWidth(), config.getFieldHeight());
        this.game = new Game(board, seed, playerNo);

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
        centerPanel.add(new InfoPanel(game, playerNo));

        player = playerFactory.getPlayer(game, playerNo);
    }

    public Game getGame() { return this.game; }
    public void start() { this.player.start(); }
}