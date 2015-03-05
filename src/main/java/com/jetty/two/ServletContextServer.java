package com.jetty.two;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by zuoyan on 2015/2/28.
 */
public class ServletContextServer {
    public static void main(String[] args) throws Exception {
        int PORT = 8080;

        Server server = new Server();
        Connector connector=new SelectChannelConnector();
        connector.setPort(Integer.getInteger("jetty.port", 8080).intValue());
        connector.setHost("127.0.0.1");
        server.setConnectors(new Connector[]{connector});
        System.out.println("server.connectors.length=====" + server.getConnectors().length);


        System.err.println("启动server===========start");
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(new ServletHolder(new InitServlet()), "");
        servletContextHandler.addServlet(new ServletHolder(new MyWebSocketServlet()), "/hello");
        servletContextHandler.addServlet(new ServletHolder(new MyWebSocketSeverlet1()), "/word");
        HandlerList handlers = new HandlerList();
        handlers.addHandler(servletContextHandler);
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
