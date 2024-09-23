package main.game.core;

import java.util.*;

public class PieceBag {
    private final Random random;
    private final Stack<Piece> pieces = new Stack<>();

    public PieceBag(long seed) {
        random = new Random(seed);
        initList();
    }

    private void ensurePieces() {
        if (pieces.isEmpty()) {
            initList();
        }
    }

    public Piece peek() {
        ensurePieces();
        return pieces.peek();
    }

    public Piece pop() {
        ensurePieces();
        return pieces.pop();
    }

    private void initList() {
        Piece[] types = {Piece.I, Piece.J, Piece.L, Piece.O, Piece.S, Piece.T, Piece.Z};
        pieces.clear();
        pieces.addAll(Arrays.asList(types));
        Collections.shuffle(pieces, random);
    }
}
