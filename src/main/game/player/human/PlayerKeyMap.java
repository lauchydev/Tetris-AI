package main.game.player.human;

import main.game.GameAction;

import java.util.Map;

public class PlayerKeyMap {
    private final Map<GameAction, Integer> map;

    public static PlayerKeyMap getPlayerMap(int num) {
        if (num == 1) {
            return new PlayerOneKeyMap();
        } else {
            return new PlayerTwoKeyMap();
        }
    }

    PlayerKeyMap(Map<GameAction, Integer> map) {
        this.map = map;
    }

    public int getKeyCode(GameAction gameAction) {
        return this.map.get(gameAction);
    }
}
