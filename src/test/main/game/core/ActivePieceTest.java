package main.game.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActivePieceTest {

    private TetrisBoard board;

    @Before
    public void setUp() {
        board = new TetrisBoard(10, 10);
    }

    @Test
    public void fits() {
        // Can place on empty board?
        var piece = new ActivePiece(Piece.I, Rotation.North, 1, 0);
        assertTrue(piece.fits(board));

        // Can't place if cell is taken?
        board.getFieldCell(1, 0);
        board.getRows().getFirst().set(1, CellKind.CYAN);
        assertFalse(piece.fits(board));
    }

    @Test
    public void shifted() {
        // Can move into valid space
        var piece = new ActivePiece(Piece.I, Rotation.North, 1, 0);
        var newPiece = piece.shifted(board, 1, 1);
        assertNotNull(newPiece);
        assertEquals(2, newPiece.x());
        assertEquals(1, newPiece.y());

        // Can't move into negative territory
        newPiece = piece.shifted(board, -1, -1);
        assertNull(newPiece);
    }

    @Test
    public void clockwise() {
    }

    @Test
    public void counterclockwise() {
    }

    @Test
    public void rotation() {
    }
}