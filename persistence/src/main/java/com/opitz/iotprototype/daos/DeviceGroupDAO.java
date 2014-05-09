package com.opitz.iotprototype.daos;

import java.io.Serializable;
import java.util.List;

import com.opitz.iotprototype.entities.DeviceGroup;

/**
 * User: Pascal Date: 04.04.14 Time: 14:48
 */
public interface DeviceGroupDAO {

	public Serializable save(DeviceGroup devicegroup);

	public void update(DeviceGroup devicegroup);

	public void delete(Serializable id);

	public DeviceGroup findByLabel(String label);

	public DeviceGroup load(Serializable id);

	public List<DeviceGroup> listAll();
}
