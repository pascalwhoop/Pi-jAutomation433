package com.opitz.iotprototype.services;

import com.opitz.iotprototype.daos.UserDAO;
import com.opitz.iotprototype.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User: Pascal
 * Date: 27.03.14
 * Time: 10:56
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;

    @Transactional
    @Override
    public Serializable save(User user) {
        return userDAO.save(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Transactional
    @Override
    public User load(String username) {
        return userDAO.load(username);
    }

    @Transactional
    @Override
    public List<User> listAll() {
        return userDAO.listAll();
    }

    @Transactional
    @Override
    public String retrieveUserStatus(String username){
        User user = load(username);
        Date now = new Date();
        Date userLastSeen = user.getPersonalDevice().getLastSeen();
        //hard coded for now 15 minutes away == offline
        if(now.getTime() - userLastSeen.getTime() < (1000*60*15)){
            return "online";
        }
        else{
            return "offline";
        }
    }
}
