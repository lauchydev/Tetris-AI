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
        return this.rows.get(y).get(x);
    }

    public ArrayList<Cell> getActivePieceCells() {
        return this.activePiece != null
                ? this.activePiece.getCells()
                : new ArrayList<>();
    }
}
