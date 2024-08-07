package main;

import main.core.TetrisBoard;

import javax.swing.*;
import java.awt.*;

public class PlayScreen extends JPanel {
    private final Tetris parentFrame;
    TetrisBoard board;

    public PlayScreen(Tetris parentFrame) {
        this.parentFrame = parentFrame;
        this.board = new TetrisBoard(10, 20);

        this.setLayout(null);

        var tetrisField = new TetrisFieldComponent(this.board);
        tetrisField.setBounds(0, 0, 100, 200);
        this.add(tetrisField);
    }
}
