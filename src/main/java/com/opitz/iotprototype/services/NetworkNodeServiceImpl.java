package com.opitz.iotprototype.services;

import com.opitz.iotprototype.daos.NetworkNodeDAO;
import com.opitz.iotprototype.daos.NodeLogEntryDAO;
import com.opitz.iotprototype.entities.NetworkNode;
import com.opitz.iotprototype.entities.NodeLogEntry;
import com.opitz.iotprototype.utils.InputStreamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * User: Pascal
 * Date: 04.03.14
 * Time: 21:39
 */


/**
 * This class provides services that tell you which devices are currently found on the network.
 * It can or is supposed to be able to perform both ping based finding as well as ARP based finding of nodes.
 * However TODO ARP is not yet working
 */
@Service
public class NetworkNodeServiceImpl implements NetworkNodeService {


    @Autowired
    NetworkNodeDAO networkNodeDAO;
    @Autowired
    NodeLogEntryDAO nodeLogEntryDAO;

    private static HashMap<String, NetworkNode> nodeCache = new HashMap<>();

    /**
     * gets all devices from the current cache + all that are stored in the DB.
     * @return
     */
    @Override
    public HashMap<String, NetworkNode> getAllStoredDevices() {
        HashMap<String, NetworkNode> nodes = new HashMap<>();

        for (NetworkNode node : networkNodeDAO.listAll()){
            nodes.put(node.getMacAddress(), node);
        }

        nodes.putAll(nodeCache);
        return nodes;
    }

    @Override
    public HashMap<String, NetworkNode> getAllDevicesFromArpCache(){
        HashMap<String, NetworkNode> nodes = new HashMap<>();

        for(String line : getArpTable()){
            String[] nodeInfo = line.split(" +"); //regex to split all spaces
            // string is of form: <dnsname> (<ip>) at <macAddress> on en0 ifscope [ethernet]
            String dnsName = nodeInfo[0];
            String ipAddress = nodeInfo[1].substring(1, nodeInfo[1].length()-1);
            String macAddress = nodeInfo[3];
            NetworkNode node = new NetworkNode(macAddress, dnsName, ipAddress);

            nodes.put(node.getMacAddress(), node);

        }


        return nodes;
    }


    @Override
    @Transactional
    public void storeAnyNewDevices(Map<String, NetworkNode> networkNodes) {

        for (NetworkNode node : networkNodes.values()){
            networkNodeDAO.saveOrUpdate(node);
        }
    }

    @Override
    public void cacheDevices(HashMap<String, NetworkNode> newNetworkNodes){
        nodeCache.putAll(newNetworkNodes);
    }

    @Override
    public Map<String, NetworkNode> getCachedDevices() {
        return nodeCache;
    }

    @Override
    public void clearDeviceCache() {
        nodeCache.clear();
    }


    @Transactional
    @Override
    public void performActiveDeviceLogging() {
        List<NetworkNode> nodes = networkNodeDAO.listAll();
        Date justNow = new Date();
        justNow.setTime(justNow.getTime() - 1000* 60 * 2);

        for(NetworkNode node : nodes){

            //just now is -2min from right now. So has the node been seen in the last two minutes? then interpret as present
            if(node.getLastSeen().after(justNow)){
                nodeLogEntryDAO.save(new NodeLogEntry(node, node.getLastSeen()));
            }
        }
    }

    private Set<String> getArpTable(){

        Runtime rt = Runtime.getRuntime();
        Process pr = null;
        try {
            pr = rt.exec("arp -a");
            String arp = InputStreamHelper.getStringFromInputStream(pr.getInputStream());
            HashSet<String> nodes = new HashSet<>(Arrays.asList(arp.split("\n")));
            Iterator<String> it = nodes.iterator();
            while(it.hasNext()){
                String node = it.next();

                if(node.contains("incomplete") || node.contains("ff:ff:ff:ff:ff:ff")){
                    it.remove();
                }
            }


            //for(String node : nodes){System.out.println(node);}

            return nodes;
        }catch (IOException e){
            e.printStackTrace();
            return new HashSet<String>();
        }

    }

}
