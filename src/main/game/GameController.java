package main.game;

import main.game.core.TetrisBoard;

public class GameController {

    private final TetrisBoard board;

    public GameController(TetrisBoard board) {
        this.board = board;
    }

    public void rotateClockwise() {
        this.board.rotateClockwise();
    }
    public void rotateCounterclockwise() {
        this.board.rotateCounterclockwise();
    }
    public void shiftLeft() {
        this.board.shiftLeft();
    }
    public void shiftRight() {
        this.board.shiftRight();
    }
    public void hardDrop() {
        this.board.hardDrop();
    }

}
