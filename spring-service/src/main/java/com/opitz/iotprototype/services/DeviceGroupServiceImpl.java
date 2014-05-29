package com.opitz.iotprototype.services;

import com.opitz.iotprototype.daos.DeviceGroupDAO;
import com.opitz.iotprototype.daos.ElroPowerPlugDAO;
import com.opitz.iotprototype.daos.UserDAO;
import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.entities.User;
import com.opitz.iotprototype.messages.DeviceGroupJSONMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * User: Pascal Date: 04.04.14 Time: 15:02
 */

@Service
public class DeviceGroupServiceImpl implements DeviceGroupService {

	@Autowired
	DeviceGroupDAO deviceGroupDAO;

	@Autowired
	UserDAO userDAO;


	@Autowired
	ElroPowerPlugDAO elroPowerPlugDAO;



	@Transactional
	@Override
	public Serializable save(DeviceGroup deviceGroup) {
        return deviceGroupDAO.save(deviceGroup);
	}

    /**
     * Convenience Method to save the group via a json message that only keeps the necessary data.
     * @param deviceGroupJSONMessage
     * @return
     */
    @Override
    @Transactional
    public Serializable saveWithJSONMessage(DeviceGroupJSONMessage deviceGroupJSONMessage) {
        DeviceGroup newDeviceGroup = getDeviceGroup(deviceGroupJSONMessage);
        return save(newDeviceGroup);
    }

    @Transactional
	@Override
	public void update(DeviceGroup devicegroup) {
		deviceGroupDAO.update(devicegroup);
	}

    @Override
    public void updateWithJSONMessage(DeviceGroupJSONMessage deviceGroupJSONMessage){
        deviceGroupDAO.update(getDeviceGroup(deviceGroupJSONMessage));
    }

	@Transactional
	@Override
	public void delete(Serializable id) {
		deviceGroupDAO.delete(id);
	}

	@Transactional
	@Override
	public DeviceGroup findByLabel(String label) {
		return deviceGroupDAO.findByLabel(label);
	}

    /**
     * Utility function that returns a JSON optimized Message of the group not all containing all objects and their data
     * @param label
     * @return
     */

    @Override
    @Transactional
    public DeviceGroupJSONMessage getDeviceGroupForJSONByLabel(String label){
        DeviceGroup group = findByLabel(label);
        return getDeviceGroupJSONMessage(group);

    }

    @Transactional
	@Override
	public List<DeviceGroup> listAll() {
		return deviceGroupDAO.listAll();
	}

    @Transactional
    @Override
    public List<DeviceGroupJSONMessage> listAllJSON(){
        List<DeviceGroupJSONMessage> returnList = new ArrayList<>();
        for(DeviceGroup deviceGroup : listAll()){
            returnList.add(getDeviceGroupJSONMessage(deviceGroup));
        }
        return returnList;
    }

	@Transactional
	@Override
	public DeviceGroup findById(Serializable id) {
		return deviceGroupDAO.load(id);
	}

	@Transactional
	@Override
	public void addUser(Integer groupId, String username) {
		DeviceGroup deviceGroup = deviceGroupDAO.load(groupId);
		User user = userDAO.load(username);
		deviceGroup.getUsersWithAccess().add(user);
		deviceGroupDAO.update(deviceGroup);
	}

	@Transactional
	@Override
	public void addPlug(Integer groupId, Integer plugId) {
		DeviceGroup deviceGroup = deviceGroupDAO.load(groupId);
		ElroPowerPlug plug = elroPowerPlugDAO.load(plugId);
		deviceGroup.getElroPowerPlugs().add(plug);
		deviceGroupDAO.update(deviceGroup);
	}

    private DeviceGroup getDeviceGroup(DeviceGroupJSONMessage deviceGroupJSONMessage) {


        //build new deviceGroup Object
        DeviceGroup newDeviceGroup = new DeviceGroup();
        newDeviceGroup.setLabel(deviceGroupJSONMessage.getLabel());
        newDeviceGroup.setId(deviceGroupJSONMessage.getId());

        //add all requested users to group
        Set<User> usersWithAccess = newDeviceGroup.getUsersWithAccess();
        for(String username : deviceGroupJSONMessage.getUsers().values()){
            usersWithAccess.add(userDAO.load(username));
        }
        newDeviceGroup.setUsersWithAccess(usersWithAccess);

        //add all requested plugs to group
        Set<ElroPowerPlug> plugs = newDeviceGroup.getElroPowerPlugs();
        for(Integer plugId : deviceGroupJSONMessage.getPlugs().keySet()){
            plugs.add(elroPowerPlugDAO.load(plugId));
        }
        newDeviceGroup.setElroPowerPlugs(plugs);
        return newDeviceGroup;
    }

    private DeviceGroupJSONMessage getDeviceGroupJSONMessage(DeviceGroup group) {
        DeviceGroupJSONMessage jsonMessage = new DeviceGroupJSONMessage();

        //label setting
        jsonMessage.setLabel(group.getLabel());
        jsonMessage.setId((group.getId()));

        //convert ElroPowerPlugs set to id:label hashmap
        HashMap<Integer, String> plugs = jsonMessage.getPlugs();
        for(ElroPowerPlug plug : group.getElroPowerPlugs()){
            plugs.put(plug.getId(), plug.getLabel());
        }
        jsonMessage.setPlugs(plugs);

        //convert users set to id:label hashmap
        HashMap<Integer, String> users = jsonMessage.getUsers();
        for(User user : group.getUsersWithAccess()){
            users.put(user.getId(), user.getUsername());
        }
        jsonMessage.setUsers(users);

        return jsonMessage;
    }

}
