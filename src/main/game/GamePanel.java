package main.game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel(Game game) {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setOpaque(false);
        setBorder(BorderFactory.createBevelBorder(1));
        TetrisFieldComponent tetrisField = new TetrisFieldComponent(game, game.getBoard());
        game.setComponent(tetrisField);
        add(new InfoPanel(game));
        add(tetrisField);
    }

}
