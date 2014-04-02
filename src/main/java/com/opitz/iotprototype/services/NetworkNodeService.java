package com.opitz.iotprototype.services;

import com.opitz.iotprototype.entities.NetworkNode;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Pascal
 * Date: 04.03.14
 * Time: 21:39
 */
public interface NetworkNodeService {


    public HashMap<String, NetworkNode> getAllStoredDevices();

    public HashMap<String,NetworkNode> getAllDevicesFromArpCache();

    public void storeAnyNewDevices(Map<String, NetworkNode> networkNodes);

    public void cacheDevices(HashMap<String, NetworkNode> newNetworkNodes);

    public Map<String, NetworkNode> getCachedDevices();

    public void clearDeviceCache();

    public void performActiveDeviceLogging();



}
