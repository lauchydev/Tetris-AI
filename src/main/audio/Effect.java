package main.audio;

public enum Effect {
    LEVEL_UP("src/main/audio/soundfiles/level-up.wav"),
    GAMEOVER("src/main/audio/soundfiles/game-finish.wav"),
    ERASE_LINE("src/main/audio/soundfiles/erase-line.wav"),
    MOVE_TURN("src/main/audio/soundfiles/move-turn.wav");

    private final String path;

    Effect(String path) {
        this.path = path;
    }
}