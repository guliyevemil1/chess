package com.eguliyev.chess.model.parser;

import com.eguliyev.chess.exception.ChessException;
import com.eguliyev.chess.model.Move;

/**
 * Created by eguliyev on 12/27/14.
 */
public class MoveParser {
    public static Move parse(String moveString) {
        if (moveString.matches("[0-7]{4}[QRBN]?")) {
//            try {
//                return new Move(moveString.toCharArray());
                return null;
//            } catch (ChessException e) {
//            }
        }

        return null;
    }
}
