package main;

import main.core.TetrisBoard;

import javax.swing.*;
import java.awt.*;

public class TetrisFieldComponent extends JComponent {
    TetrisBoard board;

    public TetrisFieldComponent(TetrisBoard board, int playScreenWidth, int playScreenHeight) {
        this.board = board;

        setPreferredSize(new Dimension(playScreenWidth, playScreenHeight));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                this.paintCell(g2d, x, y, this.board.getFieldCell(x, y) ? Color.LIGHT_GRAY : Color.BLACK);
            }
        }
        if (board.getActivePiece() != null) {
            for (var cell : board.getActivePiece().getCells()) {
                this.paintCell(g2d, cell.x(), cell.y(), Color.CYAN);
            }
        }

    }

    private void paintCell(Graphics2D g2d, int x, int y, Color color) {
        int cellWidth = this.getWidth() / board.getWidth();
        int cellHeight = this.getHeight() / board.getHeight();
        g2d.setColor(color);
        g2d.fillRect(x * cellWidth, (this.board.getHeight() - 1 - y) * cellHeight, cellWidth, cellHeight);
    }
}
