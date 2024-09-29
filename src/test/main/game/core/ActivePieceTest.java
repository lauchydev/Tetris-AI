package main.game.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ActivePieceTest {

    private TetrisBoard board;

    @Before
    public void setUp() {
        board = new TetrisBoard(10, 10);
    }

    @Test
    public void fitsOnEmpty() {
        // Can place on empty board?
        var piece = new ActivePiece(Piece.I, Rotation.North, 1, 0);
        assertTrue(piece.fits(board));
    }

    @Test
    public void fitsOnTaken() {
        // Can't place if cell is taken?
        var piece = new ActivePiece(Piece.I, Rotation.North, 1, 0);
        board.getFieldCell(1, 0);
        board.getRows().getFirst().set(1, CellKind.CYAN);
        assertFalse(piece.fits(board));
    }

    @Test
    public void shiftedValid() {
        // Can move into valid space
        var piece = new ActivePiece(Piece.I, Rotation.North, 1, 0);
        var newPiece = piece.shifted(board, 1, 1);
        assertNotNull(newPiece);
        assertEquals(2, newPiece.x());
        assertEquals(1, newPiece.y());

    }

    @Test
    public void shiftedInvalid() {
        // Can't move into negative territory
        var piece = new ActivePiece(Piece.I, Rotation.North, 1, 0);
        var newPiece = piece.shifted(board, -1, -1);
        assertNull(newPiece);
    }

    @Test
    public void clockwise() {
        var piece = new ActivePiece(Piece.I, Rotation.North, 1, 9);
        var newPiece = piece.clockwise(board);

        assertList(newPiece.getCells(), new ArrayList<>(Arrays.asList(
                new Cell(2, 10),
                new Cell(2, 9),
                new Cell(2, 8),
                new Cell(2, 7)
        )));
    }

    @Test
    public void counterclockwise() {
        var piece = new ActivePiece(Piece.I, Rotation.North, 1, 9);
        var newPiece = piece.counterclockwise(board);

        assertList(newPiece.getCells(), new ArrayList<>(Arrays.asList(
                new Cell(1, 7),
                new Cell(1, 8),
                new Cell(1, 9),
                new Cell(1, 10)
        )));
    }

    private static void assertList(ArrayList<Cell> list, ArrayList<Cell> expected) {
        assertEquals(list.size(), expected.size());
        for (var e : expected) {
            assertTrue(list.contains(e));
        }
    }
}