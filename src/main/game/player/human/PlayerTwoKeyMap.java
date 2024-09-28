package main.game.player.human;

import main.game.GameAction;

import java.awt.event.KeyEvent;
import java.util.Map;

public class PlayerTwoKeyMap extends PlayerKeyMap {

    public PlayerTwoKeyMap() {
        super(Map.of(
                GameAction.LEFT, KeyEvent.VK_COMMA,
                GameAction.RIGHT, KeyEvent.VK_PERIOD,
                GameAction.ROTATE_CLOCKWISE, KeyEvent.VK_L,
                GameAction.ROTATE_COUNTERCLOCKWISE, KeyEvent.VK_K,
                GameAction.HARD_DROP, KeyEvent.VK_SEMICOLON,
                GameAction.SOFT_DROP, KeyEvent.VK_SLASH
        ));
    }
}
