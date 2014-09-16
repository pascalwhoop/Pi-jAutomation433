package com.opitz.iotprototype.services;

import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.messages.DeviceGroupJSONMessage;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal Date: 04.04.14 Time: 15:01
 */

public interface DeviceGroupService {

	public Serializable save(DeviceGroup deviceGroup);

    public Serializable saveWithJSONMessage(DeviceGroupJSONMessage deviceGroupJSONMessage);

	public void update(DeviceGroup devicegroup);

    public void updateWithJSONMessage(DeviceGroupJSONMessage deviceGroupJSONMessage);

	public void delete(Serializable id);

	public DeviceGroup findByLabel(String label);

    public DeviceGroupJSONMessage getDeviceGroupJSONMessageByID(Integer id);

	public List<DeviceGroup> listAll();

    public List<DeviceGroupJSONMessage> listAllJSON();

	public DeviceGroup findById(Serializable id);

	public void addUser(Integer groupId, String username);

	public void addPlug(Integer groupId, Integer plugId);
}
