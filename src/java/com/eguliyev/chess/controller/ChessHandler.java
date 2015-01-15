package com.eguliyev.chess.controller;

import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* Created by eguliyev on 12/26/14.
*/
public class ChessHandler extends AbstractHandler {
    public void handle(String target,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       int x) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<p>Hello</p>");

        Request base_request = (request instanceof Request) ? (Request)request
                : HttpConnection.getCurrentConnection().getRequest();
        base_request.setHandled(true);
    }
}
