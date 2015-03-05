package com.jetty.two;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zuoyan on 2015/3/5.
 */
public class MyWebSocketSeverlet1 extends WebSocketServlet {
    private String msg;
    private static final long serialVersionUID = -7302427588920888589L;
    public MyWebSocketSeverlet1(){}
    public MyWebSocketSeverlet1(String massage){
        this.msg = massage;
    }

    public WebSocket doWebSocketConnect(HttpServletRequest request, String arg1) {
        Map map = request.getParameterMap();
        System.err.println("查看参数========"+map.toString());
        MyWebSocket myWebSocket = new MyWebSocket();
        return myWebSocket;
    }
}
