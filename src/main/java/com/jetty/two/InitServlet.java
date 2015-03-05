package com.jetty.two;

import org.eclipse.jetty.websocket.WebSocket;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuoyan on 2015/2/28.
 */
@WebServlet(loadOnStartup=1)
public class InitServlet extends HttpServlet {

    private static final long serialVersionUID = -1936532122758235837L;

    private static List<WebSocket> socketList;
    private static List<WebSocket.Connection> connections;

    public void init(ServletConfig config) throws ServletException {
        InitServlet.socketList = new ArrayList<WebSocket>();
        System.out.println("查看配置InitServlet=========="+config.getServletContext());
        super.init(config);
        System.out.println("Server start============");
    }

    public static synchronized List<WebSocket> getSocketList() {
        return InitServlet.socketList;
    }
}