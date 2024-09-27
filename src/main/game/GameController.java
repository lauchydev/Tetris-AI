package main.game;

import main.audio.Effect;
import main.audio.SoundEffects;

public class GameController {

    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void rotateClockwise() {
       SoundEffects.playEffect(Effect.MOVE_TURN);
        this.game.rotateClockwise();
    }
    public void rotateCounterclockwise() {
       SoundEffects.playEffect(Effect.MOVE_TURN);
        this.game.rotateCounterclockwise();
    }
    public void shiftLeft() {
       SoundEffects.playEffect(Effect.MOVE_TURN);
        this.game.shiftLeft();
    }
    public void shiftRight() {
       SoundEffects.playEffect(Effect.MOVE_TURN);
        this.game.shiftRight();
    }
    public void hardDrop() {
        this.game.hardDrop();
    }
    public void togglePaused() { this.game.togglePause(); }
    public void setSoftDropHeld(boolean held) { this.game.setSoftDropHeld(held); }
}
