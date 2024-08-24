package main.game.core;

public record KickOffsets(Cell north, Cell east, Cell south, Cell west) {
    public Cell getOffset(Rotation rotation) {
        return switch (rotation) {
            case North -> this.north;
            case East -> this.east;
            case South -> this.south;
            case West -> this.west;
        };
    }

    public Cell getKick(Rotation from, Rotation to) {
        var fromOffset = this.getOffset(from);
        var toOffset = this.getOffset(to);
        return new Cell(fromOffset.x() - toOffset.x(), fromOffset.y() - toOffset.y());
    };
}
