package main.core;

import java.util.ArrayList;

public class TetrisBoard {
    int width;
    int height;
    ArrayList<ArrayList<Boolean>> rows;
    ActivePiece activePiece;

    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.rows = new ArrayList<>();
        this.activePiece = new ActivePiece(Piece.I, Rotation.North, 4, 19);

        for (int y = 0; y < height; y++) {
            var row = new ArrayList<Boolean>();
            for (int x = 0; x < width; x++) {
                row.add(false);
            }
            this.rows.add(row);
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean getFieldCell(int x, int y) {
        if (x < 0 || x >= this.width || y < 0) {
            return true;
        }

        if (y >= this.height) {
            return false;
        }

        return this.rows.get(y).get(x);
    }

    public ActivePiece getActivePiece() {
        return this.activePiece;
    }

    public boolean rotateClockwise() {
        return this.rotateActivePiece(true);
    }

    public boolean rotateCounterclockwise() {
        return this.rotateActivePiece(false);
    }

    public boolean shiftLeft() {
        return this.shiftActivePiece(-1, 0);
    }

    public boolean shiftRight() {
        return this.shiftActivePiece(1, 0);
    }

    public boolean softDrop() {
        return this.shiftActivePiece(0, -1);
    }

    private boolean rotateActivePiece(boolean clockwise) {
        if (this.activePiece == null) {
            return false;
        }

        var rotated = clockwise
                ? this.activePiece.clockwise(this)
                : this.activePiece.counterclockwise(this);
        if (rotated == null) {
            return false;
        }

        this.activePiece = rotated;
        return true;
    }

    private boolean shiftActivePiece(int dx, int dy) {
        if (this.activePiece == null) {
            return false;
        }

        var shifted = this.activePiece.shifted(this, dx, dy);
        if (shifted == null) {
            return false;
        }

        this.activePiece = shifted;
        return true;
    }
}
