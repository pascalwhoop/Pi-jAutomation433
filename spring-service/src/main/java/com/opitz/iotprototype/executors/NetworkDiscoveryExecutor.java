package com.opitz.iotprototype.executors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * User: Pascal
 * Date: 25.03.14
 * Time: 10:20
 *
 * This class handles the periodic network scan via ping and arp cache lookup. it uses the NetworkNodeService but this one is supposed to be running constantly.
 */

@Component
public class NetworkDiscoveryExecutor {


    @Async
    public void pingAllInSubnet() {

        // create a pool of threads, 20 max jobs will execute in parallel
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        // submit jobs to be executing by the pool
        for (int i = 1;i<255;i++) {

            //final String execStatement = "ping -c 2 -i 0.1 " + getOwnSubnet() + "." + i;
            final String ipToPing = getOwnSubnet() + "." + i;
            threadPool.submit(new Runnable() {
                public void run() {
                    /*Runtime rt = Runtime.getRuntime();
                    Process pr = null;
                    try {
                        System.out.println("exec: " + execStatement + "with thread ID: " + Thread.currentThread().getId());
                        pr = rt.exec(execStatement);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                   try {
                       // System.out.println(ipToPing + " is " + InetAddress.getByName(ipToPing).isReachable(100));
                       InetAddress.getByName(ipToPing).isReachable(100);
                   }catch (Exception e){}

                }
            });

        }

        // once you've submitted your last job to the service it should be shut down
        threadPool.shutdown();
        try {
            //threadpool not yet finished? Force..
            if(!threadPool.awaitTermination(5, TimeUnit.SECONDS)){
                List<Runnable> remaining = threadPool.shutdownNow();

                //to see how many are left
                for(Runnable left : remaining){
                    System.out.println("There was one left behind: " + left.toString());

                }

            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private String getOwnSubnet() {
        String subnet;
        String ownIp = null;
        try {
            ownIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String[] ipParts = ownIp.split("\\.");
        subnet = ipParts[0] + "." + ipParts[1] + "." + ipParts[2]; //subnet aus eigener IP schließen
        return subnet;

    }

    /**
     * currently not working way of getting all nodes via ARP technique. this is layer two and would be more reliable but it is not yet working
     * TODO fix Arping
     *
     * @return
     * @throws IOException
     */
   /* public Map<MacAddress, InetAddress> getAllDevicesWithARPing() throws IOException {
        HashSet<NetworkInterface> interfaces = getGoodNetworkInterfaces();
        Map<MacAddress, InetAddress> nodes = new HashMap<>();
        //iterate over network interfaces
        for (NetworkInterface ni : interfaces) {
            System.out.print("getting mac for 192.168.1.100");
            MacAddress address = Arping.query(InetAddress.getByName("192.168.1.100"), 2000);
            System.out.print(address.toString());
            *//*System.out.println("getting nodes for interface: " + ni.getName());
            Map<InetAddress, MacAddress> newNodes = Arping.scan(ni.getName(), getSubnetInetAddressColl(), 10000);
            System.out.println("Nodes found: \n " + newNodes.toString());
            //iterate over found nodes
            for(Map.Entry<InetAddress, MacAddress> entry : newNodes.entrySet()){
                //if a node is found on two or more interfaces, well so be it.
                //they must be connected then. Still we found our device we're looking for
                nodes.put(entry.getValue(), entry.getKey());
            }  *//*
        }
        System.out.print(nodes.toString());
        return nodes;
    }*/


    /*static {
        try {
            System.setProperty("java.library.path", "/usr/local/lib");
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("+++++++++++" + System.getProperty("java.library.path"));
        try{
            System.loadLibrary("kpcap");
            for (PcapDeviceMetadata metadata : PcapDeviceManager.getDeviceMetadataList()) {
                System.out.println(metadata);
            }
        }catch (UnsatisfiedLinkError e){
            e.printStackTrace();
        }
        System.out.println("+++++++++++" + "loading library kpcap");


    }*/


    /*private Collection<InetAddress> getSubnetInetAddressColl() throws UnknownHostException {
        Set<InetAddress> addresses = new HashSet<InetAddress>();
        for (int i = 1; i < 255; i++) {
            addresses.add(InetAddress.getByName(this.getOwnSubnet() + "." + i));
        }
        return addresses;
    }*/

    /*private HashSet<NetworkInterface> getGoodNetworkInterfaces() {

        HashSet<NetworkInterface> returnInterfaces = new HashSet<>();

        HashSet<NetworkInterface> interfaces = getOwnInterfaces();

        for (NetworkInterface ni : interfaces) {
            if (ni.getName().contains("eth") || ni.getName().contains("wlan")) {
                if (ni.getInetAddresses().hasMoreElements()) {
                    returnInterfaces.add(ni);
                }
            }

        }

        return returnInterfaces;
    }*/


    /**
     * Returns either the interfaces or an empty set if an error occured
     *
     * @return
     */
    /*private HashSet<NetworkInterface> getOwnInterfaces() {
        try {
            return new HashSet<>(Collections.list(NetworkInterface.getNetworkInterfaces()));
        } catch (SocketException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }*/


}
