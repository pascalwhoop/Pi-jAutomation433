package com.opitz.iotprototype.services;

import com.opitz.iotprototype.daos.DeviceDAO;
import com.opitz.iotprototype.entities.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * created by: OPITZ CONSULTING Deutschland GmbH
 *
 * @author Brokmeier, Pascal
 */

@Service
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    DeviceDAO deviceDAO;

    @Autowired
    ElroPowerPlugService elroPowerPlugService;

    @Override
    @Transactional
    public boolean save(Device device) {
        //TODO perform changes to device (HUE Bridge / ElroPowerPlug Stuff)

        //TODO return type dependent on success or not
        return deviceDAO.save(device);
    }

    @Override
    @Transactional
    public List<Device> findAll() {
        return deviceDAO.findAll();
    }

    @Override
    @Transactional
    public Device find(Integer id) {
        return deviceDAO.find(id);
    }

    @Override
    @Transactional
    public boolean remove(Device device) {
        return deviceDAO.remove(device);
    }
}
