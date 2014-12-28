package com.eguliyev.chess.model;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Square {
    public int x;
    public int y;

    Square() {}

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Square(Square that) {
        this.x = that.x;
        this.y = that.y;
    }

    public boolean squareEquals(Square that) {
        return this.x == that.x && this.y == that.y;
    }

    public Direction getMovementDirection(Square that) {
        Direction result = new Direction(this, that);

        if (result.isDirected()) {
            if (Math.abs(that.x - this.x) == Math.abs(that.y - this.y)) {
                return result;
            } else if (result.rookLikeDirection()) {
                return result;
            }
        }

        return null;
    }
}
