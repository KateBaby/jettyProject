package com.jetty.two;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zuoyan on 2015/2/28.
 */
@WebServlet("/hello")
public class MyWebSocketServlet extends WebSocketServlet {
    private String msg;
    private static final long serialVersionUID = -7302427588920888589L;
    public MyWebSocketServlet(){}
    public MyWebSocketServlet(String massage){
        this.msg = massage;
    }
    public WebSocket doWebSocketConnect(HttpServletRequest request, String arg1) {
        return new MyWebSocket();
    }
}