package main.audio;

public enum Effect {
    LEVEL_UP("src/sound/level-up.wav"),
    GAMEOVER("src/sound/game-finish.wav"),
    ERASE_LINE("src/sound/erase-line.wav"),
    MOVE_TURN("src/sound/move-turn.wav");

    private final String path;

    public String getPath() {
        return path;
    }

    Effect(String path) {
        this.path = path;
    }
}