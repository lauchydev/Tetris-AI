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
        this.activePiece = new ActivePiece(Piece.I, 4, 19);

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
        boolean inBounds = x >= 0 && x < this.width && y >= 0 && y < this.height;
        return inBounds ? this.rows.get(y).get(x) : true;
    }

    public ActivePiece getActivePiece() {
        return this.activePiece;
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

    private boolean shiftActivePiece(int dx, int dy) {
        if (this.activePiece == null) {
            return false;
        }

        var shifted = this.activePiece.shifted(dx, dy);
        if (this.pieceCollides(shifted)) {
            return false;
        }

        this.activePiece = shifted;
        return true;
    }

    private boolean pieceCollides(ActivePiece piece) {
        for (var cell: piece.getCells()) {
            if (this.getFieldCell(cell.x(), cell.y())) {
                return true;
            }
        }
        return false;
    }
}
