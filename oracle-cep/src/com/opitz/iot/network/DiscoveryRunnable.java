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
    private Process nativeProcess;
    private Runtime rt = Runtime.getRuntime();

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
        //long start = System.currentTimeMillis();
        try {
            InetAddress.getByName(ip).isReachable(timeout);
          /*  prepAndExecRuntime();
            killNativeProcess();*/
        } catch (IOException e) {
            System.out.println("cant ping IP: " + ip);
        }

        //System.out.println("thread took " + (System.currentTimeMillis() - start));

    }

    public void prepAndExecRuntime() throws IOException{
        //the Runtime exec version
        final String execStatement = "ping -c 1 -i 0.008 " + ip;
        nativeProcess = rt.exec(execStatement);
    }

    public void killNativeProcess(){
        nativeProcess.destroy();
    }
}
