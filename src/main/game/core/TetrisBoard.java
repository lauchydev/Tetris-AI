package main.game.core;

import main.audio.Effect;
import main.audio.SoundEffects;

import java.util.ArrayList;
import java.util.Objects;

public class TetrisBoard {
    private final int width;
    private final int height;
    private final ArrayList<ArrayList<CellKind>> rows;
    private ActivePiece activePiece;
    private final PieceBag pieceBag;

    public TetrisBoard(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.rows = new ArrayList<>();
        this.pieceBag = new PieceBag(seed);
        this.activePiece = popNextActivePiece();

        for (int y = 0; y < height; y++) {
            var row = new ArrayList<CellKind>();
            for (int x = 0; x < width; x++) {
                row.add(null);
            }
            this.rows.add(row);
        }
    }

    private ActivePiece popNextActivePiece() {
        return getActivePieceFromPiece(pieceBag.pop());
    }

    private ActivePiece peekNextActivePiece() {
        return getActivePieceFromPiece(pieceBag.peek());
    }

    private ActivePiece getActivePieceFromPiece(Piece piece) {
        return new ActivePiece(piece, Rotation.North, width / 2, height - 1);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public CellKind getFieldCell(int x, int y) {
        if (x < 0 || x >= this.width || y < 0) {
            return CellKind.FILLED;
        }

        if (y >= this.height) {
            return null;
        }

        return this.rows.get(y).get(x);
    }


    public ActivePiece getActivePiece() {
        return this.activePiece;
    }

    public boolean isGameOver() {
        return this.activePiece == null;
    }

    public boolean rotateClockwise() {
        new Thread(() -> SoundEffects.playEffect(Effect.MOVE_TURN)).start();
        return this.rotateActivePiece(true);
    }

    public boolean rotateCounterclockwise() {
        new Thread(() -> SoundEffects.playEffect(Effect.MOVE_TURN)).start();
        return this.rotateActivePiece(false);
    }

    public boolean shiftLeft() {
        new Thread(() -> SoundEffects.playEffect(Effect.MOVE_TURN)).start();
        return this.shiftActivePiece(-1, 0);
    }

    public boolean shiftRight() {
        new Thread(() -> SoundEffects.playEffect(Effect.MOVE_TURN)).start();
        return this.shiftActivePiece(1, 0);
    }

    public boolean softDrop() {
        return this.shiftActivePiece(0, -1);
    }

    public PlacementResult hardDrop() {
        if (this.activePiece == null) {
            return null;
        }

        // softDrop has a side effect
        //noinspection StatementWithEmptyBody
        while (this.softDrop()) {}
        for (var cell : this.activePiece.getCells()) {
            if (cell.y() < this.height) {
                this.rows.get(cell.y()).set(cell.x(), this.activePiece.kind().color());
            }
        }

        this.rows.removeIf(row -> row.stream().allMatch(Objects::nonNull));
        int linesCleared = this.height - this.rows.size();
        while (this.rows.size() < this.height) {
            var row = new ArrayList<CellKind>();
            for (int x = 0; x < width; x++) {
                row.add(null);
            }
            this.rows.add(row);
        }

        var nextPiece = popNextActivePiece();
        this.activePiece = nextPiece.fits(this) ? nextPiece : null;

        return new PlacementResult(linesCleared);
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
