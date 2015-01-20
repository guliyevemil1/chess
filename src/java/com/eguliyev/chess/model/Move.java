package com.eguliyev.chess.model;

import com.eguliyev.chess.exception.ChessException;

/**
 * Created by eguliyev on 12/27/14.
 */
public class Move {
    private Square start;
    private Square end;
    private Piece.PieceKind pieceToPromoteTo;

    public Move(String[] strings) {
        this(
            strings[0].charAt(0) - '0',
            strings[1].charAt(0) - '0',
            strings[2].charAt(0) - '0',
            strings[3].charAt(0) - '0'
        );

        if (strings.length == 5) {
            switch (strings[4]) {
                case "Q":
                    this.pieceToPromoteTo = Piece.PieceKind.QUEEN;
                    break;
                case "B":
                    this.pieceToPromoteTo = Piece.PieceKind.BISHOP;
                    break;
                case "N":
                    this.pieceToPromoteTo = Piece.PieceKind.KNIGHT;
                    break;
                case "R":
                    this.pieceToPromoteTo = Piece.PieceKind.ROOK;
                    break;
                default:
                    this.pieceToPromoteTo = null;
            }
        }
    }

    public Move(int a, int b, int c, int d) {
        this.start = new Square(a,b);
        this.end = new Square(c,d);
    }

    public boolean attemptMove(Board board) {
        Piece startingPiece = board.getPiece(start);

        if (startingPiece != null) {
            try {
                return startingPiece.attemptMove(end, pieceToPromoteTo);
            } catch (ChessException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
