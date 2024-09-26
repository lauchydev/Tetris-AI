package main.game;

import main.audio.Effect;
import main.audio.SoundEffects;
import main.configuration.Configuration;
import main.game.core.*;
import main.highscores.HighScores;

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
    private int score = 0;
    private int linesSinceLastLevel = 0;

    public Game(TetrisBoard board, long seed) {
        this.board = board;
        reset();
        pieceBag = new PieceBag(seed);
        softDropHeld = false;
        gravityTicks = 0;
        spawnNextActivePiece();
        gameLoopTimer = new Timer(20, (ActionEvent e) -> {
            if (board.getActivePiece() == null) {
                stop();
                return;
            }

            if (paused) {
                return;
            }

            gravityTicks += softDropHeld ? 2 : 1;
            if (gravityTicks >= gravityDelay()) {
                gravityTicks = 0;
                if (!softDrop()) {
                    hardDrop();
                }
            }

            comp.repaint();
        });
    }

    public void spawnNextActivePiece() {
        var nextPiece = popNextActivePiece();
        var piece = nextPiece.fits(board) ? nextPiece : null;
        board.setActivePiece(piece);
        observers.forEach(GameObserver::onGameUpdated);
    }

    private ActivePiece popNextActivePiece() {
        return getActivePieceFromPiece(pieceBag.pop());
    }

    public ActivePiece peekNextActivePiece() {
        return getActivePieceFromPiece(pieceBag.peek());
    }

    private ActivePiece getActivePieceFromPiece(Piece piece) {
        var width = board.getWidth();
        var height = board.getHeight();
        return new ActivePiece(piece, Rotation.North, width / 2, height - 1);
    }

    public void addObserver(GameObserver obs) {
        observers.add(obs);
    }

    private void reset() {
        level = config.getGameLevel();
        score = 0;
    }

    public boolean inProgress() { return gameLoopTimer.isRunning(); }

    public boolean isPaused() { return paused; }

    public void start() {
        reset();
        gameLoopTimer.start();
        System.out.println("Game Started");
    }

    public void stop() {
        gameLoopTimer.stop();
        notifyObservers();
        SoundEffects.playEffect(Effect.GAMEOVER);
        System.out.println("Game Stopped");
        HighScores.checkScore(getScore());
    }



    public void togglePause() {
        setPaused(!paused);
    }

    public void setPaused(boolean paused) {
        boolean pausedChanged = this.paused != paused;
        this.paused = paused;
        if (pausedChanged) {
            notifyObservers();
        }
    }

    public void setSoftDropHeld(boolean held) {
        softDropHeld = held;
    }

    /**
     * Access the underlying TetrisBoard.
     * @return The underlying TetrisBoard. Do not mutate.
     */
    public TetrisBoard getBoard() {
        return board;
    }

    public float gravityProgress() {
        return (float)gravityTicks / (float)gravityDelay();
    }

    public void setComponent(TetrisFieldComponent comp) {
        this.comp = comp;
    }

    private int gravityDelay() {
        return 22 - level * 2;
    }

    public void rotateClockwise() {
        board.rotateClockwise();
    }
    public void rotateCounterclockwise() {
        board.rotateCounterclockwise();
    }
    public void shiftLeft() {
        board.shiftLeft();
    }
    public void shiftRight() {
        board.shiftRight();
    }


    public boolean softDrop() {
        return board.shiftActivePiece(0, -1);
    }

    public void hardDrop() {
        if (board.getActivePiece() == null) {
            return;
        }

        // softDrop has a side effect
        //noinspection StatementWithEmptyBody
        while (softDrop()) {}
        for (var cell : board.getActivePiece().getCells()) {
            if (cell.y() < board.getHeight()) {
                board.getRows().get(cell.y()).set(cell.x(), board.getActivePiece().kind().color());
            }
        }

        var rows = board.getRows();
        rows.removeIf(row -> row.stream().allMatch(Objects::nonNull));
        int linesCleared = board.getHeight() - rows.size();
        while (rows.size() < board.getHeight()) {
            var row = new ArrayList<CellKind>();
            for (int x = 0; x < board.getWidth(); x++) {
                row.add(null);
            }
            rows.add(row);
        }

        spawnNextActivePiece();

        handlePlacement(new PlacementResult(linesCleared));
    }

    private void handlePlacement(PlacementResult result) {
        // Handle level increase
        linesSinceLastLevel += result.linesCleared();
        var levelsToIncrease = linesSinceLastLevel / 10;
        level += levelsToIncrease;
        if (levelsToIncrease > 0) {
            SoundEffects.playEffect(Effect.LEVEL_UP);
        }
        linesSinceLastLevel %= 10;

        // Handle scoring
        var linesCleared = result.linesCleared();
        if (linesCleared > 0) {
            SoundEffects.playEffect(Effect.ERASE_LINE);
        }
        score += switch (linesCleared) {
            case 1 -> 100;
            case 2 -> 300;
            case 3 -> 600;
            case 4 -> 1000;
            default -> 0;
        };

        notifyObservers();
    }

    public int getScore() { return score; }

    private void notifyObservers() {
        observers.forEach(GameObserver::onGameUpdated);
    }

    public int getLevel() { return level; }
}
