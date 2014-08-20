package com.opitz.iotprototype.daos;

import com.opitz.iotprototype.entities.NetworkNode;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 25.03.14
 * Time: 19:16
 */
public interface NetworkNodeDAO {

    public Serializable save(NetworkNode networkNode);

    public void update(NetworkNode networkNode);

    public void delete(NetworkNode networkNode);

    public void delete(Serializable id);

    public List<NetworkNode> findByDnsName(String label);

    public NetworkNode load(Serializable id);

    public List<NetworkNode> listAll();

    public void saveOrUpdate (NetworkNode networkNode);
}
