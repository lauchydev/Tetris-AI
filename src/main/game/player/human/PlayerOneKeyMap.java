package main.game.player.human;

import main.game.GameAction;

import java.awt.event.KeyEvent;
import java.util.Map;

public class PlayerOneKeyMap extends PlayerKeyMap {
    public PlayerOneKeyMap() {
        super(Map.of(
                GameAction.LEFT, KeyEvent.VK_LEFT,
                GameAction.RIGHT, KeyEvent.VK_RIGHT,
                GameAction.ROTATE_CLOCKWISE, KeyEvent.VK_UP,
                GameAction.ROTATE_COUNTERCLOCKWISE, KeyEvent.VK_Z,
                GameAction.HARD_DROP, KeyEvent.VK_SPACE,
                GameAction.SOFT_DROP, KeyEvent.VK_DOWN
        ));
    }
}
