package com.eguliyev.chess.model.chess.piece;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.*;

/**
 * Created by eguliyev on 12/20/14.
 */
public class King extends Piece {
    private boolean hasMoved;

    public boolean isInCheck(Square s) throws ChessException {
        return this.board.canAttack(this.color.opposite(), this.currentSquare);
    }

    public boolean isInCheck() throws ChessException {
        return isInCheck(this.currentSquare);
    }

    private boolean canCastle(Square next) throws ChessException {
        if (hasMoved || next.y != this.currentSquare.y || isInCheck()) {
            return false;
        }

        Piece myRook;

        if (next.x == 6) {
            if (this.board.canAttack(this.color.opposite(), new Square(5, currentSquare.y)) ||
                    this.board.canAttack(this.color.opposite(), new Square(6, currentSquare.y))) {
                return false;
            }
            myRook = board.getPiece(7, currentSquare.y);
        } else if (next.x == 2) {
            if (this.board.canAttack(this.color.opposite(), new Square(2, currentSquare.y)) ||
                    this.board.canAttack(this.color.opposite(), new Square(3, currentSquare.y))) {
                return false;
            }
            myRook = board.getPiece(0, currentSquare.y);
        } else {
            return false;
        }

        if (myRook == null || myRook.pieceKind != PieceKind.ROOK || ((Rook) myRook).hasMoved) {
            return false;
        }

        return true;
    }

    @Override
    public MoveKind canMoveTo(Square next) throws ChessException {
        int xDiff = Math.abs(this.currentSquare.x - next.x);
        int yDiff = Math.abs(this.currentSquare.y - next.y);
        if (Math.max(xDiff, yDiff) == 1) {
            return MoveKind.NORMAL;
        } else if (!hasMoved && canCastle(next)) {
            return MoveKind.CASTLE;
        }
        return MoveKind.ILLEGAL;
    }

    private void moveRook(Square next) throws ChessException {
        int xi, xf;

        if (next.x == 2) {
            xi = 0;
            xf = 3;
        } else if (next.x == 6) {
            xi = 7;
            xf = 5;
        } else {
            throw new ChessException("How?!");
        }

        this.board.setPiece(xf, this.currentSquare.y, board.getPiece(xi, currentSquare.y));
    }

    @Override
    public MoveKind move(Square next) throws ChessException {
        if (!isItMyTurn()) {
            return MoveKind.ILLEGAL;
        }

        MoveKind moveKind = moveHelper(next);

        if (moveKind == MoveKind.CASTLE) {
            moveRook(next);
        }

        if (moveKind != MoveKind.ILLEGAL) {
            hasMoved = true;
            this.board.setPiece(next, this);
        }

        return moveKind;
    }

    public King(Board board, Square initial, Color color) {
        super(board, initial, color);
        this.pieceKind = PieceKind.KING;
        hasMoved = false;
    }

    @Override
    public String toString() {
        return "K";
    }
}
