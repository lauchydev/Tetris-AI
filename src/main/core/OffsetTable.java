package main.core;

import java.util.ArrayList;
import java.util.List;

public record OffsetTable(ArrayList<KickOffsets> table) {
    public OffsetTable(ArrayList<KickOffsets> table) {
        this.table = new ArrayList<>(table);
    }

    @Override
    public ArrayList<KickOffsets> table() {
        return new ArrayList<>(this.table);
    }

    public static final OffsetTable JLSTZ = new OffsetTable(new ArrayList<>(List.of(
            new KickOffsets(new Cell( 0,  0), new Cell( 0,  0), new Cell( 0,  0), new Cell( 0,  0)),
            new KickOffsets(new Cell( 0,  0), new Cell( 1,  0), new Cell( 0,  0), new Cell(-1,  0)),
            new KickOffsets(new Cell( 0,  0), new Cell( 1, -1), new Cell( 0,  0), new Cell(-1, -1)),
            new KickOffsets(new Cell( 0,  0), new Cell( 0,  2), new Cell( 0,  0), new Cell( 0,  2)),
            new KickOffsets(new Cell( 0,  0), new Cell( 1,  2), new Cell( 0,  0), new Cell(-1,  2))
    )));

    public static final OffsetTable I = new OffsetTable(new ArrayList<>(List.of(
        new KickOffsets(new Cell( 0,  0), new Cell(-1,  0), new Cell(-1,  1), new Cell( 0,  1)),
        new KickOffsets(new Cell(-1,  0), new Cell( 0,  0), new Cell( 1,  1), new Cell( 0,  1)),
        new KickOffsets(new Cell( 2,  0), new Cell( 0,  0), new Cell(-2,  1), new Cell( 0,  1)),
        new KickOffsets(new Cell(-1,  0), new Cell( 0,  1), new Cell( 1,  0), new Cell( 0, -1)),
        new KickOffsets(new Cell( 2,  0), new Cell( 0, -2), new Cell(-2,  0), new Cell( 0,  2))
    )));

    public static final OffsetTable O = new OffsetTable(new ArrayList<>(List.of(
        new KickOffsets(new Cell( 0,  0), new Cell( 0, -1), new Cell(-1, -1), new Cell(-1,  0))
    )));
}
