package main.game;

import main.game.core.CellKind;
import main.game.core.TetrisBoard;
import main.ui.UI;

import javax.swing.*;
import java.awt.*;

public class TetrisFieldComponent extends JComponent implements GameObserver {
    private final TetrisBoard board;
    private final Game game;

    public int CELL_LENGTH = 20;

    public TetrisFieldComponent(Game game, TetrisBoard board) {
        super();
        this.board = board;
        this.game = game;
        if (this.game != null) { this.game.addObserver(this); }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                board.getWidth() * CELL_LENGTH,
                board.getHeight() * CELL_LENGTH
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                this.paintCell(g2d, x, y, board.getFieldCell(x, y), 0);
            }
        }
        if (board.getActivePiece() != null) {
            var airborne = board.getActivePiece().shifted(board, 0, -1) != null;
            var yOffset = airborne ? (game != null ? game.gravityProgress() : 0) : 0;
            for (var cell : board.getActivePiece().getCells()) {
                this.paintCell(g2d, cell.x(), cell.y(), board.getActivePiece().kind().color(), yOffset);
            }
        }

        if (game != null && game.isPaused()) {
            g2d.setColor(Color.RED);
            final String[] msgLines = {"GAME IS PAUSED", "PRESS P TO CONTINUE"};
            int offset = 40;
            for (String msgLine : msgLines) {
                int panelWidth = getWidth();
                g2d.setFont(findSuitableFont(g2d, msgLine));
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(msgLine);
                int textHeight = fm.getAscent();
                offset += textHeight + 5;
                int x = (panelWidth - textWidth) / 2;
                int y = 0;
                g2d.drawString(msgLine, x, y + offset);
            }
        }
    }

    Font findSuitableFont(Graphics2D g2d, String text) {
        int fontSize = 24;
        int panelWidth = getWidth();
        Font font;
        FontMetrics fm;
        do {
            font = UI.getFont(fontSize);
            if (fontSize <= 6) { break; }
            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            fontSize -= 2;
        } while (fm.stringWidth(text) > panelWidth);
        return font;
    }


    @Override
    public void onGameUpdated() {
        repaint();
    }

    private void paintCell(Graphics2D g2d, int x, int y, CellKind cellKind, float yOffset) {
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
        g2d.fillRect(
            x * cellWidth,
            (int)((board.getHeight() - 1 - y + yOffset) * cellHeight),
            cellWidth,
            cellHeight
        );
    }

}
