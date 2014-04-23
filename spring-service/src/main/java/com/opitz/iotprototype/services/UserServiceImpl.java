package com.opitz.iotprototype.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opitz.iotprototype.daos.UserDAO;
import com.opitz.iotprototype.entities.User;

/**
 * User: Pascal Date: 27.03.14 Time: 10:56
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;
	@Autowired
	NetworkNodeService networkNodeService;

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
	public HashMap<String, String> getDeviceMACUserMap() {
		List<User> users = userDAO.listAll();
		HashMap<String, String> map = new HashMap<>();
		for (User user : users) {
			map.put(user.getPersonalDevice().getMacAddress(), user.getUsername());
		}
		return map;
	}

	@Transactional
	@Override
	public List<User> listAll() {
		return userDAO.listAll();
	}

}
