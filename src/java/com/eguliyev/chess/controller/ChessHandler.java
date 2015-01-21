package com.eguliyev.chess.controller;

import com.eguliyev.chess.model.chess.Game;
import com.eguliyev.chess.model.chess.Move;
import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
* Created by eguliyev on 12/26/14.
*/
public class ChessHandler extends AbstractHandler {

    Path mainPath = Paths.get("src/web/easel.html");
    Game game = new Game();

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String kind = request.getParameter("kind");
        PrintWriter out = response.getWriter();

        if (kind == null) {
            response.setContentType("text/html;charset=utf-8");
            try {
                Stream<String> lines = Files.lines(mainPath);
                lines.forEach(line -> out.println(line));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else if (kind.equals("start")) {
            response.setContentType("application/json");
            out.print(Game.gson.toJson(game.board));
        } else if (kind.equals("move")) {
            Move move = new Move(
                    new String[] {
                            request.getParameter("prev_x"),
                            request.getParameter("prev_y"),
                            request.getParameter("next_x"),
                            request.getParameter("next_y"),
                            request.getParameter("promotion")
                    });

            response.setContentType("application/json");
            if (move.attemptMove(game.board)) {
                System.out.println("Success!");
                out.print(Game.gson.toJson(game.board));
            } else {
                System.out.println("Failure!");
                out.print(Game.gson.toJson(null));
            }
            System.out.println(game.board.getTurn());
        }
    }

    private void handleImage(String requestURI, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");

        byte[] buf = new byte[1024];
        ServletOutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(requestURI);

        int count;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }

        in.close();
    }

    private void serveRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/")) {
            requestURI = requestURI.substring(1);
        }

        if ("".equals(requestURI)) {
            handleRequest(request, response);
        } else if ("favicon.ico".equals(requestURI)) {
            handleImage("src/web/resources/favicon.ico", response);
        } else if (requestURI.endsWith(".png")) {
            handleImage(requestURI, response);
        }
    }

    public void handle(String target,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       int x) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);

        serveRequest(request, response);

        Request base_request = (request instanceof Request) ? (Request) request
                : HttpConnection.getCurrentConnection().getRequest();
        base_request.setHandled(true);
    }
}