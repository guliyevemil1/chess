package com.eguliyev.chess.model.chess;

/**
* Created by eguliyev on 12/20/14.
*/
public enum Color {
    BLACK, WHITE;

    public Color opposite() {
        if (this == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public int toInt() {
        if (this == BLACK) {
            return -1;
        } else {
            return 1;
        }
    }
    public int pawnStartingPosition() {
        return pawnStartingPosition(1);
    }


    public int pawnStartingPosition(int y) {
        if (this == BLACK) {
            return 7 - y;
        } else {
            return y;
        }
    }

    public String toShortString() {
        return this.toString().substring(0, 1).toLowerCase();
    }
}
