package main.game.player;

import main.game.Game;
import main.game.GameController;

public abstract class Player {
    protected GameController controller;
    protected Game game;

    public Player(Game game) {
        this.game = game;
        this.controller = new GameController(game);
    }

    public void start() {
        game.start();
    }
}
