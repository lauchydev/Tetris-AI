package main.game;

import main.Tetris;
import main.game.core.TetrisBoard;
import main.ui.BasicScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayScreen extends BasicScreen implements GameObserver {

    private static final String FONT_NAME = "Arial";
    private static final int FONT_FLAGS = Font.BOLD;
    private static final int HEADER_FONT_SIZE = 20;
    TetrisBoard board;
    public int playScreenWidth = 200;
    public int playScreenHeight = 400;
    private final Game game;
    private final JLabel pausedLabel;

    public PlayScreen(Tetris parentFrame) {
        super(parentFrame, "");
        this.setBackground(new Color(20, 20, 20));

        JLabel titleLabel = new JLabel("Tetris");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(415, 20, 300, 80);
        this.add(titleLabel);


        this.backButton.setFocusable(false);

        JLayeredPane layeredPane = this.createLayeredPane();
        this.board = new TetrisBoard(10, 20);

        this.setLayout(new BorderLayout());
        this.setupKeybindings();

        var tetrisField = new TetrisFieldComponent(this.board, this.playScreenWidth, this.playScreenHeight);
        tetrisField.setBounds((Tetris.frameWidth/2)- (this.playScreenWidth/2), 100, this.playScreenWidth, this.playScreenHeight);
        layeredPane.add(tetrisField, JLayeredPane.DEFAULT_LAYER);
        this.add(layeredPane, BorderLayout.CENTER);

        this.pausedLabel = this.createPauseLabel();
        layeredPane.add(this.pausedLabel, JLayeredPane.PALETTE_LAYER);

        this.game = new Game(this.parentFrame.config, tetrisField, board, this);
        this.game.start();
    }

    private JLayeredPane createLayeredPane() {
        var layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(this.getSize());
        layeredPane.setVisible(true);
        layeredPane.setBackground(Color.blue);
        return layeredPane;
    }

    private JLabel createPauseLabel() {
        String text = "Game is paused. Press P to resume.";
        var pausedLabel = new JLabel(text);
        pausedLabel.setForeground(Color.RED);
        Font font =  new Font(FONT_NAME, FONT_FLAGS, HEADER_FONT_SIZE);
        pausedLabel.setFont(font);
        FontMetrics metrics = pausedLabel.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();
        pausedLabel.setBounds((Tetris.frameWidth - textWidth) / 2, 150, textWidth, textHeight);
        pausedLabel.setVisible(false);
        return pausedLabel;
    }

    @Override
    protected void onBackButtonClicked() {
        if (this.game.inProgress()) {
            boolean initialPauseState = this.game.isPaused();
            this.game.setPaused(true);

            if (!this.confirmExitDialog()) {
                this.game.setPaused(initialPauseState);
                return;
            }
        }

        this.game.stop();
        super.onBackButtonClicked();
    }

    private boolean confirmExitDialog() {
        var result = JOptionPane.showConfirmDialog(null, "Are you sure you want to stop the game?", "Confirm End Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    private void setupKeybindings() {

        var normalSpeedAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                game.setSpeedMultiplier(1.0f);
            }
        };

        this.bindKeyToAction("RotateClockwise", KeyStroke.getKeyStroke("UP"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.rotateClockwise();
                repaint();
            }
        });
        this.bindKeyToAction("RotateCounterclockwise", KeyStroke.getKeyStroke("Z"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.rotateCounterclockwise();
                repaint();
            }
        });
        this.bindKeyToAction("ShiftLeft", KeyStroke.getKeyStroke("LEFT"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.shiftLeft();
                repaint();
            }
        });
        this.bindKeyToAction("ShiftRight", KeyStroke.getKeyStroke("RIGHT"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.shiftRight();
                repaint();
            }
        });
        this.bindKeyToAction("SoftDrop", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                game.setSpeedMultiplier(5.0f);
                repaint();
            }
        });
        this.bindKeyToAction("SoftDropStop", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), normalSpeedAction);
        this.bindKeyToAction("HardDrop", KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                game.setSpeedMultiplier(10.0f);
                repaint();
            }
        });
        this.bindKeyToAction("HardDropStop", KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), normalSpeedAction);
        this.bindKeyToAction("TogglePause", KeyStroke.getKeyStroke("P"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                game.togglePause();
            }
        });
    }

    private void bindKeyToAction(String name, KeyStroke keyStroke, Action action) {
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
        this.getActionMap().put(name, action);
    }

    @Override
    public void onGamePauseChanged(boolean paused) {
        this.pausedLabel.setVisible(paused);
    }

    @Override
    public void onGameEnded() {
        // TODO: handle highscores name enter
        //
    }
}
