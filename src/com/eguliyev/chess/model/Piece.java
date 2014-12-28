package com.eguliyev.chess.model;

import com.eguliyev.chess.exception.ChessException;

/**
 * Created by eguliyev on 12/20/14.
 */
public abstract class Piece {
    public static enum PieceKind {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
    }

    public static enum MoveKind {
        ILLEGAL, NORMAL, CASTLE, ENPASSANT
    }

    public PieceKind pieceKind;
    public BoardWrapper board;
    public Square currentSquare;
    public Color color;

    public Piece(Board board, Square initial, Color color) {
        board.pieces[initial.x][initial.y] = this;
        this.board = new BoardWrapper(board);
        currentSquare = initial;
        this.color = color;
    }
    public Square getDirection(Square next) {
        return this.currentSquare.getMovementDirection(next);
    }

    public MoveKind horizontalVerticalMovementHelper(Square next) throws ChessException {
        return horizontalVerticalMovementHelper(getDirection(next), next);
    }

    public MoveKind horizontalVerticalMovementHelper(Square direction, Square next) throws ChessException {
        if (direction == null) {
            throw new ChessException("Can't move there.");
        } else {
            int xDiff = direction.x;
            int yDiff = direction.y;
            int distance = Math.max(Math.abs(currentSquare.x - next.x), Math.abs(currentSquare.y - next.y));

            Square tmp = new Square(this.currentSquare);

            for (int counter = 1; counter < distance ; counter++) {
                tmp.x += xDiff;
                tmp.y += yDiff;
                if (board.getPiece(tmp) != null) {
                    return MoveKind.ILLEGAL;
                }
            }

            return MoveKind.NORMAL;
        }
    }

    public MoveKind canMoveTo(Square next) throws ChessException {
        throw new ChessException("Unknown piece - cannot check movement.");
    }

    public MoveKind canTakeOrMoveTo(Square next) throws ChessException {

        Piece pieceToBeTaken = board.getPiece(next);

        if ((pieceToBeTaken == null) ||
                (pieceToBeTaken.color == color.opposite())) {
            return canMoveTo(next);
        } else {
            return MoveKind.ILLEGAL;
        }
    }

    public MoveKind moveHelper(Square next) throws ChessException {


        try {
            if (this.canTakeOrMoveTo(next) != MoveKind.ILLEGAL) {
                board.setPiece(this.currentSquare.x, this.currentSquare.y, null);
                this.currentSquare = next;
                board.setPiece(this.currentSquare.x, this.currentSquare.y, this);
                return MoveKind.NORMAL;
            }
        } catch (ChessException e) {
        }

        return MoveKind.ILLEGAL;
    }

    public MoveKind move(Square next) throws ChessException {
        return moveHelper(next);
    }

    public boolean isPawn() {
        return this.pieceKind == PieceKind.PAWN;
    }

    @Deprecated
    public boolean promote(Piece newPiece) throws ChessException {
        if (isPawn()) {
            if (!newPiece.isPawn() && !(newPiece.pieceKind == PieceKind.KING)) {
                newPiece.currentSquare = this.currentSquare;
                newPiece.color = this.color;
                newPiece.board = this.board;
                board.setPiece(currentSquare.x, currentSquare.y, newPiece);
                return true;
            } else {
                return false;
            }
        } else {
            throw new ChessException("Cannot promote a non-pawn.");
        }
    }
}
