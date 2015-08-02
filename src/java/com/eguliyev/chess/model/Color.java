package com.eguliyev.chess.model;

/**
* Created by eguliyev on 12/20/14.
*/
public enum Color {
    BLACK, WHITE;

    private int oppOrdinal() {
        return 1 - this.ordinal();
    }

    public Color opposite() {
        return Color.values()[oppOrdinal()];
    }

    public int sign() {
        return 2 * this.ordinal() - 1;
    }

    public int start(int x) {
        return this.ordinal() * 7 + x * sign();
    }

    public int start() {
        return start(0);
    }

    public int end(int x) {
        return this.opposite().start(x);
    }

    public int end() {
        return end(0);
    }
}
