package com.opitz.iotprototype.services;

import com.opitz.iotprototype.entities.DeviceGroup;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 04.04.14
 * Time: 15:01
 */


public interface DeviceGroupService {

    public Serializable save(DeviceGroup devicegroup) throws Exception;

    public void update(DeviceGroup devicegroup) throws Exception;

    public void delete(DeviceGroup devicegroup);

    public List<DeviceGroup> findByLabel(String label);

    public List<DeviceGroup> listAll();
}
