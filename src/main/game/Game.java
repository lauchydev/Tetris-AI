package main.game;

import main.audio.Effect;
import main.audio.SoundEffects;
import main.configuration.Configuration;
import main.game.core.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Objects;

public class Game {
    private final Configuration config = Configuration.getInstance();
    private int level;
    private boolean paused = false;
    private boolean softDropHeld;
    private int gravityTicks;
    private final Timer gameLoopTimer;
    private final TetrisBoard board;
    private TetrisFieldComponent comp;
    private final ArrayList<GameObserver> observers = new ArrayList<>();
    private final PieceBag pieceBag;

    public Game(TetrisBoard board, long seed) {
        this.board = board;
        this.reset();
        this.pieceBag = new PieceBag(seed);
        this.softDropHeld = false;
        this.gravityTicks = 0;
        this.spawnNextActivePiece();
        this.gameLoopTimer = new Timer(20, (ActionEvent e) -> {
            if (this.board.getActivePiece() == null) {
                this.stop();
                return;
            }

            if (paused) {
                return;
            }

            this.gravityTicks += this.softDropHeld ? 2 : 1;
            if (this.gravityTicks >= this.gravityDelay()) {
                this.gravityTicks = 0;
                if (!this.softDrop()) {
                    var result = this.hardDrop();
                    // TODO: do some scoring...
                    System.out.println("Cleared lines, do scoring...");
                }
            }

            comp.repaint();
        });
    }

    public void spawnNextActivePiece() {
        var nextPiece = popNextActivePiece();
        var piece = nextPiece.fits(this.board) ? nextPiece : null;
        this.board.setActivePiece(piece);
        observers.forEach(GameObserver::onGameUpdated);
    }

    private ActivePiece popNextActivePiece() {
        return getActivePieceFromPiece(pieceBag.pop());
    }

    public ActivePiece peekNextActivePiece() {
        return getActivePieceFromPiece(pieceBag.peek());
    }

    private ActivePiece getActivePieceFromPiece(Piece piece) {
        var width = this.board.getWidth();
        var height = this.board.getHeight();
        return new ActivePiece(piece, Rotation.North, width / 2, height - 1);
    }

    public void addObserver(GameObserver obs) {
        observers.add(obs);
    }

    private void reset() {
        this.level = this.config.getGameLevel();
    }

    public boolean inProgress() { return this.gameLoopTimer.isRunning(); }

    public boolean isPaused() { return this.paused; }

    public void start() {
        this.reset();
        this.gameLoopTimer.start();
        System.out.println("Game Started");
    }

    public void stop() {
        this.gameLoopTimer.stop();
        this.observers.forEach(GameObserver::onGameUpdated);
        new Thread(() -> SoundEffects.playEffect(Effect.GAMEOVER)).start();
        System.out.println("Game Stopped");
    }

    public void togglePause() {
        this.setPaused(!this.paused);
    }

    public void setPaused(boolean paused) {
        boolean pausedChanged = this.paused != paused;
        this.paused = paused;
        if (pausedChanged) {
            this.observers.forEach(GameObserver::onGameUpdated);
        }
    }

    public void setSoftDropHeld(boolean held) {
        this.softDropHeld = held;
    }

    /**
     * Access the underlying TetrisBoard.
     * @return The underlying TetrisBoard. Do not mutate.
     */
    public TetrisBoard getBoard() {
        return this.board;
    }

    public float gravityProgress() {
        return (float)this.gravityTicks / (float)this.gravityDelay();
    }

    public void setComponent(TetrisFieldComponent comp) {
        this.comp = comp;
    }

    private int gravityDelay() {
        return 22 - this.level * 2;
    }

    public void rotateClockwise() {
        this.board.rotateClockwise();
    }
    public void rotateCounterclockwise() {
        this.board.rotateCounterclockwise();
    }
    public void shiftLeft() {
        this.board.shiftLeft();
    }
    public void shiftRight() {
        this.board.shiftRight();
    }


    public boolean softDrop() {
        return this.board.shiftActivePiece(0, -1);
    }

    public PlacementResult hardDrop() {
        var activePiece = this.board.getActivePiece();
        if (activePiece == null) {
            return null;
        }

        // softDrop has a side effect
        //noinspection StatementWithEmptyBody
        while (this.softDrop()) {}
        for (var cell : activePiece.getCells()) {
            if (cell.y() < this.board.getHeight()) {
                this.board.getRows().get(cell.y()).set(cell.x(), activePiece.kind().color());
            }
        }

        var rows = this.board.getRows();
        rows.removeIf(row -> row.stream().allMatch(Objects::nonNull));
        int linesCleared = this.board.getHeight() - rows.size();
        while (rows.size() < this.board.getHeight()) {
            var row = new ArrayList<CellKind>();
            for (int x = 0; x < this.board.getWidth(); x++) {
                row.add(null);
            }
            rows.add(row);
        }

        this.spawnNextActivePiece();

        return new PlacementResult(linesCleared);
    }

}
