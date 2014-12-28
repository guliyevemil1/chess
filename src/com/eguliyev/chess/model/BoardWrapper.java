package com.eguliyev.chess.model;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.piece.*;

/**
 * Created by eguliyev on 12/27/14.
 */
public class BoardWrapper {
    Board board;

    BoardWrapper(Board board) {
        this.board = board;
    }

    public void setPiece(int x, int y, Piece piece) {
        this.board.pieces[x][y] = piece;
    }

    public Piece getPiece(int x, int y) {
        return this.board.pieces[x][y];
    }

    public Square getEnpassantableSquare() {
        return this.board.enPassantableSquare;
    }

    public boolean canAttack(Color color, Square square) throws ChessException {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                if (board.pieces[i][j] != null &&
                        board.pieces[i][j].color == color &&
                        board.pieces[i][j].canTakeOrMoveTo(square) != Piece.MoveKind.ILLEGAL) {
                    return true;
                }
            }
        }
        return false;
    }

    public Piece getPiece(Square square) {
        return board.pieces[square.x][square.y];
    }
    public boolean isEmpty(Square square) {
        return getPiece(square) == null;
    }

    public King getKing(Color c) {
        if (c == Color.WHITE) {
            return board.whiteKing;
        } else {
            return board.blackKing;
        }
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                if (board.pieces[j][Board.BOARD_SIZE - 1 - i] == null) {
                    result += " ";
                } else {
                    result += board.pieces[j][Board.BOARD_SIZE - 1 - i].toString();
                }
                result += "" + j + (Board.BOARD_SIZE - 1 -i) + " ";
            }
            result += "\n";
        }
        return result;
    }
}
