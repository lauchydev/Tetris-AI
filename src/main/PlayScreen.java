package main;

import main.core.TetrisBoard;

import javax.swing.*;
import java.awt.event.*;

public class PlayScreen extends JPanel {
    private final Tetris parentFrame;
    TetrisBoard board;

    public PlayScreen(Tetris parentFrame) {
        int playScreenWidth = 200;
        int playScreenHeight = 400;

        this.parentFrame = parentFrame;
        this.board = new TetrisBoard(10, 20);

        this.setLayout(null);
        this.setupKeybindings();

        var tetrisField = new TetrisFieldComponent(this.board, playScreenWidth, playScreenHeight);
        tetrisField.setBounds((Tetris.frameWidth/2)- (playScreenWidth/2), 100, playScreenWidth, playScreenHeight);
        this.add(tetrisField);
    }

    private void setupKeybindings() {
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
        this.bindKeyToAction("SoftDrop", KeyStroke.getKeyStroke("DOWN"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.softDrop();
                repaint();
            }
        });
        this.bindKeyToAction("HardDrop", KeyStroke.getKeyStroke("SPACE"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                board.hardDrop();
                repaint();
            }
        });
    }

    private void bindKeyToAction(String name, KeyStroke keyStroke, Action action) {
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
        this.getActionMap().put(name, action);
    }
}
