package com.eguliyev.chess.model.chess.piece2;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.chess.*;
import com.eguliyev.chess.model.chess.piece2.Bishop;
import com.eguliyev.chess.model.chess.piece2.Knight;
import com.eguliyev.chess.model.chess.piece2.Queen;
import com.eguliyev.chess.model.chess.piece2.Rook;

/**
 * Created by eguliyev on 12/20/14.
 */
public abstract class Piece {
    public static enum PieceKind {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING;

        public Piece create(Color color) throws ChessException {
            switch (this) {
                case KNIGHT:
                    return new Knight(color);
                case QUEEN:
                    return new Queen(color);
                case BISHOP:
                    return new Bishop(color);
                case ROOK:
                    return new Rook(color);
                case PAWN:
                    return new Pawn(color);
                case KING:
                    return new King(color);
                default:
                    throw new ChessException("What piece is that?");
            }
        }

        public String toShortString() {
            if (this == PieceKind.KNIGHT) {
                return "n";
            } else {
                return this.toString().substring(0, 1).toLowerCase();
            }
        }
    }

    public PieceKind pieceKind;
    public Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public boolean isItMyTurn(Color turn) {
        return turn == this.color;

    }
}
