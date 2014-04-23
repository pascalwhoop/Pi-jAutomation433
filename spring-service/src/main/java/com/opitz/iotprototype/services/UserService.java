package com.opitz.iotprototype.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.opitz.iotprototype.entities.User;

/**
 * User: Pascal Date: 27.03.14 Time: 10:55
 */

public interface UserService {

	public Serializable save(User user);

	public void update(User user);

	public void delete(User user);

	public User load(String username);

	public List<User> listAll();

	public HashMap<String, String> getDeviceMACUserMap();

}
