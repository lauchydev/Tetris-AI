package main;

import javax.swing.*;
import java.awt.*;

public class PlayScreen extends JPanel {
    private final Tetris parentFrame;

    public PlayScreen(Tetris parentFrame) {
        this.parentFrame = parentFrame;

        this.setLayout(null);

        var tetrisField = new TetrisFieldComponent();
        tetrisField.setBounds(0, 0, 100, 200);
        this.add(tetrisField);
    }
}
