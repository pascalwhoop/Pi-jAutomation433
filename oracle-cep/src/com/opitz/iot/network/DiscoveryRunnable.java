package com.opitz.iot.network;

import java.io.IOException;
import java.net.InetAddress;

/**
 * User: Pascal
 * Date: 09.04.14
 * Time: 10:47
 */
public class DiscoveryRunnable implements Runnable {

    private final String ip;
    private final int timeout;

    public DiscoveryRunnable(String ip, int timeout) {
        this.ip = ip;
        this.timeout = timeout;
    }

    /**
     * default timeout constructor with 150 ms ping timeout
     * @param ip
     */
    public DiscoveryRunnable(String ip) {
        this.ip = ip;
        this.timeout = 150;
    }

    public void run(){
        doPing();
    }

    /**
     * method that performs a ping for the set up ip in this runnable using a given timeout
     */
    public void doPing() {
        try {
            InetAddress.getByName(ip).isReachable(timeout);
            /*final String execStatement = "ping -c 2 -i 0.2 " + ip;

            Runtime rt = Runtime.getRuntime();
            Process pr = null;
            System.out.println("exec: " + execStatement + "with thread ID: " + Thread.currentThread().getId());
            pr = rt.exec(execStatement);*/

            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
