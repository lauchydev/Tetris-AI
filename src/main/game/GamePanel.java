package main.game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBorder(BorderFactory.createBevelBorder(1));
    }

}
