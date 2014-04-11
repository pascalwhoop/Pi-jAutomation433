package com.opitz.iotprototype.daos;

import com.opitz.iotprototype.entities.NetworkNode;
import com.opitz.iotprototype.entities.NodeLogEntry;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 26.03.14
 * Time: 16:05
 */
@Repository
public class NodeLogEntryDAOImpl implements NodeLogEntryDAO {


    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Serializable save(NodeLogEntry nodeLogEntry) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println("+++ saving nodelogEntry " + nodeLogEntry.getNode().getDnsName() + " +++");

        return session.save(nodeLogEntry);
    }

    @Override
    public List<NodeLogEntry> findByNetworkNode(NetworkNode networkNode) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from NodeLogEntry nle where (lower(nle.node.macAddress)  LIKE lower('%' || :searchString || '%')) ");
        query.setParameter("searchString", networkNode.getMacAddress());
        //Alle Objekttypen holen
        return query.list();
    }

    @Override
    public NodeLogEntry load(Serializable id) {
        Session session = sessionFactory.getCurrentSession();
        return (NodeLogEntry) session.load(NodeLogEntry.class, id);
    }

    @Override
    public List<NodeLogEntry> listAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(NodeLogEntry.class).list();
    }
}
