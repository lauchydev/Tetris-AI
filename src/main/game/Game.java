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
    private boolean running = false;
    private boolean paused = false;
    private float speedMultiplier;
    private final Timer gameLoopTimer;
    private final TetrisBoard board;
    private final GameObserver gobs;

    public Game(Configuration config, TetrisFieldComponent comp, TetrisBoard board, GameObserver gobs) {
        this.config = config;
        this.board = board;
        this.gobs = gobs;
        this.reset();

        this.gameLoopTimer = new Timer(Math.round(calculateMultiplier()), (ActionEvent e) -> {
            if (running && !paused) {
                if (!this.board.softDrop()) {

                    if (this.board.getActivePiece() == null) {
                        this.stop();
                    } else {
                        var result = this.board.hardDrop();
                        // TODO: do some scoring...
                        System.out.println("Cleared lines, do scoring...");
                    }
                }
                comp.repaint();
            }
        });
    }

    private void reset() {
        this.level = this.config.getGameLevel();
        this.speedMultiplier = 1.0f;
    }

    public boolean inProgress() { return this.running; }

    public boolean isPaused() { return this.paused; }

    public void start() {
        this.reset();
        this.running = true;
        this.gameLoopTimer.start();
        System.out.println("Game Started");
    }

    public void stop() {
        this.running = false;
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

    public void setSpeedMultiplier(float multiplier) {
        this.speedMultiplier = multiplier;
        this.updateTimerDelay();
    }

    private int timerDelay() {
        return Math.round(calculateMultiplier());
    }

    private float calculateMultiplier() {
        return this.level * 400 / this.speedMultiplier;
    }

    private void updateTimerDelay() {
        this.gameLoopTimer.setDelay(this.timerDelay());
    }
}
