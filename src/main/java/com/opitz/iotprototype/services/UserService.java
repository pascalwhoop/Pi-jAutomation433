package com.opitz.iotprototype.services;

import com.opitz.iotprototype.entities.NetworkNode;
import com.opitz.iotprototype.entities.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * User: Pascal
 * Date: 27.03.14
 * Time: 10:55
 */

public interface UserService {

    public Serializable save(User user);

    public void update(User user);

    public void delete(User user);

    public User load(String username);

    public List<User> listAll();

    public String retrieveUserStatusByUsername(String username);

    public HashMap<String, String> retrieveAllUserStatuses();

    public List<User> checkForUserNodes(HashMap<String, NetworkNode> nodes);
}
