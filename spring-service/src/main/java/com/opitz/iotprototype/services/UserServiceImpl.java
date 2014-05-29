package com.opitz.iotprototype.services;

import com.opitz.iotprototype.daos.DeviceGroupDAO;
import com.opitz.iotprototype.daos.UserDAO;
import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.entities.User;
import com.opitz.iotprototype.entities.UserState;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * User: Pascal Date: 27.03.14 Time: 10:56
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;

    @Autowired
    DeviceGroupDAO deviceGroupDAO;

    @Autowired
    SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Serializable save(User user) {
        if(user.getState() == null){
            user.setState(UserState.OFFLINE);
        }
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
        //we need to "detach" the user from all of his things first (e.g. plugGroups)
        List<DeviceGroup> groups = deviceGroupDAO.listAll();
        for(DeviceGroup group : groups){                        //iterate over the deviceGroups
            if(group.getUsersWithAccess().contains(user)){      //iterate over the users with access to this group
                group.getUsersWithAccess().remove(user);        //remove the user that is to be deleted

                if(group.getUsersWithAccess().size() == 0){     //if he was the last user with access to the group also delete the group
                    deviceGroupDAO.delete(group.getId());
                }
                else{
                    deviceGroupDAO.save(group);                 //else still save it so it doesnt contain the user anymore
                }
            }
        }
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
		userDAO.delete(user);
	}

	@Transactional
	@Override
	public User load(String username) {
		return userDAO.load(username);
	}

    @Transactional
    @Override
    public User load(Integer id){
        return userDAO.load(id);
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
