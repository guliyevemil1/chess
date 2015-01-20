package com.eguliyev.chess.model;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.chess.piece.*;

/**
 * Created by eguliyev on 12/27/14.
 */
public class BoardWrapper {
    Board board;

    BoardWrapper() {
        this.board = new Board();
    }

    BoardWrapper(Board board) {
        this.board = board;
    }

    public Color getTurn() {
        return board.getTurn();
    }

    public void setPiece(int x, int y, Piece piece) {
        this.board.pieces[x][y] = piece;

        if (piece != null) {
            piece.currentSquare = new Square(x, y);
        }
    }

    public void setPiece(Square square, Piece piece) {
        this.board.pieces[square.x][square.y] = piece;
        piece.currentSquare = square;
    }

    public Piece getPiece(int x, int y) {
        return this.board.pieces[x][y];
    }

    public Square getEnpassantableSquare() {
        return this.board.enPassantableSquare;
    }

    public void setEnpassantableSquare(Square that) {
        this.board.enPassantableSquare = that;
    }

    public void changeSide() {
        this.board.turn = this.board.turn.opposite();
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

    public void makeMove(int a, int b, int c, int d) {
        new Move(a,b,c,d).attemptMove(this.board);
        System.out.println(this);
    }

    public static void main(String[] args) throws ChessException {
        BoardWrapper board = new BoardWrapper();

        System.out.println(board);
        board.makeMove(4,1,4,3);
        board.makeMove(3,6,3,4);
        board.makeMove(4,3,4,4);
        board.makeMove(5,6,5,4);
        board.makeMove(4,4,5,5);
        board.makeMove(6,7,5,5);
        board.makeMove(3,0,5,2);
        board.makeMove(4,6,5,5);
        board.makeMove(5,0,1,4);
        board.makeMove(1,7,0,5);
//        System.out.print(board.board.blackKing.isInCheck());
    }
}
