package com.eguliyev.chess.model.piece;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.*;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Pawn extends Piece {
    private static enum MoveKind {
        ONE, TWO, TAKE, ENPASSANT, ILLEGAL
    }

    public Pawn(Board board, Square initial, Color color) {
        super(board, initial, color);
        this.pieceKind = PieceKind.PAWN;
    }

    public MoveKind movementKind(Square next) {
        if (next.x == this.currentSquare.x && (next.y - this.currentSquare.y) * this.color.toInt() == 1) {
            return MoveKind.ONE;
        } else if (next.x == this.currentSquare.x && (next.y - this.currentSquare.y) * this.color.toInt() == 2) {
            return MoveKind.TWO;
        } else if (Math.abs(next.x - this.currentSquare.x) == 1 &&
                (next.y - this.currentSquare.y) * this.color.toInt() == 1 &&
                this.board.getPiece(next) != null &&
                this.board.getPiece(next).color == this.color.opposite()) {
            return MoveKind.TAKE;
        } else if (Math.abs(next.x - this.currentSquare.x) == 1 &&
                (next.y - this.currentSquare.y) * this.color.toInt() == 1 &&
                next.squareEquals(this.board.getEnpassantableSquare())) {
            return MoveKind.ENPASSANT;
        } else {
            return MoveKind.ILLEGAL;
        }
    }

    @Override
    public Piece.MoveKind canMoveTo(Square next) {
        MoveKind moveKind = movementKind(next);
        if (moveKind == MoveKind.ONE || moveKind == MoveKind.TWO) {
            return Piece.MoveKind.NORMAL;
        } else {
            return Piece.MoveKind.ILLEGAL;
        }
    }

    @Override
    public Piece.MoveKind canTakeOrMoveTo(Square next) throws ChessException {
        MoveKind moveKind = movementKind(next);
        if (moveKind == MoveKind.ILLEGAL) {
            return Piece.MoveKind.ILLEGAL;
        } else if (moveKind == MoveKind.ENPASSANT) {
            return Piece.MoveKind.ENPASSANT;
        } else {
            return Piece.MoveKind.NORMAL;
        }
    }

    @Override
    public String toString() {
        return "P";
    }
}
