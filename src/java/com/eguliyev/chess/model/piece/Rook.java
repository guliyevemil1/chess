package com.eguliyev.chess.model.piece;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.*;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Rook extends Piece {
    public boolean hasMoved;

    public Rook(Board board, Square initial, Color color) {
        super(board, initial, color);
        this.pieceKind = PieceKind.ROOK;
        hasMoved = false;
    }

    @Override
    public MoveKind canMoveTo(Square next) throws ChessException {
        Direction direction = getDirection(next);

        if (direction != null && direction.rookLikeDirection()) {
            return horizontalVerticalMovementHelper(direction, next);
        } else {
            return MoveKind.ILLEGAL;
        }
    }

    @Override
    public MoveKind move(Square next) throws ChessException {
        MoveKind moveKind = moveHelper(next);
        if (moveKind == MoveKind.NORMAL) {
            hasMoved = true;
        }
        return moveKind;
    }

    @Override
    public String toString() {
        return "R";
    }

}
