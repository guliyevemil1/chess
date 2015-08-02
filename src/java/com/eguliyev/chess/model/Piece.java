package com.eguliyev.chess.model;

/**
 * Created by emilguliyev on 8/2/15.
 */
public class Piece {
    public enum PieceKind {
        NONE,
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        QUEEN,
        KING;
    }

    public static Piece NONE = new Piece(PieceKind.NONE, null);

    private PieceKind pieceKind;
    private Color color;
    private Move lastMove;

    public Piece(PieceKind pieceKind, Color color) {
        this.pieceKind = pieceKind;
        this.color = color;
        this.lastMove = Move.UNMOVED;
    }

    public Color getColor() {
        return color;
    }

    public PieceKind getPieceKind() {
        return pieceKind;
    }

    public Move getLastMove() {
        return lastMove;
    }
}
