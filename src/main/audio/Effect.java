package main.audio;

public enum Effect {
    LEVEL_UP("src/resources/sound/level-up.wav"),
    GAMEOVER("src/resources/sound/game-finish.wav"),
    ERASE_LINE("src/resources/sound/erase-line.wav"),
    MOVE_TURN("src/resources/sound/move-turn.wav");

    private final String path;

    public String getPath() {
        return path;
    }

    Effect(String path) {
        this.path = path;
    }
}