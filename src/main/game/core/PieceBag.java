package main.game.core;

import java.util.*;

public class PieceBag {
    private final Random random;
    private final Stack<Piece> pieces = new Stack<>();

    public PieceBag(long seed) {
        random = new Random(seed);
        initList();
    }

    public Piece peek() {
        return pieces.peek();
    }

    public Piece pop() {
        var popped = pieces.pop();
        if (pieces.isEmpty()) {
            initList();
        }
        return popped;
    }

    private void initList() {
        Piece[] types = {Piece.I, Piece.J, Piece.L, Piece.O, Piece.S, Piece.T, Piece.Z};
        pieces.clear();
        pieces.addAll(Arrays.asList(types));
        Collections.shuffle(pieces, random);
    }
}
