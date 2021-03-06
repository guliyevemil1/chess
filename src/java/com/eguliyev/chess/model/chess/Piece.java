package com.eguliyev.chess.model.chess;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.chess.piece.Bishop;
import com.eguliyev.chess.model.chess.piece.Knight;
import com.eguliyev.chess.model.chess.piece.Queen;
import com.eguliyev.chess.model.chess.piece.Rook;

/**
 * Created by eguliyev on 12/20/14.
 */
public abstract class Piece {
    public static enum PieceKind {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING;

        public Piece create(Board board, Color color) throws ChessException {
            switch (this) {
                case KNIGHT:
                    return new Knight(board, null, color);
                case QUEEN:
                    return new Queen(board, null, color);
                case BISHOP:
                    return new Bishop(board, null, color);
                case ROOK:
                    return new Rook(board, null, color);
                default:
                    return null;
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
    public BoardWrapper board;
    public Square currentSquare;
    public Color color;

    public Piece(Board board, Square initial, Color color) {
        board.pieces[initial.x][initial.y] = this;
        this.board = new BoardWrapper(board);
        currentSquare = initial;
        this.color = color;
    }

    public Direction getDirection(Square next) {
        return this.currentSquare.getMovementDirection(next);
    }

    public MoveKind horizontalVerticalMovementHelper(Square next) throws ChessException {
        return horizontalVerticalMovementHelper(getDirection(next), next);
    }

    public MoveKind horizontalVerticalMovementHelper(Direction direction, Square next) throws ChessException {
        if (direction == null) {
            System.err.println("No direction given.");
            return MoveKind.ILLEGAL;
        } else {
            int xDiff = direction.x;
            int yDiff = direction.y;
            int distance = Math.max(Math.abs(currentSquare.x - next.x), Math.abs(currentSquare.y - next.y));

            Square tmp = new Square(this.currentSquare);

            for (int counter = 1; counter < distance ; counter++) {
                tmp.x += xDiff;
                tmp.y += yDiff;
                if (board.getPiece(tmp) != null) {
                    System.err.println("Object in the way.");
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

        if (pieceToBeTaken == null || pieceToBeTaken.color == color.opposite()) {
            return canMoveTo(next);
        } else {
            System.err.println("Cannot take own piece.");
            return MoveKind.ILLEGAL;
        }
    }

    public boolean isItMyTurn() {
        return this.board.getTurn() == this.color;

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

        System.err.println("The move was illegal.");
        return MoveKind.ILLEGAL;
    }

    public MoveKind move(Square next) throws ChessException {
        return moveHelper(next);
    }

    private void takeCareOfEnpassant(MoveKind myMove) {
        if (myMove == MoveKind.ENPASSANT) {
            this.board.setEnpassantableSquare(new Square(
                    this.currentSquare.x,
                    this.color.pawnStartingPosition(2)
            ));
        } else {
            this.board.setEnpassantableSquare(null);
        }
    }

    public boolean attemptMove(Square next, PieceKind pieceKindForPromotion) throws ChessException {
        Board oldBoard = this.board.board;
        Board oldBoardCopy = new Board(oldBoard);

        MoveKind myMove = this.move(next);

        if (myMove != MoveKind.ILLEGAL && !this.board.getKing(this.color).isInCheck()) {
            this.board.setPiece(this.currentSquare.x, this.currentSquare.y, null);
            if (myMove == MoveKind.PROMOTION) {
                this.board.setPiece(next, pieceKindForPromotion.create(this.board.board, this.color));
            } else {
                this.board.setPiece(next, this);
            }

            takeCareOfEnpassant(myMove);
            System.out.println("CHANGING SIDE YO");
            this.board.changeSide();
            return true;
        }

        this.board.board = oldBoardCopy;
        return false;
    }
}
