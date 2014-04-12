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

    public void doPing() {
        try {
            InetAddress.getByName(ip).isReachable(timeout);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
