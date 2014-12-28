package com.eguliyev.chess.controller;

import com.eguliyev.chess.ChessConstants;
import org.mortbay.jetty.Server;

/**
 * Created by eguliyev on 12/23/14.
 */
public class ChessHttpServer {

    ChessHttpServer() {
        Server server;
        server = new Server(ChessConstants.DEFAULT_PORT);
        server.setHandler(new ChessHandler());

        try {
            server.start();
            server.join();
        } catch (Exception e) {
//            logger.fatal("Jetty threw an exception during execution: ", e);
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        ChessHttpServer chessHttpServer = new ChessHttpServer();
    }
}
