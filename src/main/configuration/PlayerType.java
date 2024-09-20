package main.configuration;

public enum PlayerType {
    HUMAN,
    AI,
    EXTERNAL;

    public String getFriendlyName() {
        return switch (this)
        {
            case HUMAN -> "Human";
            case AI -> "AI";
            case EXTERNAL -> "External";
        };
    }
}
