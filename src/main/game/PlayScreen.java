package main.game;

import main.Tetris;
import main.configuration.Configuration;
import main.ui.BasicScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class PlayScreen extends BasicScreen implements GameObserver {

    private static final Font PAUSED_LABEL_FONT = new Font("Arial", Font.BOLD, 20);
    private GameController[] controllers;
    private Game[] games;
    private final Configuration config = Configuration.getInstance();
    private int playerCount;

    public PlayScreen() {
        super("Play");
        backButton.setFocusable(false);
        setupKeybindings();
    }

    private void setupLayout() {
        centerPanel.removeAll();
        centerPanel.setLayout(new GridLayout(1, playerCount));
    }

    private void startGame() {
        playerCount = config.getNumberOfPlayers();
        setupLayout();
        controllers = new GameController[playerCount];
        games = new Game[playerCount];
        long seed = System.currentTimeMillis();
        for (int i = 0; i < playerCount; i++) {
            games[i] = new Game(this, seed);
            controllers[i] = new GameController(games[i]);
            JPanel gamePanel = new GamePanel(games[i]);
            centerPanel.add(gamePanel);
            games[i].start();
        }

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) { this.startGame(); }
        Tetris.instance.pack();
    }

    private boolean gameInProgress() {
        for (int i = 0; i < playerCount; i++) {
            if (games[i].inProgress()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onBackButtonClicked(ActionEvent e) {
        if (gameInProgress()) {
            boolean initialPauseState = games[0].isPaused();
            for (int i = 0; i < playerCount; i++) {
                games[i].setPaused(true);
            }

            if (!confirmExitDialog()) {
                for (int i = 0; i < playerCount; i++) {
                    games[i].setPaused(initialPauseState);
                }
                return;
            }
            for (int i = 0; i < playerCount; i++) {
                games[i].stop();
            }
        }
        super.onBackButtonClicked(e);
    }

    private boolean confirmExitDialog() {
        var result = JOptionPane.showConfirmDialog(null, "Are you sure you want to stop the game?", "Confirm End Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    private void setupKeybindings() {
        bindMovementInput("RotateClockwise", KeyEvent.VK_UP, pressed -> controllers[0].rotateClockwise());
        bindMovementInput("RotateCounterclockwise", KeyEvent.VK_Z, pressed -> controllers[0].rotateCounterclockwise());
        bindMovementInput("ShiftLeft", KeyEvent.VK_LEFT, pressed -> controllers[0].shiftLeft());
        bindMovementInput("ShiftRight", KeyEvent.VK_RIGHT, pressed -> controllers[0].shiftRight());
        bindMovementInput("HardDrop", KeyEvent.VK_SPACE, pressed -> controllers[0].hardDrop());

        bindMovementInput("ToggleMusic", KeyEvent.VK_M, pressed -> config.setMusicOn(!config.getMusicOn()));
        bindMovementInput("ToggleSound", KeyEvent.VK_S, pressed -> config.setSoundOn(!config.getSoundOn()));
        bindKeyToAction("SoftDrop", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                for (int i = 0; i < playerCount; i++) {
                    games[i].setSoftDropHeld(true);
                }
            }
        });
        bindKeyToAction("SoftDropStop", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                for (int i = 0; i < playerCount; i++) {
                    games[i].setSoftDropHeld(false);
                }
            }
        });
        bindKeyToAction("TogglePause", KeyStroke.getKeyStroke("P"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                for (int i = 0; i < playerCount; i++) {
                    games[i].togglePause();
                }
            }
        });
    }

    private void bindKeyToAction(String name, KeyStroke keyStroke, Action action) {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
        getActionMap().put(name, action);
    }

    private void bindMovementInput(String name, int keyCode, Consumer<Boolean> listener) {
        this.bindKeyToAction(name, KeyStroke.getKeyStroke(keyCode, 0, false), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (gameInProgress()) {
                    listener.accept(true);
                    repaint();
                }
            }
        });
    }

    @Override
    public void onGamePauseChanged(Game game, boolean paused) {
        // TODO: "Game is paused. Press P to resume." label
    }

    @Override
    public void onGameEnded(Game game) {
        // TODO: handle highscores name enter
        //
    }
}
