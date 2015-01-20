package com.eguliyev.chess.model.chess.piece;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.*;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Queen extends Piece {

    public Queen(Board board, Square initial, Color color) {
        super(board, initial, color);
        this.pieceKind = PieceKind.QUEEN;
    }

    @Override
    public MoveKind canMoveTo(Square next) throws ChessException {
        return horizontalVerticalMovementHelper(next);
    }

    @Override
    public String toString() {
        return "Q";
    }
}
