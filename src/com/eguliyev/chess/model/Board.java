package com.eguliyev.chess.model;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.piece.*;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Board {
    public static final int BOARD_SIZE = 8;
    protected Color turn = Color.WHITE;
    protected Square enPassantableSquare = null;
    protected Piece[][] pieces;

    King whiteKing;
    King blackKing;

    public Board(Board that) {
        this.pieces = new Piece[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.pieces[i][j] = that.pieces[i][j];
            }
        }

        this.whiteKing = that.whiteKing;
        this.blackKing = that.blackKing;
    }

    public Board() {
        pieces = new Piece[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                pieces[i][j] = null;
            }
        }

                        new Rook    (this, new Square(0,0), Color.WHITE);
                        new Knight  (this, new Square(1,0), Color.WHITE);
                        new Bishop  (this, new Square(2,0), Color.WHITE);
                        new Queen   (this, new Square(3,0), Color.WHITE);
        whiteKing =     new King    (this, new Square(4,0), Color.WHITE);
                        new Bishop  (this, new Square(5,0), Color.WHITE);
                        new Knight  (this, new Square(6,0), Color.WHITE);
                        new Rook    (this, new Square(7,0), Color.WHITE);

        for (int i = 0; i < BOARD_SIZE; i++) {
            new Pawn(this, new Square(i,1), Color.WHITE);
        }

                        new Rook    (this, new Square(0,7), Color.BLACK);
                        new Knight  (this, new Square(1,7), Color.BLACK);
                        new Bishop  (this, new Square(2,7), Color.BLACK);
                        new Queen   (this, new Square(3,7), Color.BLACK);
        blackKing =     new King    (this, new Square(4,7), Color.BLACK);
                        new Bishop  (this, new Square(5,7), Color.BLACK);
                        new Knight  (this, new Square(6,7), Color.BLACK);
                        new Rook    (this, new Square(7,7), Color.BLACK);

        for (int i = 0; i < BOARD_SIZE; i++) {
            new Pawn(this, new Square(i,6), Color.BLACK);
        }
    }

//    public boolean canAttack(Color color, Square square) throws ChessException {
//        for (int i = 0; i < BOARD_SIZE; i++) {
//            for (int j = 0; j < BOARD_SIZE; j++) {
//                if (pieces[i][j] != null &&
//                        pieces[i][j].color == color &&
//                        pieces[i][j].canTakeOrMoveTo(square) != Piece.MoveKind.ILLEGAL) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public Piece getPiece(Square square) {
        return pieces[square.x][square.y];
    }

//    public boolean isEmpty(Square square) {
//        return getPiece(square) == null;
//    }
//
//    public King getKing(Color c) {
//        if (c == Color.WHITE) {
//            return whiteKing;
//        } else {
//            return blackKing;
//        }
//    }
//
//    @Override
//    public String toString() {
//        String result = "";
//
//        for (int i = 0; i < BOARD_SIZE; i++) {
//            for (int j = 0; j < BOARD_SIZE; j++) {
//                if (pieces[j][BOARD_SIZE - 1 - i] == null) {
//                    result += " ";
//                } else {
//                    result += pieces[j][BOARD_SIZE - 1 - i].toString();
//                }
//                result += "" + j + (BOARD_SIZE - 1 -i) + " ";
//            }
//            result += "\n";
//        }
//        return result;
//    }
}
