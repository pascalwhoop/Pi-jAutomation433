package com.opitz.iotprototype.services;

import com.opitz.iotprototype.executors.NetworkDiscoveryExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * User: Pascal
 * Date: 25.03.14
 * Time: 11:14
 */

@Service
public class SchedulerService {

    @Autowired
    private NetworkDiscoveryExecutor nde;
    @Autowired
    private NetworkNodeService nns;


    @Scheduled(cron="5,20,35,50 * * * * ? ")
    public void doNetworkDiscovery(){
        System.out.println("### running cron discovery ###");
        nde.pingAllInSubnet();
    }

    @Scheduled(cron="10,25,40,55 * * * * ? ")
    public void storeNodesInCache(){
        System.out.println("### caching devices in Memory ###");
        nns.cacheDevices(nns.getAllDevices());
    }

    @Scheduled(cron="0 0/30 * * * ? ")
    public void doStateLog(){
        System.out.println("### logging device state ###");
        nns.performActiveDeviceLogging();
    }

    @Scheduled(cron="0 0/5 * * * ? ")
    public void storeNewDevicesToDatabase(){
        System.out.println("### storing devices to DB ###");
        nns.storeAnyNewDevices(nns.getCachedDevices());
        nns.clearDeviceCache();
    }


}
