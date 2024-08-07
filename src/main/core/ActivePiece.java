package main.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActivePiece {
    final Piece kind;
    int x;
    int y;

    public ActivePiece(Piece kind, int x, int y) {
        this.kind = kind;
        this.x = x;
        this.y = y;
    }

    public Piece getKind() {
        return this.kind;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public ArrayList<Cell> getCells() {
        var cells = new ArrayList<Cell>();
        for (Cell cell : this.kind.cells()) {
            cells.add(cell.shifted(this.x, this.y));
        }

        return cells;
    }

    public void shift(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}
