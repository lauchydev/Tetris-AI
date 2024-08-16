package main;

import main.core.TetrisBoard;
import main.core.game.Game;
import main.ui.BasicScreen;

import javax.swing.*;
import java.awt.event.*;

public class PlayScreen extends BasicScreen {

    TetrisBoard board;
    public int playScreenWidth = 200;
    public int playScreenHeight = 400;
    private final Game game;

    public PlayScreen(Tetris parentFrame) {
        super(parentFrame, "Tetris");

        this.board = new TetrisBoard(10, 20);

        this.setLayout(null);
        this.setupKeybindings();

        var tetrisField = new TetrisFieldComponent(this.board, this.playScreenWidth, this.playScreenHeight);
        tetrisField.setBounds((Tetris.frameWidth/2)- (this.playScreenWidth/2), 100, this.playScreenWidth, this.playScreenHeight);
        this.add(tetrisField);

        this.game = new Game(this.parentFrame.config, tetrisField, board);
        this.game.start();
    }

    @Override
    protected void onBackButtonClicked() {
        this.game.stop();
        super.onBackButtonClicked();
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
    }

    private void bindKeyToAction(String name, KeyStroke keyStroke, Action action) {
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
        this.getActionMap().put(name, action);
    }
}
