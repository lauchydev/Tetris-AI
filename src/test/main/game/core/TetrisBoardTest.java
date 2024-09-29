package main.game.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class TetrisBoardTest {

    @Test
    public void hardDropNoClear() {
        TetrisBoard board = new TetrisBoard(10, 20);
        board.setActivePiece(new ActivePiece(Piece.I, Rotation.North, 1, 19));
        board.hardDrop();
        assertNull(board.getFieldCell(1, 19));
        assertNotNull("cell should not be null", board.getFieldCell(0, 0));
    }

    @Test
    public void hardDropClearLines() {
        TetrisBoard board = new TetrisBoard(4, 20);
        board.setActivePiece(new ActivePiece(Piece.I, Rotation.North, 1, 19));
        board.hardDrop();
        assertNull("cell should be null", board.getFieldCell(0, 0));
    }
}