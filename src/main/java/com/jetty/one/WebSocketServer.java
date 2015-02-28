package com.jetty.one;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.FileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
/**
 * Created by zuoyan on 2015/2/12.
 */
public class WebSocketServer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;


    private int port;

    public static void main(String[] args) {

        WebSocketServer server = new WebSocketServer(8085);
        server.start();

    }

    public WebSocketServer(int port) {
        this.port=port;
    }

    public void start(){

        server = new Server(port);

        SpamWebSocketHandler myWebSocketHandler = new SpamWebSocketHandler();

        URL url=this.getClass().getClassLoader() .getResource("org/noahx/websocket/http");

        ResourceHandler resourceHandler=new ResourceHandler();
        try {
            resourceHandler.setBaseResource(new FileResource(url));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        myWebSocketHandler.setHandler(resourceHandler);

        server.setHandler(myWebSocketHandler);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
}