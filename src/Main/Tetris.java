package Main;

import java.awt.*;

public class Tetris extends Frame {
    private MainScreen mainScreen;


    public Tetris() {
        initUI();
    }

    private void initUI() {
        mainScreen = new MainScreen(this);
        add(mainScreen);
        setTitle("Tetris");
        setSize(900, 600);
        setResizable(false);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) {
        new Tetris();
    }
}