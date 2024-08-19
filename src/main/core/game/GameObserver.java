package main.core.game;

public interface GameObserver {
    void onGamePauseChanged(boolean paused);
    void onGameEnded();
}
