package com.eguliyev.chess.controller;

import com.eguliyev.chess.ChessConstants;
import org.mortbay.jetty.Server;

/**
 * Created by eguliyev on 12/23/14.
 */
public class ChessHttpServer {
    public static ChessHttpServer server = new ChessHttpServer();

    private ChessHttpServer() {
        this(ChessConstants.DEFAULT_PORT);
    }

    private ChessHttpServer(int port) {
        Server server;
        server = new Server(port);
        server.setHandler(new ChessHandler());

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
