package main.configuration;

public enum PlayerType {
    HUMAN,
    AI,
    EXTERNAL;

    @Override
    public String toString() {
        return switch (this)
        {
            case HUMAN -> "Human";
            case AI -> "AI";
            case EXTERNAL -> "External";
        };
    }
}
