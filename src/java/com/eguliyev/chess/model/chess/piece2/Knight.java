package com.eguliyev.chess.model.chess.piece2;

import com.eguliyev.chess.model.chess.*;
import com.eguliyev.chess.model.chess.piece2.Piece;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
        this.pieceKind = PieceKind.KNIGHT;
    }

    @Override
    public String toString() {
        return "N";
    }
}
