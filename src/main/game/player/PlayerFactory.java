package main.game.player;

import main.configuration.Configuration;
import main.configuration.PlayerType;
import main.game.Game;
import main.game.PlayScreen;
import main.game.player.ai.AIPlayer;
import main.game.player.external.ExternalPlayer;
import main.game.player.human.HumanPlayer;

public class PlayerFactory {
    private final PlayScreen screen;
    private int humanPlayerNo = 1;

    public PlayerFactory(PlayScreen screen) {
        this.screen = screen;
    }

    public Player getPlayer(Game game, int playerNo) {
        var playerType = Configuration.getInstance().getPlayerType(playerNo);
        return switch(playerType) {
            case PlayerType.HUMAN -> new HumanPlayer(game, this.screen, humanPlayerNo++);
            case PlayerType.EXTERNAL -> new ExternalPlayer(game);
            case PlayerType.AI -> new AIPlayer(game);
        };
    }
}
