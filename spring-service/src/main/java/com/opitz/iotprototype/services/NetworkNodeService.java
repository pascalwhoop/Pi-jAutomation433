package com.opitz.iotprototype.services;

import java.util.HashMap;
import java.util.List;

import com.opitz.iotprototype.entities.NetworkNode;

/**
 * User: Pascal Date: 04.03.14 Time: 21:39
 */
public interface NetworkNodeService {

	public HashMap<String, NetworkNode> getAllStoredDevices();

	public void storeAnyNewDevices(List<NetworkNode> networkNodes);
}
