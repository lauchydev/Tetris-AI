package main.core;

import java.util.List;
import java.util.ArrayList;

public record Piece(ArrayList<Cell> cells) {
    public Piece(ArrayList<Cell> cells) {
        this.cells = new ArrayList<>(cells);
    }

    @Override
    public ArrayList<Cell> cells() {
        return new ArrayList<>(this.cells);
    }

    public static final Piece I = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  0),
        new Cell( 0,  0),
        new Cell( 1,  0),
        new Cell( 2,  0)
    )));
}
