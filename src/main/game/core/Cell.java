package main.game.core;

public record Cell(int x, int y) {
    public Cell shifted(int dx, int dy) {
        return new Cell(this.x + dx, this.y + dy);
    }

    // suppress incorrect name combination warnings as swapping y and x for rotation is the intent
    @SuppressWarnings("SuspiciousNameCombination")
    public Cell rotated(Rotation rotation) {
        return switch (rotation) {
            case North -> new Cell(this.x, this.y);
            case East -> new Cell(this.y, -this.x);
            case South -> new Cell(-this.x, -this.y);
            case West -> new Cell(-this.y, this.x);
        };
    }
}
