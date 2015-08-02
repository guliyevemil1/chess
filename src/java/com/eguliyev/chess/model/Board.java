package com.eguliyev.chess.model;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Board {
    public static final int BOARD_SIZE = 8;
    private Color turn = Color.WHITE;
    private Piece[][] pieces = new Piece[BOARD_SIZE][BOARD_SIZE];

    private Move lastMove;

    public Board() {
        lastMove = Move.UNMOVED;

        for (int i = 2; i < BOARD_SIZE - 2; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                pieces[i][j] = Piece.NONE;
            }
        }

        for (Color color : Color.values()) {
            int i = 0;
            for (Piece.PieceKind pieceKind : new Piece.PieceKind[] {
                    Piece.PieceKind.ROOK,
                    Piece.PieceKind.KNIGHT,
                    Piece.PieceKind.BISHOP,
                    Piece.PieceKind.QUEEN,
                    Piece.PieceKind.KING,
                    Piece.PieceKind.BISHOP,
                    Piece.PieceKind.KNIGHT,
                    Piece.PieceKind.ROOK
            }) {
                pieces[i][color.start(0)] = new Piece(pieceKind, color);
                i++;
            }
            for (i = 0; i < BOARD_SIZE; i++) {
                pieces[i][color.start(1)] = new Piece(Piece.PieceKind.PAWN, color);
            }
        }
    }

    public Color getTurn() {
        return turn;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public Move getLastMove() {
        return lastMove;
    }

    Piece getPiece(int x, int y) {
        return pieces[x][y];
    }

    private Piece.PieceKind getPieceKind(Move move) {
        return getPiece(move).getPieceKind();
    }

    private Piece getPiece(Move move) {
        return pieces[move.startX][move.startY];
    }

    private boolean isLegal(Move move) {
        return true;
    }

    private boolean handleEnPassant(Move move) {
        return true;
    }

    private boolean handleCastle(Move move) {
        return true;
    }

    public boolean move(Move move) {
        if (isLegal(move)) {
            Piece myPiece = getPiece(move);
            if (myPiece.getPieceKind() == Piece.PieceKind.NONE) {
                return false;
            }

            this.lastMove = move;
            this.turn = this.turn.opposite();

            if (myPiece.getPieceKind() == Piece.PieceKind.PAWN && move.startY == myPiece.getColor().end()) {
                pieces[move.endX][move.endY] = new Piece(move.promotionPiece, myPiece.getColor());
            } else if (myPiece.getPieceKind() == Piece.PieceKind.PAWN && move.startX != move.endX) {
                return handleEnPassant(move);
            } else if (myPiece.getPieceKind() == Piece.PieceKind.KING && Math.abs(move.startX - move.endX) == 2) {
                return handleCastle(move);
            } else {
                pieces[move.endX][move.endY] = pieces[move.startX][move.startY];
            }

            pieces[move.startX][move.startY] = Piece.NONE;

            return true;
        } else {
            return false;
        }
    }

}
