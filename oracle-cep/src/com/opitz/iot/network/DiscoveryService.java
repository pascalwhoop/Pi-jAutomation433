package com.opitz.iot.network;

import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * User: Pascal
 * Date: 04.03.14
 * Time: 21:39
 */


/**
 * This class handles the periodic network scan via ping and arp cache lookup. It provides services that tell you which
 * devices are currently found on the network. It can or is supposed to be able to perform both ping based finding as
 * well as ARP based finding of nodes. However TODO ARP is not yet working
 */
public class DiscoveryService {


    private final ArrayList<InterfaceAddress> interfaceAddresses;
    private final int PING_TIMEOUT;

    public DiscoveryService(int timeout) {
        this.PING_TIMEOUT = timeout;
        this.interfaceAddresses = getOwnInterfaceAddresses();
    }

    public void pingAllAddressesInRange() {
        for (InterfaceAddress ia : interfaceAddresses) {

            //only certain types of networks
            /*if (ia.getNetworkPrefixLength() == 16) {
                pingAllIn16(ia.getAddress().getHostAddress());
            }*/

            //TODO for now only /24 and treating /16 as such but maybe more later  (performance issues)
            if (ia.getNetworkPrefixLength() == 24 || ia.getNetworkPrefixLength() == 16) {
                pingAllIn24(ia.getAddress().getHostAddress());
            }
        }
    }

    private void pingAllIn16(String hostAddress) {
        long start = System.currentTimeMillis();
        String[] ipParts = hostAddress.split("\\.");

        // create a pool of threads, 20 max jobs will execute in parallel
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        // submit jobs to be executing by the pool
        for (int i = 1; i <= 255; i++) {
            for (int j = 1; j <= 255; j++) {
                //build IP to ping
                StringBuffer ipToPing = new StringBuffer();
                ipToPing.append(ipParts[0]).append(".").append(ipParts[1]).append(".").append(i).append(".").append(j);
                threadPool.submit(new DiscoveryRunnable(ipToPing.toString(), PING_TIMEOUT));
            }
        }
        // once you've submitted your last job to the service it should be shut down
        threadPool.shutdown();
        //see if our threads are all done
        try {
            if (!threadPool.awaitTermination(500, TimeUnit.SECONDS)) threadPool.shutdownNow();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Pinging in /16 network took " + (System.currentTimeMillis() - start) + "ms -- own IP" + hostAddress);


    }

    private void pingAllIn24(String hostAddress) {
        long start = System.currentTimeMillis();
        String[] ipParts = hostAddress.split("\\.");
        // create a pool of threads, 20 max jobs will execute in parallel
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        // submit jobs to be executing by the pool
        for (int i = 1; i <= 255; i++) {
            //build IP to ping
            final StringBuffer ipToPing = new StringBuffer();
            ipToPing.append(ipParts[0]).append(".").append(ipParts[1]).append(".").append(ipParts[2]).append(".").append(i);
            threadPool.submit(new DiscoveryRunnable(ipToPing.toString(), PING_TIMEOUT));
        }
        // once you've submitted your last job to the service it should be shut down
        threadPool.shutdown();
        //see if our threads are all done
        try {
            if (!threadPool.awaitTermination(500, TimeUnit.SECONDS)) threadPool.shutdownNow();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Pinging in /24 network took " + (System.currentTimeMillis() - start) + "ms -- own IP" + hostAddress);
    }

    /**
     * retrieves all nodes from arp cache and puts them in a usable format. usually its just a long string but we need a
     * Map or something to use in our code.
     *
     * @return
     */
    public HashMap<String, NetworkNode> getAllDevicesFromArpCache() {
        HashMap<String, NetworkNode> nodes = new HashMap<String, NetworkNode>();
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

    protected Set<String> getArpTable() {

        Runtime rt = Runtime.getRuntime();
        Process pr = null;

        //a regex pattern that lets us search the arp output for mac addresses
        Pattern macPattern = Pattern.compile("([0-9A-Fa-f]{1,2}[:-]){5}([0-9A-Fa-f]{1,2})");
        try {
            pr = rt.exec("arp -an");
            String arp = InputStreamHelper.getStringFromInputStream(pr.getInputStream());
            HashSet<String> nodes = new HashSet<String>(Arrays.asList(arp.split("\n")));
            Iterator<String> it = nodes.iterator();
            while (it.hasNext()) {
                String node = it.next();

                if (!macPattern.matcher(node).find()) {
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


    protected ArrayList<InterfaceAddress> getOwnInterfaceAddresses() {
        ArrayList<InterfaceAddress> ownAddresses = new ArrayList<InterfaceAddress>();

        try {
            HashSet<NetworkInterface> ni = getGoodNetworkInterfaces();
            for (NetworkInterface nInter : ni) {
                for (InterfaceAddress address : nInter.getInterfaceAddresses()) {
                    ownAddresses.add(address);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ownAddresses;
    }


    /**
     * currently not working way of getting all nodes via ARP technique. this is layer two and would be more reliable
     * but it is not yet working TODO fix Arping
     *
     * @return
     * @throws SocketException
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
    protected HashSet<NetworkInterface> getGoodNetworkInterfaces() throws SocketException {

        HashSet<NetworkInterface> returnInterfaces = new HashSet<NetworkInterface>();

        HashSet<NetworkInterface> interfaces = getOwnInterfaces();

        for (NetworkInterface ni : interfaces) {
            if (ni.isUp() && !ni.isLoopback() && !ni.isVirtual()) {
                if (ni.getInetAddresses().hasMoreElements()) {
                    returnInterfaces.add(ni);
                }
            }

        }

        return returnInterfaces;
    }


    /**
     * Returns either the interfaces or an empty set if an error occured
     *
     * @return
     */
    protected HashSet<NetworkInterface> getOwnInterfaces() {
        try {
            return new HashSet<NetworkInterface>(Collections.list(NetworkInterface.getNetworkInterfaces()));
        } catch (SocketException e) {
            e.printStackTrace();
            return new HashSet<NetworkInterface>();
        }
    }


}
