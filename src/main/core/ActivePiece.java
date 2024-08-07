package main.core;

import java.util.ArrayList;

public record ActivePiece(Piece kind, Rotation rotation, int x, int y) {
    public ArrayList<Cell> getCells() {
        var cells = new ArrayList<Cell>();
        for (var cell : this.kind.cells()) {
            cells.add(cell.rotated(this.rotation).shifted(this.x, this.y));
        }

        return cells;
    }

    public ActivePiece shifted(int dx, int dy) {
        return new ActivePiece(this.kind, this.rotation, this.x + dx, this.y + dy);
    }

    public ActivePiece clockwise() {
        return new ActivePiece(this.kind, rotation.clockwise(), this.x, this.y);
    }

    public ActivePiece counterclockwise() {
        return new ActivePiece(this.kind, rotation.counterclockwise(), this.x, this.y);
    }
}
