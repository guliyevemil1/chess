package com.eguliyev.chess.model.chess.piece;

import com.eguliyev.chess.model.*;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Knight extends Piece {
    @Override
    public MoveKind canMoveTo(Square next) {
        int distance = 0;
        int tmp;
        tmp = next.x - this.currentSquare.x;
        distance += tmp * tmp;
        tmp = next.y - this.currentSquare.y;
        distance += tmp * tmp;

        if (distance == 5) {
            return MoveKind.NORMAL;
        } else {
            return MoveKind.ILLEGAL;
        }
    }

    public Knight(Board board, Square initial, Color color) {
        super(board, initial, color);
        this.pieceKind = PieceKind.KNIGHT;
    }

    @Override
    public String toString() {
        return "N";
    }
}
