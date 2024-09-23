package main.game;

public class GameController {

    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void rotateClockwise() {
        this.game.getBoard().rotateClockwise();
    }
    public void rotateCounterclockwise() {
        this.game.getBoard().rotateCounterclockwise();
    }
    public void shiftLeft() {
        this.game.getBoard().shiftLeft();
    }
    public void shiftRight() {
        this.game.getBoard().shiftRight();
    }
    public void hardDrop() {
        this.game.getBoard().hardDrop();
    }

}
