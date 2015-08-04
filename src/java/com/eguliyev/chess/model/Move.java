package com.eguliyev.chess.model;

/**
 * Created by eguliyev on 12/27/14.
 */
public final class Move {
    enum MoveKind {
        REGULAR,
        PAWN2,
        PAWN1,
        PAWN_TAKE,
        PAWN_PROMOTION,
        PAWN_TAKE_PROMOTION,
        ENPASSANT,
        KING_SIDE_CASTLE,
        QUEEN_SIDE_CASTLE,
        ILLEGAL,
        NONE
    }

    int startX;
    int endX;
    int startY;
    int endY;
    Piece.PieceKind promotionPiece;
    MoveKind moveKind;

    public static final Move UNMOVED = new Move(-1,-1,-1,-1);
    public static final Move UNPARSED = UNMOVED;

    public static final int KING_X = 4;
    public static final int KING_SIDE_ROOK_X = 7;
    public static final int QUEEN_SIDE_ROOK_X = 0;
    public static final int KING_SIDE_CASTLE_X = 6;
    public static final int QUEEN_SIDE_CASTLE_X = 2;

    public Move(int startX,
                int endX,
                int startY,
                int endY) {
        this(startX, endX, startY, endY, Piece.PieceKind.NONE);
    }

    public Move(int startX,
                int endX,
                int startY,
                int endY,
                Piece.PieceKind promotionPiece) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.promotionPiece = promotionPiece;
        this.moveKind = MoveKind.NONE;
    }

    private Move(String... result) {
        this.startX = Integer.parseInt(result[0]);
        this.startY = Integer.parseInt(result[1]);
        this.endX = Integer.parseInt(result[2]);
        this.endY = Integer.parseInt(result[3]);
        this.promotionPiece = Piece.PieceKind.NONE;
        this.moveKind = MoveKind.NONE;
    }

    public static Move createMove(String... result) {
        try {
            return new Move(result);
        } catch (NumberFormatException e) {
            return UNPARSED;
        }
    }

    boolean isDiagonal() {
        return Math.abs(startX - endX) == Math.abs(startY - endY) && startX - endX != 0;
    }

    boolean isHorOrVert() {
        return (startX == endX) ^ (startY == endY);
    }

    boolean isKnightMove() {
        int diffX = startX - endX;
        int diffY = startY - endY;
        return diffX * diffX + diffY * diffY == 5;
    }

    private MoveKind castleCheckHelper(Board board) {
        Piece myPiece;
        MoveKind moveKind;
        if (endX == KING_SIDE_CASTLE_X) {
            myPiece = board.getPiece(KING_SIDE_ROOK_X, startY);
            moveKind = MoveKind.KING_SIDE_CASTLE;
        } else if (endX == QUEEN_SIDE_CASTLE_X) {
            myPiece = board.getPiece(QUEEN_SIDE_ROOK_X, startY);
            moveKind = MoveKind.QUEEN_SIDE_CASTLE;
        } else {
            return MoveKind.ILLEGAL;
        }

        return myPiece.getPieceKind() == Piece.PieceKind.ROOK && myPiece.getLastMove() == UNMOVED ?
                moveKind : MoveKind.ILLEGAL;
    }

    MoveKind isValidQueen(Board board) {
        return emptyPath(board) && (isHorOrVert() || isDiagonal()) ? MoveKind.REGULAR : MoveKind.ILLEGAL;
    }

    MoveKind isValidRook(Board board) {
        return emptyPath(board) && isHorOrVert() ? MoveKind.REGULAR : MoveKind.ILLEGAL;
    }

    MoveKind isValidBishop(Board board) {
        return emptyPath(board) && isDiagonal() ? MoveKind.REGULAR : MoveKind.ILLEGAL;
    }

    MoveKind isValidKnight(Board board) {
        return emptyPath(board) && isKnightMove() ? MoveKind.REGULAR : MoveKind.ILLEGAL;
    }

    MoveKind isValidPawn(Board board) {
        Piece myPiece = board.getPiece(startX, startY);
        MoveKind moveKind;
        int diffY = (endY - startY) * myPiece.getColor().sign();

        if (endX == myPiece.getColor().end()) {
            moveKind = MoveKind.PAWN_PROMOTION;
        }

        switch (endX - startX) {
            case 0:
                if (diffY != 1 && diffY != 2) {
                    return MoveKind.ILLEGAL;
                }

                if (diffY == 2) {
                    if (!(myPiece.getLastMove() == UNMOVED)) {
                        return MoveKind.ILLEGAL;
                    }
                    moveKind = MoveKind.PAWN2;
                } else {
                    moveKind = MoveKind.PAWN1;
                }

                return emptyPath(board) && board.getPiece(endX, endY) == Piece.NONE ? moveKind : MoveKind.ILLEGAL;
            case 1:
            case -1:
                if (diffY != 1) {
                    return  MoveKind.ILLEGAL;
                }

                Move lastMove = board.getLastMove();

                if (board.getPiece(endX, endY) == Piece.NONE) {
                    return startY == myPiece.getColor().start(5) &&
                            lastMove.moveKind == MoveKind.PAWN2 &&
                            lastMove.startX == this.endX ?
                            MoveKind.ENPASSANT : MoveKind.ILLEGAL;
                } else if (myPiece.getColor() != board.getPiece(endX, endY).getColor()) {
                    return diffY == 1 ? MoveKind.PAWN1 : MoveKind.PAWN2;
                }
            default:
                return MoveKind.ILLEGAL;
        }
    }

    MoveKind isValidCastle(Board board) {
        Piece myPiece = board.getPiece(startX, startY);
        return (myPiece.getLastMove() == UNMOVED &&
                startX == KING_X &&
                startY == endY &&
                emptyPath(board)) ? castleCheckHelper(board) : MoveKind.ILLEGAL;
    }

    boolean emptyPath(Board board) {
        if (board.getPiece(startX,startY).getColor() == board.getPiece(endX,endY).getColor()) {
            return false;
        }

        if (isDiagonal() || isHorOrVert()) {
            int stepX = Integer.signum(endX - startX);
            int stepY = Integer.signum(endY - startY);

            int x = startX + stepX;
            int y = startY + stepX;

            while (x + stepX <= endX && y + stepY <= endY) {
                if (board.getPiece(x, y) != Piece.NONE) {
                    return false;
                }
                x += stepX;
                y += stepY;
            }

            return true;
        } else if (isKnightMove()) {
            return true;
        } else {
            return false;
        }
    }
}
