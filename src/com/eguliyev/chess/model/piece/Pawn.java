package com.eguliyev.chess.model.piece;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.*;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Pawn extends Piece {
    public Pawn(Board board, Square initial, Color color) {
        super(board, initial, color);
        this.pieceKind = PieceKind.PAWN;
    }

    @Override
    public MoveKind canTakeOrMoveTo(Square next) {
        if (next.x == this.currentSquare.x && (next.y - this.currentSquare.y) * this.color.toInt() == 1) {
            return MoveKind.NORMAL;
        } else if (next.x == this.currentSquare.x && (next.y - this.currentSquare.y) * this.color.toInt() == 2) {
            return MoveKind.PAWN_TWO;
        } else if (Math.abs(next.x - this.currentSquare.x) == 1 &&
                (next.y - this.currentSquare.y) * this.color.toInt() == 1 &&
                this.board.getPiece(next) != null &&
                this.board.getPiece(next).color == this.color.opposite()) {
            return MoveKind.PAWN_TAKE;
        } else if (Math.abs(next.x - this.currentSquare.x) == 1 &&
                (next.y - this.currentSquare.y) * this.color.toInt() == 1 &&
                next.squareEquals(this.board.getEnpassantableSquare())) {
            return MoveKind.ENPASSANT;
        } else {
            return MoveKind.ILLEGAL;
        }
    }

    @Override
    public MoveKind move(Square next) throws ChessException {
        MoveKind moveKind = canTakeOrMoveTo(next);

        switch (moveKind) {
            case ENPASSANT:
                Square enpassantableSquare = this.board.getEnpassantableSquare();
                this.board.setPiece(enpassantableSquare.x, this.color.pawnStartingPosition(3), null);
            case NORMAL:
            case PAWN_TAKE:
            case PAWN_TWO:
            case PROMOTION:
                moveHelper(next);
                break;
        }

        return moveKind;
    }

    @Override
    public String toString() {
        return "P";
    }
}
