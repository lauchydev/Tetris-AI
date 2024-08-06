package main;

import javax.swing.*;
import java.awt.*;

public class TetrisFieldComponent extends JComponent {
    public TetrisFieldComponent() {
        setPreferredSize(new Dimension(100, 200));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
