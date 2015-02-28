package com.jetty.two;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;;import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
        System.err.println("@@@@@@@@@@@@@@@@@@@@@@");
        WebAppContext webAC =new WebAppContext();
        webAC.setContextPath("/");
        webAC.setBaseResource(Resource.newClassPathResource(""));
        webAC.setConfigurations(new Configuration[0]);
        webAC.addServlet(MyWebSocketServlet.class, "/hello");
        HandlerList handlers = new HandlerList();
        handlers.addHandler(webAC);
        server.setHandler(handlers);

//        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
//        servletContextHandler.setContextPath("/");
//
//        servletContextHandler.addServlet(new ServletHolder(new MyWebSocketServlet()), "/hello");
//        HandlerList handlers = new HandlerList();
//        handlers.addHandler(servletContextHandler);
//        server.setHandler(handlers);

        server.start();
        System.err.println("@@@@@@@@@@@@@@@@@@@@@@");
        server.join();
    }
}
