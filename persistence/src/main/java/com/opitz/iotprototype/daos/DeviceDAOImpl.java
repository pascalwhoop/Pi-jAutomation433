package com.opitz.iotprototype.daos;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.opitz.iotprototype.entities.Device;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * User: Pascal
 * Date: 07.01.14
 * Time: 15:59
 */
@Repository
public class DeviceDAOImpl extends GenericDAOImpl<Device, Integer> implements DeviceDAO{

    @Autowired
    private SessionFactory sessionFactory;

}
