package main.game;

import main.game.core.ActivePiece;
import main.game.core.Rotation;
import main.game.core.TetrisBoard;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel implements GameObserver {
    private final JLabel level = new JLabel();
    private final JLabel score = new JLabel();
    private final TetrisBoard nextTetrominoBoard = new TetrisBoard(6, 4);
    private final TetrisFieldComponent nextPieceComp;
    private final Game game;

    public InfoPanel(Game game) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.game = game;
        setPreferredSize(new Dimension(200, 500));
        setOpaque(false);
        game.addObserver(this);

        add(level);
        add(score);
        nextPieceComp = new TetrisFieldComponent(null, nextTetrominoBoard);
        nextPieceComp.setMaximumSize(nextPieceComp.getPreferredSize()); // TODO: an issue here
        add(nextPieceComp);
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
        level.setText("Level: " + game.getLevel());
        score.setText("Score: " + game.getScore());
    }
}
