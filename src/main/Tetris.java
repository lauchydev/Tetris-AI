package main;

import javax.swing.*;

public class Tetris extends JFrame {


    public Tetris() {
        initUI();
    }

    private void initUI() {
        MainScreen mainScreen = new MainScreen(this);
        add(mainScreen);
        setTitle("Tetris");
        setSize(900, 600);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        new Tetris();
    }
}