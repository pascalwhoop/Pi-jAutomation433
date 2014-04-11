package com.opitz.iotprototype.services;

import com.opitz.iotprototype.daos.DeviceGroupDAO;
import com.opitz.iotprototype.entities.DeviceGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 04.04.14
 * Time: 15:02
 */

@Service
public class DeviceGroupServiceImpl implements DeviceGroupService {

    @Autowired
    DeviceGroupDAO deviceGroupDAO;


    @Override
    public Serializable save(DeviceGroup devicegroup) throws Exception{
        if(devicegroup.getUsersWithAccess().size() == 0){
            throw new Exception("not enough users added");
        }

        return deviceGroupDAO.save(devicegroup);
    }

    @Override
    public void update(DeviceGroup devicegroup) throws Exception{
        if(devicegroup.getUsersWithAccess().size() == 0){
            throw new Exception("not enough users added");
        }

        deviceGroupDAO.update(devicegroup);
    }

    @Override
    public void delete(DeviceGroup devicegroup) {
        deviceGroupDAO.delete(devicegroup);
    }

    @Override
    public List<DeviceGroup> findByLabel(String label) {
        return deviceGroupDAO.findByLabel(label);
    }

    @Override
    public List<DeviceGroup> listAll() {
        return deviceGroupDAO.listAll();
    }
}
