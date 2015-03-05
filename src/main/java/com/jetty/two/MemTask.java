package com.jetty.two;

import java.util.Random;
import java.util.TimerTask;

/**
 * Created by zuoyan on 2015/3/5.
 */
public class MemTask extends TimerTask {

    private MyWebSocketServlet.MyJettyWebSocket myWebSocket;

    public MemTask(MyWebSocketServlet.MyJettyWebSocket myWebSocket) {
        this.myWebSocket = myWebSocket;
    }

    @Override
    public void run() {
        myWebSocket.getConn().setMaxTextMessageSize(50000);
        if(myWebSocket.getConn().isOpen()) {
            myWebSocket.send("Hello word" + new Random().nextInt(100000));
        }
    }

}