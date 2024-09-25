package main.game;

import main.game.core.ActivePiece;
import main.game.core.Rotation;
import main.game.core.TetrisBoard;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel implements GameObserver {
    private final TetrisBoard nextTetrominoBoard = new TetrisBoard(6, 4);
    private final TetrisFieldComponent nextPieceComp;
    private final Game game;

    public InfoPanel(Game game) {
        super();
        this.game = game;
        setPreferredSize(new Dimension(200, 500));
        setOpaque(false);
        game.addObserver(this);

        nextPieceComp = new TetrisFieldComponent(null, nextTetrominoBoard);
        add(nextPieceComp);
        onGameUpdated();
    }

    @Override
    public void onGameUpdated() {
        var piece = game.peekNextActivePiece();
        var kind = piece.kind();
        nextTetrominoBoard.setActivePiece(new ActivePiece(
                kind,
                Rotation.North,
                2,
                nextTetrominoBoard.getHeight() - 3
        ));
        nextPieceComp.repaint();
    }
}
