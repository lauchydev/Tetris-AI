package main.game;

public interface GameObserver {
    void onGamePauseChanged(boolean paused);
    void onGameEnded();
}
