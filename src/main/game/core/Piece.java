package main.game.core;

import java.util.List;
import java.util.ArrayList;

public record Piece(ArrayList<Cell> cells, OffsetTable offsetTable, CellKind color) {
    public Piece(ArrayList<Cell> cells, OffsetTable offsetTable, CellKind color) {
        this.cells = new ArrayList<>(cells);
        this.offsetTable = offsetTable;
        this.color = color;
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
    )), OffsetTable.I, CellKind.CYAN);

    public static final Piece J = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  1),
        new Cell(-1,  0),
        new Cell( 0,  0),
        new Cell( 1,  0)
    )), OffsetTable.JLSTZ, CellKind.BLUE);

    public static final Piece L = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  0),
        new Cell( 0,  0),
        new Cell( 1,  1),
        new Cell( 1,  0)
    )), OffsetTable.JLSTZ, CellKind.ORANGE);

    public static final Piece O = new Piece(new ArrayList<>(List.of(
        new Cell( 0,  1),
        new Cell( 0,  0),
        new Cell( 1,  1),
        new Cell( 1,  0)
    )), OffsetTable.O, CellKind.YELLOW);

    public static final Piece S = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  0),
        new Cell( 0,  1),
        new Cell( 0,  0),
        new Cell( 1,  1)
    )), OffsetTable.JLSTZ, CellKind.GREEN);

    public static final Piece T = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  0),
        new Cell( 0,  1),
        new Cell( 0,  0),
        new Cell( 1,  0)
    )), OffsetTable.JLSTZ, CellKind.PURPLE);

    public static final Piece Z = new Piece(new ArrayList<>(List.of(
        new Cell(-1,  1),
        new Cell( 0,  1),
        new Cell( 0,  0),
        new Cell( 1,  0)
    )), OffsetTable.JLSTZ, CellKind.RED);


    public int[][] serialize() {
        // Find the minimum and maximum x, y values
        int minX = cells.stream().mapToInt(Cell::x).min().orElseThrow();
        int minY = cells.stream().mapToInt(Cell::y).min().orElseThrow();
        int maxX = cells.stream().mapToInt(Cell::x).max().orElseThrow();
        int maxY = cells.stream().mapToInt(Cell::y).max().orElseThrow();

        // Calculate width and height of the grid
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        // Initialize the grid with 0s
        int[][] grid = new int[height][width];

        // Populate the grid with 1s where the cells exist
        for (Cell cell : cells) {
            grid[maxY - cell.y()][cell.x() - minX] = 1;
        }

        return grid;
    }
}
