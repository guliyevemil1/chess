package com.eguliyev.chess.model;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Square {
    public int x;
    public int y;

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

    public boolean atLeastOneNonZero() {
        return this.x != 0 || this.y != 0;
    }

    public boolean atLeastOneZero() {
        return this.x == 0 || this.y == 0;
    }

    public Square getMovementDirection(Square that) {
        Square result = new Square(0,0);
        result.x = Integer.signum(that.x - this.x);
        result.y = Integer.signum(that.y - this.y);

        if (result.atLeastOneNonZero()) {
            if (Math.abs(that.x - this.x) == Math.abs(that.y - this.y)) {
                return result;
            } else if (result.atLeastOneZero()) {
                return result;
            }
        }

        return null;
    }
}
