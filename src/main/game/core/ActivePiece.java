package main.game.core;

import java.util.ArrayList;

public record ActivePiece(Piece kind, Rotation rotation, int x, int y) {
    public ArrayList<Cell> getCells() {
        var cells = new ArrayList<Cell>();
        for (var cell : this.kind.cells()) {
            cells.add(cell.rotated(this.rotation).shifted(this.x, this.y));
        }

        return cells;
    }

    public boolean fits(TetrisBoard board) {
        for (var cell: this.getCells()) {
            if (board.getFieldCell(cell.x(), cell.y()) != null) {
                return false;
            }
        }
        return true;
    }

    public ActivePiece shifted(TetrisBoard board, int dx, int dy) {
        var shifted = new ActivePiece(this.kind, this.rotation, this.x + dx, this.y + dy);
        return shifted.fits(board) ? shifted : null;
    }

    public ActivePiece clockwise(TetrisBoard board) {
        return this.rotatedTo(board, this.rotation.clockwise());
    }

    public ActivePiece counterclockwise(TetrisBoard board) {
        return this.rotatedTo(board, this.rotation.counterclockwise());
    }

    private ActivePiece rotatedTo(TetrisBoard board, Rotation to) {
        for (var offsets : this.kind.offsetTable().table()) {
            var offset = offsets.getKick(this.rotation, to);
            var rotated = new ActivePiece(this.kind, to, this.x + offset.x(), this.y + offset.y());
            if (rotated.fits(board)) {
                return rotated;
            }
        }
        return null;
    }
}
