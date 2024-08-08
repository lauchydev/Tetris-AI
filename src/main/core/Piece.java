package main.core;

import java.util.List;
import java.util.ArrayList;

public record Piece(ArrayList<Cell> cells, OffsetTable offsetTable) {
    public Piece(ArrayList<Cell> cells, OffsetTable offsetTable) {
        this.cells = new ArrayList<>(cells);
        this.offsetTable = offsetTable;
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
    )), OffsetTable.I);

    public static final Piece J = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  1),
        new Cell(-1,  0),
        new Cell( 0,  0),
        new Cell( 1,  0)
    )), OffsetTable.JLSTZ);

    public static final Piece L = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  0),
        new Cell( 0,  0),
        new Cell( 1,  1),
        new Cell( 1,  0)
    )), OffsetTable.JLSTZ);

    public static final Piece O = new Piece(new ArrayList<>(List.of(
        new Cell( 0,  1),
        new Cell( 0,  0),
        new Cell( 1,  1),
        new Cell( 1,  0)
    )), OffsetTable.O);

    public static final Piece S = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  0),
        new Cell( 0,  1),
        new Cell( 0,  0),
        new Cell( 1,  1)
    )), OffsetTable.JLSTZ);

    public static final Piece T = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  0),
        new Cell( 0,  1),
        new Cell( 0,  0),
        new Cell( 1,  0)
    )), OffsetTable.JLSTZ);

    public static final Piece Z = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  1),
        new Cell( 0,  1),
        new Cell( 0,  0),
        new Cell( 1,  0)
    )), OffsetTable.JLSTZ);
}
