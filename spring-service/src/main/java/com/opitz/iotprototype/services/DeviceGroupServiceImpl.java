package com.opitz.iotprototype.services;

import com.opitz.iotprototype.daos.DeviceGroupDAO;
import com.opitz.iotprototype.daos.ElroPowerPlugDAO;
import com.opitz.iotprototype.daos.UserDAO;
import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

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
	public Serializable save(DeviceGroup devicegroup) {
		return deviceGroupDAO.save(devicegroup);
	}

	@Transactional
	@Override
	public void update(DeviceGroup devicegroup) {
		deviceGroupDAO.update(devicegroup);
	}

	@Transactional
	@Override
	public void delete(Serializable id) {
		deviceGroupDAO.delete(id);
	}

	@Transactional
	@Override
	public List<DeviceGroup> findByLabel(String label) {
		return deviceGroupDAO.findByLabel(label);
	}

	@Transactional
	@Override
	public List<DeviceGroup> listAll() {
		return deviceGroupDAO.listAll();
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

}
