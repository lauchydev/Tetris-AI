package main.game;

import main.Tetris;
import main.configuration.Configuration;
import main.game.player.PlayerFactory;
import main.game.player.human.PlayerKeyMap;
import main.ui.BasicScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class PlayScreen extends BasicScreen {
    private GamePanel[] panels;
    private final Configuration config = Configuration.getInstance();
    private int playerCount;
    private final JPanel gamesPanel = new JPanel();
    private final PlayerFactory playerFactory;

    public PlayScreen() {
        super("PLAY");
        playerFactory = new PlayerFactory(this);
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
        panels = new GamePanel[playerCount];
        long seed = System.currentTimeMillis();
        for (int i = 0; i < playerCount; i++) {
            panels[i] = new GamePanel(playerFactory, i + 1, seed);
            gamesPanel.add(panels[i]);
            panels[i].start();
        }
        Tetris.instance.pack();
        Tetris.instance.centerFrame();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) { this.startGame(); }
    }

    private boolean gameInProgress() {
        for (int i = 0; i < playerCount; i++) {
            if (panels[i].getGame().inProgress()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onBackButtonClicked(ActionEvent e) {
        if (gameInProgress()) {
            boolean initialPauseState = panels[0].getGame().isPaused();
            for (int i = 0; i < playerCount; i++) {
                panels[i].getGame().setPaused(true);
            }

            if (!confirmExitDialog()) {
                for (int i = 0; i < playerCount; i++) {
                    panels[i].getGame().setPaused(initialPauseState);
                }
                return;
            }
            for (int i = 0; i < playerCount; i++) {
                panels[i].getGame().stop();
            }
        }
        super.onBackButtonClicked(e);
    }

    private boolean confirmExitDialog() {
        var result = JOptionPane.showConfirmDialog(null, "Are you sure you want to stop the game?", "Confirm End Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    public void setupPlayerKeybindings(PlayerKeyMap map, GameController controller, String suffix) {
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
                for (var g : panels) {
                    g.getGame().togglePause();
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
