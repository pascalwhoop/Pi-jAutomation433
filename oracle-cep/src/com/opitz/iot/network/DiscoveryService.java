package com.opitz.iot.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * User: Pascal
 * Date: 04.03.14
 * Time: 21:39
 */


/**
 * This class handles the periodic network scan via ping and arp cache lookup.
 * It provides services that tell you which devices are currently found on the network.
 * It can or is supposed to be able to perform both ping based finding as well as ARP based finding of nodes.
 * However TODO ARP is not yet working
 */
public class DiscoveryService {


    /**
     * A method for pinging all devices in a local network. check arp cache afterwards
     * @param responseTimeout
     */
    public void pingAllInSubnet(int responseTimeout) {

        // create a pool of threads, 20 max jobs will execute in parallel
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        // submit jobs to be executing by the pool
        for (int i = 1;i<255;i++) {
            //build IP to ping
            final String ipToPing = getOwnSubnet() + "." + i;

            threadPool.submit(new DiscoveryRunnable(ipToPing, responseTimeout));
        }

        // once you've submitted your last job to the service it should be shut down
        threadPool.shutdown();

        //see if our threads are all done
        try {
            if(!threadPool.awaitTermination(5, TimeUnit.SECONDS)){

            }
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void pingAllInSubnet(){
        this.pingAllInSubnet(150);
    }

    public HashMap<String, NetworkNode> getAllDevicesFromArpCache() {
        HashMap<String, NetworkNode> nodes = new HashMap<>();
        for (String line : getArpTable()) {
            String[] nodeInfo = line.split(" +"); //regex to split all spaces
            // string is of form: <dnsname> (<ip>) at <macAddress> on en0 ifscope [ethernet]
            String dnsName = nodeInfo[0];
            String ipAddress = nodeInfo[1].substring(1, nodeInfo[1].length() - 1);
            String macAddress = nodeInfo[3];
            NetworkNode node = new NetworkNode(macAddress, dnsName, ipAddress);

            nodes.put(node.getMacAddress(), node);

        }


        return nodes;
    }

    private Set<String> getArpTable() {

        Runtime rt = Runtime.getRuntime();
        Process pr = null;
        try {
            pr = rt.exec("arp -a");
            String arp = InputStreamHelper.getStringFromInputStream(pr.getInputStream());
            HashSet<String> nodes = new HashSet<>(Arrays.asList(arp.split("\n")));
            Iterator<String> it = nodes.iterator();
            while (it.hasNext()) {
                String node = it.next();

                if (node.contains("incomplete") || node.contains("ff:ff:ff:ff:ff:ff")) {
                    it.remove();
                }
            }


            //for(String node : nodes){System.out.println(node);}

            return nodes;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashSet<String>();
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
