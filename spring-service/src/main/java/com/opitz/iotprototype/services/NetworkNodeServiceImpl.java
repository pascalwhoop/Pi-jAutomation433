package com.opitz.iotprototype.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opitz.iotprototype.daos.NetworkNodeDAO;
import com.opitz.iotprototype.daos.NodeLogEntryDAO;
import com.opitz.iotprototype.entities.NetworkNode;
import com.opitz.iotprototype.entities.NodeLogEntry;

/**
 * User: Pascal
 * Date: 04.03.14
 * Time: 21:39
 */

/**
 * This class provides services that tell you which devices are currently found
 * on the network. It can or is supposed to be able to perform both ping based
 * finding as well as ARP based finding of nodes. However TODO ARP is not yet
 * working
 */
@Service
public class NetworkNodeServiceImpl implements NetworkNodeService {

	@Autowired
	NetworkNodeDAO networkNodeDAO;
	@Autowired
	NodeLogEntryDAO nodeLogEntryDAO;

	/**
	 * gets all devices from the current cache + all that are stored in the DB.
	 * 
	 * @return
	 */

	@Transactional
	@Override
	public HashMap<String, NetworkNode> getAllStoredDevices() {
		HashMap<String, NetworkNode> nodes = new HashMap<>();

		for (NetworkNode node : networkNodeDAO.listAll()) {
			nodes.put(node.getMacAddress(), node);
		}

		return nodes;
	}

	@Override
	@Transactional
	public void storeAnyNewDevices(List<NetworkNode> networkNodes) {

		for (NetworkNode node : networkNodes) {
			networkNodeDAO.saveOrUpdate(node);
			nodeLogEntryDAO.save(new NodeLogEntry(node, node.getLastSeen()));
		}
	}

}
