package main.game;

import main.game.core.CellKind;
import main.game.core.TetrisBoard;

import javax.swing.*;
import java.awt.*;

public class TetrisFieldComponent extends JComponent {
    TetrisBoard board;

    public TetrisFieldComponent(TetrisBoard board, int playScreenWidth, int playScreenHeight) {
        super();
        this.board = board;

        this.setPreferredSize(new Dimension(playScreenWidth, playScreenHeight));
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                this.paintCell(g2d, x, y, this.board.getFieldCell(x, y));
            }
        }
        if (board.getActivePiece() != null) {
            for (var cell : board.getActivePiece().getCells()) {
                this.paintCell(g2d, cell.x(), cell.y(), board.getActivePiece().kind().color());
            }
        }

    }

    private void paintCell(Graphics2D g2d, int x, int y, CellKind cellKind) {
        int cellWidth = this.getWidth() / board.getWidth();
        int cellHeight = this.getHeight() / board.getHeight();
        g2d.setColor(switch (cellKind) {
            case null -> Color.BLACK;
            case FILLED -> Color.BLACK;
            case CYAN -> Color.CYAN;
            case BLUE -> Color.BLUE;
            case ORANGE -> Color.ORANGE;
            case YELLOW -> Color.YELLOW;
            case GREEN -> Color.GREEN;
            case PURPLE -> Color.MAGENTA;
            case RED -> Color.RED;
        });
        g2d.fillRect(x * cellWidth, (this.board.getHeight() - 1 - y) * cellHeight, cellWidth, cellHeight);
    }
}
