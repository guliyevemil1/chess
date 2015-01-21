package com.eguliyev.chess.model.chess.piece2;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.chess.Color;
//import com.eguliyev.chess.model.chess.Piece;
import com.eguliyev.chess.model.chess.Square;
import com.eguliyev.chess.model.chess.piece2.*;

/**
 * Created by eguliyev on 12/20/14.
 */
public class Board {
    public static final int BOARD_SIZE = 8;
    protected Color turn = Color.WHITE;
    protected Square enPassantableSquare = null;
    protected Piece[][] pieces;

    private Mover mover = new Mover();

    protected King whiteKing;
    protected King blackKing;

    public Color getTurn() {
        return turn;
    }

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

    private King createPieces(int row, Color color) throws ChessException {
        pieces[0][row] = Piece.PieceKind.ROOK.create(color);
        pieces[1][row] = Piece.PieceKind.KNIGHT.create(color);
        pieces[2][row] = Piece.PieceKind.BISHOP.create(color);
        pieces[3][row] = Piece.PieceKind.QUEEN.create(color);
        pieces[4][row] = Piece.PieceKind.KING.create(color);
        pieces[5][row] = Piece.PieceKind.BISHOP.create(color);
        pieces[6][row] = Piece.PieceKind.KNIGHT.create(color);
        pieces[7][row] = Piece.PieceKind.ROOK.create(color);

        int nextRow = row + (-2 * (row % 2) + 1);

        for (int i = 0; i < BOARD_SIZE; i++) {
            pieces[i][nextRow] = Piece.PieceKind.PAWN.create(color);
        }

        return (King) pieces[4][row];
    }

    public Board() throws ChessException {
        pieces = new Piece[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                pieces[i][j] = null;
            }
        }

        whiteKing = createPieces(0, Color.WHITE);
        blackKing = createPieces(7, Color.BLACK);
    }
}
