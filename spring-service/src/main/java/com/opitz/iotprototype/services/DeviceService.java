package com.opitz.iotprototype.services;

import com.opitz.iotprototype.entities.Device;

import java.util.List;

/**
 * created by: OPITZ CONSULTING Deutschland GmbH
 *
 * @author Brokmeier, Pascal
 */
public interface DeviceService {

    public boolean save(Device device);

    public Device find(Integer id);

    public List<Device> findAll();

    public boolean remove(Device device);
}
