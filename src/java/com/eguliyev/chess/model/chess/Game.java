package com.eguliyev.chess.model.chess;

import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Created by eguliyev on 1/19/15.
 */
public class Game {
    public final static Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(Piece.class, new PieceSerializer())
            .registerTypeAdapter(Board.class, new BoardSerializer())
            .create();

    long gameId;
    public Board board = new Board();
    Player whitePlayer = new Player();
    Player blackPlayer = new Player();

    private static class GameSerializer implements JsonSerializer<Game> {
        @Override
        public JsonElement serialize(Game src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            return null;
        }
    }

    private static class BoardSerializer implements JsonSerializer<Board> {
        @Override
        public JsonElement serialize(Board src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pieces", gson.toJsonTree(src.pieces));
            jsonObject.addProperty("turn", src.turn.toShortString());
            jsonObject.add("enpassantSquare", gson.toJsonTree(src.enPassantableSquare));
            return jsonObject;
        }
    }


    private static class PieceSerializer implements JsonSerializer<Piece> {
        @Override
        public JsonElement serialize(Piece src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            JsonPrimitive jsonObject = new JsonPrimitive(src.color.toShortString() + src.pieceKind.toShortString());
            return jsonObject;
        }
    }
}
