package main.game;

public interface GameObserver {
    void onGamePauseChanged(Game game, boolean paused);
    void onGameEnded(Game game);
}
