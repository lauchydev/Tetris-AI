package main.game;

import main.game.core.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void hardDrop() {
        TetrisBoard board = new TetrisBoard(10, 20);
        Game g = new Game(board, 0);
        g.getBoard().setActivePiece(new ActivePiece(Piece.I, Rotation.North, 1, 19));
        g.hardDrop();
        assertNull(g.getBoard().getFieldCell(1, 19));
        System.out.println(g.getBoard().getFieldCell(0, 0));
        assertNotNull("cell should not be null", g.getBoard().getFieldCell(0, 0));
    }

    @Test
    public void clearLines() {
        TetrisBoard board = new TetrisBoard(4, 20);
        Game g = new Game(board, 0);
        g.getBoard().setActivePiece(new ActivePiece(Piece.I, Rotation.North, 1, 19));
        g.hardDrop();
        assertNull("cell should be null", g.getBoard().getFieldCell(0, 0));
    }
}