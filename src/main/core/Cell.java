package main.core;

public record Cell(int x, int y) {
    public Cell shifted(int dx, int dy) {
        return new Cell(this.x + dx, this.y + dy);
    }
}
