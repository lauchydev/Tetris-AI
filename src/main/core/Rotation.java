package main.core;

public enum Rotation {
    North, East, South, West;

    public Rotation clockwise() {
        return switch (this) {
            case North -> Rotation.East;
            case East -> Rotation.South;
            case South -> Rotation.West;
            case West -> Rotation.North;
        };
    }

    public Rotation counterclockwise() {
        return switch (this) {
            case North -> Rotation.West;
            case East -> Rotation.North;
            case South -> Rotation.East;
            case West -> Rotation.South;
        };
    }
}
