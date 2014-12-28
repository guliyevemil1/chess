package com.eguliyev.chess.model;

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
        if (this == BLACK) {
            return 6;
        } else {
            return 1;
        }
    }
}
