package com.opitz.iotprototype.daos;

import com.opitz.iotprototype.entities.User;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 27.03.14
 * Time: 10:10
 */
public interface UserDAO {

    public Serializable save(User user);

    public void update(User user);

    public void delete(User user);

    public User load(String username);

    public User load (Integer id);

    public List<User> listAll();
}
