package com.jetty.one;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by zuoyan on 2015/2/25.
 */
public class SpamSmsWebSocketServer extends Server {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Server server;

    private String sql = "show sqlsession do sth";
    private String iplocation = "show iplocation do sth";
    private String pseudoSmsTotal = "show pseudoSmsTotal";
    private String listpseudoSmsTotalProvince ="show pseudoSmsTotalProvince";
    private String listpseudoSmsTotalCity = "show pseudoSmsTotalCity";
    private String intprovinceNum = "show provinceNum";
    private String intwebSocketMaxNum = "show int webSocketMaxNum";
    private String booleanlogSwitch = "show boolean logSwitch";
    private String booleansendInitSwitch ="show boolean sendInitSwitch";

    public SpamSmsWebSocketServer(String  configuration, String sqlSessionFactory, String ipLocation, int port)throws UnknownHostException{
        super(port);
        System.err.println("init config");
    }

    public void doStart() throws Exception {
        System.out.println("do sql sth");
        super.start();
    }
    public void doStop() throws Exception{
        super.stop();
    }

    public String getListpseudoSmsTotalCity() {
        return listpseudoSmsTotalCity;
    }

    public String getListpseudoSmsTotalProvince() {
        return listpseudoSmsTotalProvince;
    }

    public String getPseudoSmsTotal() {
        return pseudoSmsTotal;
    }
    public synchronized void incr(String province , String city){
        System.out.println("do the incr");
    }

}
