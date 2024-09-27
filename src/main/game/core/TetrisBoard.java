package main.game.core;

import java.util.ArrayList;

public class TetrisBoard {
    private final int width;
    private final int height;
    private final ArrayList<ArrayList<CellKind>> rows;
    private ActivePiece activePiece;

    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.rows = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            var row = new ArrayList<CellKind>();
            for (int x = 0; x < width; x++) {
                row.add(null);
            }
            this.rows.add(row);
        }
    }

    public void setActivePiece(ActivePiece piece) { this.activePiece = piece; }


    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public ArrayList<ArrayList<CellKind>> getRows() { return this.rows; }

    public CellKind getFieldCell(int x, int y) {
        if (x < 0 || x >= this.width || y < 0) {
            return CellKind.FILLED;
        }

        if (y >= this.height) {
            return null;
        }

        return this.rows.get(y).get(x);
    }

    public int[][] getPureCells() {
        int[][] cells = new int[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                var cell = getFieldCell(x, y);
                cells[height - y - 1][x] = cell != null ? 1 : 0;
            }
        }
        return cells;
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

    public boolean shiftActivePiece(int dx, int dy) {
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
