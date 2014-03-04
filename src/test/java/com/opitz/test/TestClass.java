package com.opitz.test;

import org.krakenapps.pcap.decoder.ethernet.MacAddress;
import org.krakenapps.pcap.util.Arping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: Pascal
 * Date: 02.03.14
 * Time: 12:58
 */
public class TestClass {

    public static void main (String[] args){
        TestClass testClass = new TestClass();

        try {
            InetAddress address = InetAddress.getByName("192.168.1.1");
            System.out.print(address.toString());

            //testClass.checkHosts(null);
            //testClass.runPingToBroadcast(testClass.getOwnSubnet());
            //testClass.runARP();
            testClass.tryARPing();
        }catch (Exception e){
            e.printStackTrace();
        };

    }

    private String getOwnSubnet(){
        String subnet;
        String ownIp = null;
        try {
            ownIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String[] ipParts = ownIp.split("\\.");
        subnet = ipParts[0]+ "." + ipParts[1] + "." + ipParts[2]; //subnet aus eigener IP schließen
        return subnet;

    }

    public void checkHosts(String subnet) throws UnknownHostException, IOException{
        String ownIp = InetAddress.getLocalHost().getHostAddress();
        String[] ipParts = ownIp.split("\\.");

        if(subnet == null){
            subnet = ipParts[0]+ "." + ipParts[1] + "." + ipParts[2]; //subnet aus eigener IP schließen
        }

        int timeout=100;

        int start = Integer.parseInt(ipParts[3]);
        start = start - (start % 100);

        for (int i=start;i<254;i++){
            String host=subnet + "." + i;
            InetAddress hostInQuestion = InetAddress.getByName(host);
            if (hostInQuestion.isReachable(timeout)){

                System.out.println(host + " aka " + hostInQuestion.getCanonicalHostName() + " is reachable");
            }
        }
    }

    public void runARP(){
        Runtime rt = Runtime.getRuntime();
        Process pr = null;
        try {
            pr = rt.exec("arp -a");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        InputStream is = pr.getInputStream();
        String returnedValue = getStringFromInputStream(is);
        System.out.print(returnedValue);



    }

    private void runPingToBroadcast(String subnet){
        Runtime rt = Runtime.getRuntime();
        Process pr = null;
        try {
            String pingCommand = "ping -b en0 " + "-c 1 -i 2 " + subnet + ".255";
            pr = rt.exec(pingCommand);
            InputStream is = pr.getInputStream();
            String out = getStringFromInputStream(is);
            is.close();
             System.out.println(out);
            System.out.println("\n");
        }catch (Exception e){}

    }

    private Collection<InetAddress> getSubnetInetAddressColl() throws UnknownHostException {
        Set<InetAddress> addresses = new HashSet<InetAddress>();
        for (int i = 1; i<255;i++){
            addresses.add(InetAddress.getByName(this.getOwnSubnet() + "." + i));
        }
        return addresses;
    }

    private void tryARPing() throws IOException {
        System.load("/Users/Pascal/.m2/repository/org/krakenapps/kraken-pcap/1.3.0/kraken-pcap-1.3.0.jar!/lib/linux_x86_64/libkpcap.so");
        Map<InetAddress, MacAddress> nodes = Arping.scan("en0", getSubnetInetAddressColl(), 2);
        System.out.print(nodes.toString());

    }

    private void setLibPath(){
        try {
            System.setProperty( "java.library.path", "" );
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
