package main.game.player.ai;

import main.game.Game;
import main.game.core.*;
import main.game.player.Player;

public class AIPlayer extends Player implements Runnable {
    private int pieceNumber = 0;
    private ActivePiece target;

    public AIPlayer(Game game) {
        super(game);
    }

    @Override
    public void start() {
        super.start();
        Thread aiThread = new Thread(this);
        aiThread.start();
    }

    @Override
    public synchronized void run() {
        while (this.game.isNotFinished()) {
            if (this.game.inProgress()) { tick(); }
            try {
                //noinspection BusyWait
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized void tick() {
        if (this.pieceNumber < this.game.getPiecesSpawned()) {
            this.pieceNumber = this.game.getPiecesSpawned();
            recalculateTarget();
        }

        var active = game.getBoard().getActivePiece();
        if (active.rotation().counterclockwise().equals(target.rotation())) {
            controller.rotateCounterclockwise();
            return;
        }
        if (active.rotation().ordinal() < target.rotation().ordinal()) {
            controller.rotateClockwise();
            return;
        }

        if (active.x() > target.x()) {
            controller.shiftLeft();
            return;
        }
        if (active.x() < target.x()) {
            controller.shiftRight();
            return;
        }

        controller.hardDrop();
    }

    private synchronized void recalculateTarget() {
        var active = game.getBoard().getActivePiece();

        int bestScore = Integer.MIN_VALUE;
        for (var rotation: Rotation.values()) {
            for (int x = 0; x < game.getBoard().getWidth(); x++) {
                var piece = new ActivePiece(active.kind(), rotation, x, active.y());
                if (!piece.fits(game.getBoard())) {
                    continue;
                }

                var board = new TetrisBoard(game.getBoard());
                board.setActivePiece(piece);
                var result = board.hardDrop();

                int holes = 0;
                int totalHeight = 0;
                int bumpiness = 0;
                int prevHeight = -1;
                for (int sx = 0; sx < board.getWidth(); sx++) {
                    int height = 0;
                    for (int sy = board.getHeight() - 1; sy >= 0; sy--) {
                        if (board.getFieldCell(sx, sy) != null) {
                            height = Math.max(height, sy + 1);
                        } else if (sy < height) {
                            holes++;
                        }
                    }

                    totalHeight += height;
                    if (prevHeight != -1) {
                        bumpiness += Math.abs(prevHeight - height);
                    }
                    prevHeight = height;
                }

                int score = totalHeight * -510
                    + result.linesCleared() * 761
                    + holes * -357
                    + bumpiness * -184;
                if (score >= bestScore) {
                    bestScore = score;
                    target = piece;
                }
            }
        }
    }
}
