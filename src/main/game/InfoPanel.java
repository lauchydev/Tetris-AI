package main.game;

import main.configuration.Configuration;
import main.configuration.PlayerType;
import main.game.core.ActivePiece;
import main.game.core.Rotation;
import main.game.core.TetrisBoard;
import main.ui.UI;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel implements GameObserver {
    private final JLabel initialLevel =  UI.createGameInfoHeaderLabel();
    private final JLabel level =  UI.createGameInfoHeaderLabel();
    private final JLabel linesCleared =  UI.createGameInfoHeaderLabel();
    private final JLabel score =  UI.createGameInfoHeaderLabel();
    private final TetrisBoard nextTetrominoBoard = new TetrisBoard(6, 4);
    private final TetrisFieldComponent nextPieceComp;
    private final Game game;

    public InfoPanel(Game game, int playerNo) {
        super(new GridBagLayout());
        this.game = game;
        setPreferredSize(new Dimension(230, 500));
        setOpaque(false);
        game.addObserver(this);

        JLabel next = UI.createGameInfoHeaderLabel("NEXT PIECE:");
        nextPieceComp = new TetrisFieldComponent(null, nextTetrominoBoard);
        nextPieceComp.setMaximumSize(nextPieceComp.getPreferredSize());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        add(next, gbc);
        add(nextPieceComp, gbc);

        // Add a spacer
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(0, 90));
        add(spacer, gbc);

        add(level, gbc);
        add(initialLevel, gbc);
        add(linesCleared, gbc);
        add(score, gbc);
        JLabel playerType = UI.createGameInfoHeaderLabel();
        add(playerType, gbc);
        PlayerType playerTypeValue = Configuration.getInstance().getPlayerType(playerNo);
        playerType.setText("TYPE: " + playerTypeValue);

        onGameUpdated();
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
        initialLevel.setText("START LEVEL: " + game.getStartingLevel());
        level.setText("LEVEL: " + game.getLevel());
        linesCleared.setText("LINES CLEARED: " + game.getTotalLinesCleared());
        score.setText("SCORE: " + game.getScore());
    }
}