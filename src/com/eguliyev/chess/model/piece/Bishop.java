package com.eguliyev.chess.model.piece;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.*;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Bishop extends Piece {
    public Bishop(Board board, Square initial, Color color) {
        super(board, initial, color);
        this.pieceKind = PieceKind.BISHOP;
    }

    @Override
    public MoveKind canMoveTo(Square next) throws ChessException {
        Square direction = getDirection(next);

        if (!direction.atLeastOneZero()) {
            return horizontalVerticalMovementHelper(direction, next);
        } else {
            return MoveKind.ILLEGAL;
        }
    }

    @Override
    public String toString() {
        return "B";
    }
}
