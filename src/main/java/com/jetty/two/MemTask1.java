package com.jetty.two;

import java.util.Random;
import java.util.TimerTask;

/**
 * Created by zuoyan on 2015/3/5.
 */
public class MemTask1 extends TimerTask {

    private MyWebSocket myWebSocket;

    public MemTask1(MyWebSocket myWebSocket) {
        this.myWebSocket = myWebSocket;
    }

    @Override
    public void run() {
        myWebSocket.send("Hello word" +new Random().nextInt(100000));

    }
}
