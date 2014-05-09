package com.opitz.iotprototype.daos;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opitz.iotprototype.entities.User;

/**
 * User: Pascal
 * Date: 27.03.14
 * Time: 10:10
 */
@Repository
public class UserDAOImpl implements UserDAO {


    @Autowired
    SessionFactory sessionFactory;


    @Override
    public Serializable save(User user) {
        Session session = sessionFactory.getCurrentSession();
        return session.save(user);
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    @Override
    public User load(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT u FROM User u WHERE (lower(u.username)  LIKE lower('%' || :searchString || '%')) ");
        query.setParameter("searchString", username);

        return (User)query.uniqueResult();
    }

    @Override
    public List<User> listAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(User.class).list();
    }
}
