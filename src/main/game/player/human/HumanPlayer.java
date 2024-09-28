package main.game.player.human;

import main.game.Game;
import main.game.PlayScreen;
import main.game.player.Player;

public class HumanPlayer extends Player {
    private final PlayScreen screen;
    private int humanPlayerNo;

    public HumanPlayer(Game game, PlayScreen screen, int humanPlayerNo) {
        super(game);
        this.screen = screen;
        this.humanPlayerNo = humanPlayerNo;
    }

    @Override
    public void start() {
        super.start();
        PlayerKeyMap keyMap = PlayerKeyMap.getPlayerMap(humanPlayerNo++);
        this.screen.setupPlayerKeybindings(keyMap, this.controller, Integer.toString(humanPlayerNo));
    }
}
