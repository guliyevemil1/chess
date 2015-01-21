package com.eguliyev.chess.model.chess.piece2;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.chess.*;
import com.eguliyev.chess.model.chess.piece2.Piece;

/**
 * Created by eguliyev on 12/20/14.
 */
public class King extends Piece {
    private boolean hasMoved;

    public King(Color color) {
        super(color);
        this.pieceKind = PieceKind.KING;
        hasMoved = false;
    }

    @Override
    public String toString() {
        return "K";
    }
}
