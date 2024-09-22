package main.game;

import main.audio.Effect;
import main.audio.SoundEffects;
import main.configuration.Configuration;
import main.game.core.TetrisBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Game {
    private final Configuration config;
    private int level;
    private boolean paused = false;
    private boolean softDropHeld;
    private int gravityTicks;
    private final Timer gameLoopTimer;
    private final TetrisBoard board;
    private final GameObserver gobs;
    private TetrisFieldComponent comp;

    public Game(Configuration config, TetrisBoard board, GameObserver gobs) {
        this.config = config;
        this.board = board;
        this.gobs = gobs;
        this.reset();

        this.softDropHeld = false;
        this.gravityTicks = 0;
        this.gameLoopTimer = new Timer(20, (ActionEvent e) -> {
            if (this.board.isGameOver()) {
                this.stop();
                return;
            }

            if (paused) {
                return;
            }

            this.gravityTicks += this.softDropHeld ? 2 : 1;
            if (this.gravityTicks >= this.gravityDelay()) {
                this.gravityTicks = 0;
                if (!this.board.softDrop()) {
                    var result = this.board.hardDrop();
                    // TODO: do some scoring...
                    System.out.println("Cleared lines, do scoring...");
                }
            }

            comp.repaint();
        });
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
        this.gobs.onGameEnded();
        new Thread(() -> {
            SoundEffects.playEffect(Effect.GAMEOVER);
        }).start();
        System.out.println("Game Stopped");
    }

    public void togglePause() {
        this.setPaused(!this.paused);
    }

    public void setPaused(boolean paused) {
        boolean pausedChanged = this.paused != paused;
        this.paused = paused;
        if (pausedChanged) { this.gobs.onGamePauseChanged(this.paused); }
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
}
