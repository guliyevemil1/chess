package com.eguliyev.chess.model.chess.piece2;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.chess.*;
import com.eguliyev.chess.model.chess.piece2.Piece;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
        this.pieceKind = PieceKind.QUEEN;
    }

    @Override
    public String toString() {
        return "Q";
    }
}
