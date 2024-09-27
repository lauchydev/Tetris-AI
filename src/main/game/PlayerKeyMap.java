package main.game;

import java.awt.event.KeyEvent;
import java.util.Map;

public class PlayerKeyMap {
    private final Map<GameAction, Integer> map;

    private static final PlayerKeyMap PlayerOne = new PlayerKeyMap(Map.of(
            GameAction.LEFT, KeyEvent.VK_LEFT,
            GameAction.RIGHT, KeyEvent.VK_RIGHT,
            GameAction.ROTATE_CLOCKWISE, KeyEvent.VK_UP,
            GameAction.ROTATE_COUNTERCLOCKWISE, KeyEvent.VK_Z,
            GameAction.HARD_DROP, KeyEvent.VK_SPACE,
            GameAction.SOFT_DROP, KeyEvent.VK_DOWN
    ));

    private static final PlayerKeyMap PlayerTwo = new PlayerKeyMap(Map.of(
            GameAction.LEFT, KeyEvent.VK_COMMA,
            GameAction.RIGHT, KeyEvent.VK_PERIOD,
            GameAction.ROTATE_CLOCKWISE, KeyEvent.VK_L,
            GameAction.ROTATE_COUNTERCLOCKWISE, KeyEvent.VK_K,
            GameAction.HARD_DROP, KeyEvent.VK_SEMICOLON,
            GameAction.SOFT_DROP, KeyEvent.VK_SLASH
    ));

    public static PlayerKeyMap getPlayerMap(int num) {
        if (num == 1) {
            return PlayerOne;
        } else {
            return PlayerTwo;
        }
    }

    PlayerKeyMap(Map<GameAction, Integer> map) {
        this.map = map;
    }

    public int getKeyCode(GameAction gameAction) {
        return this.map.get(gameAction);
    }
}
