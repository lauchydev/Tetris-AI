package main.game;

import main.configuration.ConfigObserver;
import main.configuration.Configuration;
import main.game.core.ActivePiece;
import main.game.core.Rotation;
import main.game.core.TetrisBoard;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel implements GameObserver, ConfigObserver {
    private static final Font HEADER_FONT = new Font("Arial", Font.PLAIN, 22);
    private final JLabel level = new JLabel();
    private final JLabel score = new JLabel();
    private final JLabel music = new JLabel();
    private final JLabel sound = new JLabel();
    private final JLabel next = new JLabel("Next");
    private final TetrisBoard nextTetrominoBoard = new TetrisBoard(6, 4);
    private final TetrisFieldComponent nextPieceComp;
    private final Game game;
    private final Configuration config = Configuration.getInstance();

    public InfoPanel(Game game) {
        super(new GridBagLayout());
        this.game = game;
        setPreferredSize(new Dimension(200, 500));
        setOpaque(false);
        game.addObserver(this);
        config.addObserver(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;

        next.setForeground(Color.WHITE);
        next.setFont(HEADER_FONT);
        nextPieceComp = new TetrisFieldComponent(null, nextTetrominoBoard);
        nextPieceComp.setMaximumSize(nextPieceComp.getPreferredSize());

        level.setForeground(Color.WHITE);
        level.setFont(HEADER_FONT);
        score.setForeground(Color.WHITE);
        score.setFont(HEADER_FONT);
        music.setForeground(Color.WHITE);
        music.setFont(HEADER_FONT);
        sound.setForeground(Color.WHITE);
        sound.setFont(HEADER_FONT);

        add(next, gbc);
        add(nextPieceComp, gbc);

        // Add a spacer
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(0, 100));
        add(spacer, gbc);

        add(level, gbc);
        add(score, gbc);
        add(music, gbc);
        add(sound, gbc);

        onGameUpdated();
        configChanged();
    }

    @Override
    public synchronized void onGameUpdated() {
        var piece = game.peekNextActivePiece();
        var kind = piece.kind();
        nextTetrominoBoard.setActivePiece(new ActivePiece(
                kind,
                Rotation.North,
                2,
                nextTetrominoBoard.getHeight() - 3
        ));
        nextPieceComp.repaint();
        level.setText("Level: " + game.getLevel());
        score.setText("Score: " + game.getScore());
    }

    @Override
    public void configChanged() {
        music.setText("Music: " + (config.getMusicOn() ? "ON" : "OFF"));
        sound.setText("Sound: " + (config.getSoundOn() ? "ON" : "OFF"));
    }
}