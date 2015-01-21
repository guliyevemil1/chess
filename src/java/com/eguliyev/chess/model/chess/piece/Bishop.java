package com.eguliyev.chess.model.chess.piece;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.chess.*;

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
        Direction direction = getDirection(next);

        if (direction != null && !direction.rookLikeDirection()) {
            return horizontalVerticalMovementHelper(direction, next);
        } else {
            System.err.println("Direction doesn't make sense for bishop.");
            return MoveKind.ILLEGAL;
        }
    }

    @Override
    public String toString() {
        return "B";
    }
}
