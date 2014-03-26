package com.opitz.iotprototype.daos;

import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.entities.NetworkNode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 25.03.14
 * Time: 19:19
 */
@Repository
public class NetworkNodeDAOImpl implements NetworkNodeDAO{

    @Autowired
    SessionFactory sessionFactory;


    @Override
    public List<NetworkNode> listAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(NetworkNode.class).list();
    }

    @Override
    public NetworkNode load(Serializable id) {
        Session session = sessionFactory.getCurrentSession();
        return (NetworkNode) session.load(NetworkNode.class, id);
    }

    @Override
    public List<NetworkNode> findByDnsName(String searchString) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from NetworkNode nn where (lower(nn.dnsName)  LIKE lower('%' || :searchString || '%')) ");
        query.setParameter("searchString", searchString);

        //Alle Objekttypen holen
        return query.list();

    }

    @Override
    public void delete(Serializable id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(load(id));
    }

    @Override
    public void delete(NetworkNode networkNode) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(networkNode);
    }

    @Override
    public void update(NetworkNode networkNode) {
        Session session = sessionFactory.getCurrentSession();
        session.update(networkNode);
    }

    @Override
    public Serializable save(NetworkNode networkNode) {
        Session session = sessionFactory.getCurrentSession();
        return session.save(networkNode);
    }

    @Override
    public void saveOrUpdate(NetworkNode networkNode){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(networkNode);
    }
}
