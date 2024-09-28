package main.game;

import main.audio.Effect;
import main.audio.SoundEffects;
import main.configuration.Configuration;
import main.game.core.*;
import main.game.player.external.external.PureGame;
import main.game.state.Event;
import main.game.state.State;
import main.game.state.StateMachine;
import main.game.state.StateMachineObserver;
import main.highscores.HighScores;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Game implements StateMachineObserver {
    private int level;
    private final int startingLevel;
    private final StateMachine stateMachine = new StateMachine();
    private boolean softDropHeld;
    private int gravityTicks;
    private final Timer gameLoopTimer;
    private final TetrisBoard board;
    private TetrisFieldComponent comp;
    private final ArrayList<GameObserver> observers = new ArrayList<>();
    private final PieceBag pieceBag;
    private int score = 0;
    private int linesSinceLastLevel = 0;
    private int totalLinesCleared = 0;
    private int piecesSpawned = 0;

    public Game(TetrisBoard board, long seed) {
        this.board = board;
        stateMachine.setObserver(this);
        startingLevel = level = Configuration.getInstance().getGameLevel();
        score = 0;
        pieceBag = new PieceBag(seed);
        softDropHeld = false;
        gravityTicks = 0;
        spawnNextActivePiece();
        gameLoopTimer = new Timer(20, (ActionEvent e) -> {
            if (board.getActivePiece() == null) {
                lose();
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

    public int getTotalLinesCleared() { return totalLinesCleared; }

    public int getStartingLevel() { return startingLevel; }

    @Override
    public void onStateChanged(State state, Event triggerEvent) {
        switch (state) {
            case PAUSED:
                gameLoopTimer.stop();
                break;
            case RUNNING:
                gameLoopTimer.start();
                break;
            case FINISHED:
                if (triggerEvent == Event.LOSE) {
                    SoundEffects.playEffect(Effect.GAMEOVER);
                }
                gameLoopTimer.stop();
                notifyObservers();
                HighScores.checkScore(getScore());
                break;
        }
        notifyObservers();
    }

    public void spawnNextActivePiece() {
        var nextPiece = popNextActivePiece();
        var piece = nextPiece.fits(board) ? nextPiece : null;
        board.setActivePiece(piece);
        piecesSpawned++;
        observers.forEach(GameObserver::onGameUpdated);
    }

    public int getPiecesSpawned() { return piecesSpawned; }

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

    public boolean inProgress() { return stateMachine.getState() == State.RUNNING; }

    public boolean isPaused() { return stateMachine.getState() == State.PAUSED; }

    public void start() {
        stateMachine.handleEvent(Event.START);
    }

    public void lose() {
        stateMachine.handleEvent(Event.LOSE);
    }

    public void stop() {
        stateMachine.handleEvent(Event.STOP);
    }

    public void togglePause() {
        switch (stateMachine.getState()) {
            case State.RUNNING -> stateMachine.handleEvent(Event.PAUSE);
            case State.PAUSED -> stateMachine.handleEvent(Event.UNPAUSE);
        }
    }

    public void setPaused(boolean paused) {
        if (paused && stateMachine.getState() == State.RUNNING) {
            stateMachine.handleEvent(Event.PAUSE);
        }

        if (!paused && stateMachine.getState() == State.PAUSED) {
            stateMachine.handleEvent(Event.UNPAUSE);
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
        return 22 - Math.clamp(level, 1, 10) * 2;
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


    public synchronized boolean softDrop() {
        return board.softDrop();
    }

    public synchronized void hardDrop() {
        var result = this.board.hardDrop();
        if (result != null) {
            spawnNextActivePiece();
            handlePlacement(result);
        }
    }

    public PureGame serialize() throws PieceNotSpawnedException {
        var ap = this.board.getActivePiece();
        if (ap != null) {
            return new PureGame(this.board.getWidth(), this.board.getHeight(), this.board.getPureCells(), ap.kind().serialize(), this.peekNextActivePiece().kind().serialize());
        }
        throw new PieceNotSpawnedException();
    }

    private void handlePlacement(PlacementResult result) {
        totalLinesCleared += result.linesCleared();
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

    public boolean isFinished() {
        return this.stateMachine.getState() == State.FINISHED;
    }
}
