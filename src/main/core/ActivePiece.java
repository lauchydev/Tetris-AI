package main.core;

import java.util.ArrayList;

public record ActivePiece(Piece kind, int x, int y) {
    public ArrayList<Cell> getCells() {
        var cells = new ArrayList<Cell>();
        for (var cell : this.kind.cells()) {
            cells.add(cell.shifted(this.x, this.y));
        }

        return cells;
    }

    public ActivePiece shifted(int dx, int dy) {
        return new ActivePiece(this.kind, this.x + dx, this.y + dy);
    }
}
