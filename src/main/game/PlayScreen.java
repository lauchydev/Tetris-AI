package main.game;

import main.configuration.Configuration;
import main.game.core.TetrisBoard;
import main.ui.BasicScreen;
import main.ui.MainScreenListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayScreen extends BasicScreen implements GameObserver {

    private static final Font PAUSED_LABEL_FONT = new Font("Arial", Font.BOLD, 20);
    TetrisBoard board;
    public int playScreenWidth = 200;
    public int playScreenHeight = 400;
    private final Game game;
    private final JLabel pausedLabel;

    public PlayScreen(MainScreenListener listener, Configuration config) {
        super(listener, null);
        backButton.setFocusable(false);

        JLayeredPane layeredPane = createLayeredPane();
        board = new TetrisBoard(10, 20);

        setupKeybindings();

        var tetrisField = new TetrisFieldComponent(board, playScreenWidth, playScreenHeight);
        pausedLabel = createPauseLabel();
        SwingUtilities.invokeLater(() -> {
            tetrisField.setBounds((getSize().width/2)- (playScreenWidth/2), 50, playScreenWidth, playScreenHeight);
            layeredPane.add(tetrisField, JLayeredPane.DEFAULT_LAYER);
            add(layeredPane, BorderLayout.CENTER);
            layeredPane.add(pausedLabel, JLayeredPane.PALETTE_LAYER);
        });


        game = new Game(config, tetrisField, board, this);
        game.start();
    }

    private JLayeredPane createLayeredPane() {
        var layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(getSize());
        layeredPane.setVisible(true);
        layeredPane.setBackground(Color.blue);
        return layeredPane;
    }

    private JLabel createPauseLabel() {
        String text = "Game is paused. Press P to resume.";
        var pausedLabel = new JLabel(text);
        pausedLabel.setForeground(Color.RED);
        pausedLabel.setFont(PAUSED_LABEL_FONT);
        FontMetrics metrics = pausedLabel.getFontMetrics(PAUSED_LABEL_FONT);
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();
        SwingUtilities.invokeLater(() -> {
            pausedLabel.setBounds((getWidth() - textWidth) / 2, 150, textWidth, textHeight);
            pausedLabel.setVisible(false);
        });
        return pausedLabel;
    }

    @Override
    protected void onBackButtonClicked(ActionEvent e) {
        if (game.inProgress()) {
            boolean initialPauseState = game.isPaused();
            game.setPaused(true);

            if (!confirmExitDialog()) {
                game.setPaused(initialPauseState);
                return;
            }
        }

        game.stop();
        super.onBackButtonClicked(e);
    }

    private boolean confirmExitDialog() {
        var result = JOptionPane.showConfirmDialog(null, "Are you sure you want to stop the game?", "Confirm End Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    private void setupKeybindings() {
        bindKeyToAction("RotateClockwise", KeyStroke.getKeyStroke("UP"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.rotateClockwise();
                repaint();
            }
        });
        bindKeyToAction("RotateCounterclockwise", KeyStroke.getKeyStroke("Z"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.rotateCounterclockwise();
                repaint();
            }
        });
        bindKeyToAction("ShiftLeft", KeyStroke.getKeyStroke("LEFT"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.shiftLeft();
                repaint();
            }
        });
        bindKeyToAction("ShiftRight", KeyStroke.getKeyStroke("RIGHT"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.shiftRight();
                repaint();
            }
        });
        bindKeyToAction("SoftDrop", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                game.setSpeedMultiplier(2.0f);
                repaint();
            }
        });
        bindKeyToAction("SoftDropStop", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                game.setSpeedMultiplier(1.0f);
                repaint();
            }
        });
        bindKeyToAction("HardDrop", KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.hardDrop();
                repaint();
            }
        });
        bindKeyToAction("TogglePause", KeyStroke.getKeyStroke("P"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                game.togglePause();
            }
        });
    }

    private void bindKeyToAction(String name, KeyStroke keyStroke, Action action) {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
        getActionMap().put(name, action);
    }

    @Override
    public void onGamePauseChanged(boolean paused) {
        pausedLabel.setVisible(paused);
    }

    @Override
    public void onGameEnded() {
        // TODO: handle highscores name enter
        //
    }
}
