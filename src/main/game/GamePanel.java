package main.game;

import javax.swing.*;

public class GamePanel extends JPanel {

    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
        setBorder(BorderFactory.createBevelBorder(1));
    }

}
