package com.eguliyev.chess.model;

/**
 * Created by eguliyev on 12/28/14.
 */

public class Direction extends Square {
    Direction() {

    }

    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction(Square a, Square b) {
        this.x = Integer.signum(a.x - b.x);
        this.y = Integer.signum(a.y - b.y);
    }

    public boolean isDirected() {
        return this.x != 0 || this.y != 0;
    }

    public boolean rookLikeDirection() {
        return this.x == 0 || this.y == 0;
    }
}
