package com.opitz.iotprototype.daos;

import com.opitz.iotprototype.entities.ElroPowerPlug;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 07.01.14
 * Time: 15:59
 */
@Repository
public class ElroPowerPlugDAOImpl implements ElroPowerPlugDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Serializable save(ElroPowerPlug elroPowerPlug) {
        Session session = sessionFactory.getCurrentSession();
        Serializable id = session.save(elroPowerPlug);
        return id;
    }

    @Override
    public void update(ElroPowerPlug elroPowerPlug) {
        Session session = sessionFactory.getCurrentSession();
        session.update(elroPowerPlug);
    }

    @Override
    public void delete(ElroPowerPlug elroPowerPlug) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(elroPowerPlug);
    }

    @Override
    public void delete(Serializable id) {
        ElroPowerPlug elroPowerPlug = load(id);
        Session session = sessionFactory.getCurrentSession();

        session.delete(elroPowerPlug);
    }

    @Override
    public List<ElroPowerPlug> findByLabel(String label) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from ElroPowerPlug epp where (lower(epp.label)  LIKE lower('%' || :name || '%')) ");
        query.setParameter("name", label);
        //Alle Objekttypen holen
        List<ElroPowerPlug> elroPowerPlugs = query.list();
        return elroPowerPlugs;
    }

    @Override
    public ElroPowerPlug load(Serializable id){
        Session session = sessionFactory.getCurrentSession();
        ElroPowerPlug plug = (ElroPowerPlug) session.get(ElroPowerPlug.class, id);
        return plug;
    }


    @Override
    public List<ElroPowerPlug> listAll() {
        Session session = sessionFactory.getCurrentSession();
        List<ElroPowerPlug> elroPowerPlugs =  session.createCriteria(ElroPowerPlug.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();
        return elroPowerPlugs;
    }
}
