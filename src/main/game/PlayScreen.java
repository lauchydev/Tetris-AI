package main.game;

import main.Tetris;
import main.configuration.Configuration;
import main.configuration.PlayerType;
import main.game.core.TetrisBoard;
import main.game.external.ExternalPlayer;
import main.ui.BasicScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class PlayScreen extends BasicScreen {
    private GameController[] controllers;
    private Game[] games;
    private final Configuration config = Configuration.getInstance();
    private int playerCount;
    private final JPanel gamesPanel = new JPanel();

    public PlayScreen() {
        super("Play");
        centerPanel.setOpaque(false);
        backButton.setFocusable(false);
        gamesPanel.setOpaque(false);
        setupGeneralKeybindings();
    }

    private void setupLayout() {
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(new StatusPanel(), BorderLayout.NORTH);
        gamesPanel.setLayout(new GridLayout(1, playerCount));
        centerPanel.add(gamesPanel, BorderLayout.CENTER);
    }

    private void startGame() {
        playerCount = config.getNumberOfPlayers();
        setupLayout();
        controllers = new GameController[playerCount];
        games = new Game[playerCount];
        long seed = System.currentTimeMillis();
        int humanPlayerNo = 1;
        for (int i = 0; i < playerCount; i++) {
            var board = new TetrisBoard(config.getFieldWidth(), config.getFieldHeight());
            games[i] = new Game(board, seed);
            controllers[i] = new GameController(games[i]);

            switch(config.getPlayerType(i + 1)) {
                case PlayerType.HUMAN:
                    PlayerKeyMap keyMap = PlayerKeyMap.getPlayerMap(humanPlayerNo++);
                    setupPlayerKeybindings(keyMap, controllers[i], Integer.toString(i));
                    break;

                case PlayerType.EXTERNAL:
                    ExternalPlayer externalPlayer = new ExternalPlayer(games[i], controllers[i]);
                    Thread externalThread = new Thread(externalPlayer);
                    games[i].start();
                    externalThread.start();
                    break;
            }

            JPanel gamePanel = new GamePanel(games[i], config.getPlayerType(i + 1));
            gamesPanel.add(gamePanel);
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

    private void setupPlayerKeybindings(PlayerKeyMap map, GameController controller, String suffix) {
        // Player specific
        bindMovementInput("RotateClockwise" + suffix, map.getKeyCode(GameAction.ROTATE_CLOCKWISE), pressed -> controller.rotateClockwise());
        bindMovementInput("RotateCounterclockwise" + suffix, map.getKeyCode(GameAction.ROTATE_COUNTERCLOCKWISE), pressed -> controller.rotateCounterclockwise());
        bindMovementInput("ShiftLeft" + suffix, map.getKeyCode(GameAction.LEFT), pressed -> controller.shiftLeft());
        bindMovementInput("ShiftRight" + suffix, map.getKeyCode(GameAction.RIGHT), pressed -> controller.shiftRight());
        bindMovementInput("HardDrop" + suffix, map.getKeyCode(GameAction.HARD_DROP), pressed -> controller.hardDrop());
        bindKeyToAction("SoftDrop" + suffix, KeyStroke.getKeyStroke(map.getKeyCode(GameAction.SOFT_DROP), 0, false), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.setSoftDropHeld(true);
            }
        });
        bindKeyToAction("SoftDropStop" + suffix, KeyStroke.getKeyStroke(map.getKeyCode(GameAction.SOFT_DROP), 0, true), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.setSoftDropHeld(false);
            }
        });
    }

    private void setupGeneralKeybindings() {
        bindKeyToAction("TogglePause", KeyStroke.getKeyStroke("P"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                for (var c : controllers) {
                    c.togglePaused();
                }
            }
        });
        bindMovementInput("ToggleMusic", KeyEvent.VK_M, pressed -> config.setMusicOn(!config.getMusicOn()));
        bindMovementInput("ToggleSound", KeyEvent.VK_S, pressed -> config.setSoundOn(!config.getSoundOn()));
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

}
