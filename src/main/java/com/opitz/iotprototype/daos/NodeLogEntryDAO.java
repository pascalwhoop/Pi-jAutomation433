package com.opitz.iotprototype.daos;

import com.opitz.iotprototype.entities.NetworkNode;
import com.opitz.iotprototype.entities.NodeLogEntry;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 26.03.14
 * Time: 16:05
 */
public interface NodeLogEntryDAO {

    public Serializable save(NodeLogEntry nodeLogEntry);

    public List<NodeLogEntry> findByNetworkNode (NetworkNode networkNode);

    public NodeLogEntry load(Serializable id);

    public List<NodeLogEntry> listAll();

}
