package main;

import javax.swing.*;

public class Tetris extends JFrame {


    public Tetris() {
        MainScreen mainScreen = new MainScreen();
        this.add(mainScreen);

        initWindow();
    }

    private void initWindow() {
        this.setTitle("Tetris");
        this.setSize(900, 600);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        new Tetris();
    }
}