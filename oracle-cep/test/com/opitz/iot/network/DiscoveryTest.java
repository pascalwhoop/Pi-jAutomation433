package com.opitz.iot.network;

import java.util.Set;

/**
 * created by: OPITZ CONSULTING Deutschland GmbH
 *
 * @author Brokmeier, Pascal
 */
public class DiscoveryTest {

    public static void main(String[] args){



        try{
            DiscoveryService ds = new DiscoveryService(100);
            ds.pingAllAddressesInRange();
            Set<String> arpTable = ds.getArpTable();
            
            StringBuffer buff = new StringBuffer();
            for(String s : arpTable){
                buff.append(s).append("\n");
            }
            System.out.println(buff.toString());


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }




}
