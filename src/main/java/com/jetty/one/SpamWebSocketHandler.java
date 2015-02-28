package com.jetty.one;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Timer;
/**
 * Created by zuoyan on 2015/2/12.
 */
public class SpamWebSocketHandler extends WebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {

        if (logger.isDebugEnabled()) {
            logger.debug("url=" + request.getRequestURL() + ",protocol=" + protocol);
        }

        return new SpamWebSocket();
    }

    public class SpamWebSocket implements WebSocket.OnTextMessage {

        private Logger logger = LoggerFactory.getLogger(this.getClass());

        private Connection connection;

        private Timer timer = new Timer();


        public void onMessage(String data) {
            if (logger.isDebugEnabled()) {
                logger.debug("onMessage");
            }
        }

        public void onOpen(Connection connection) {
            if (logger.isDebugEnabled()) {
                logger.debug("onOpen");
            }
            this.connection = connection;

        }

        public void onClose(int closeCode, String message) {
            if (logger.isDebugEnabled()) {
                logger.debug("onClose");
            }
            timer.cancel();
        }

        public void send(String msg) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("send:" + msg);
                }
                connection.sendMessage(msg);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                timer.cancel();
            }
        }

    }
}