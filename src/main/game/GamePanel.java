package main.game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel(Game game) {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setBackground(Color.BLUE);
        setBorder(BorderFactory.createBevelBorder(1));
        TetrisFieldComponent tetrisField = new TetrisFieldComponent(game);
        game.setComponent(tetrisField);
        add(new InfoPanel(game));
        add(tetrisField);
    }

}
