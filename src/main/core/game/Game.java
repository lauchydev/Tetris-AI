package main.core.game;

import main.TetrisFieldComponent;
import main.configuration.Configuration;
import main.core.TetrisBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Game {
    private final Configuration config;
    private int score;
    private int level;
    private boolean running = false;
    private float speedMultiplier;
    private final Timer gameLoopTimer;
    private final TetrisBoard board;

    public Game(Configuration config, TetrisFieldComponent comp, TetrisBoard board) {
        this.config = config;
        this.board = board;
        this.reset();

        this.gameLoopTimer = new Timer(Math.round(this.level * 200 / this.speedMultiplier), (ActionEvent e) -> {
            if (running) {
                this.board.softDrop();
                comp.repaint();
            }
        });
    }

    private void reset() {
        this.level = this.config.getGameLevel();
        this.score = 0;
        this.speedMultiplier = 1.0f;
    }

    public void start() {
        this.reset();
        this.running = true;
        this.gameLoopTimer.start();
    }

    public void stop() {
        this.running = false;
        this.gameLoopTimer.stop();
    }

    public void pause() { }
    public void unpause() { }

    public void setSpeedMultiplier(float multiplier) {
        this.speedMultiplier = multiplier;
        this.updateTimerDelay();
    }

    private int timerDelay() {
        return Math.round(this.level * 200 / this.speedMultiplier);
    }

    private void updateTimerDelay() {
        this.gameLoopTimer.setDelay(this.timerDelay());
    }
}
