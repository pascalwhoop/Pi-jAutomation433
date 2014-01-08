package com.opitz.devices.services;

import com.opitz.devices.daos.ElroPowerPlugDAO;
import com.opitz.devices.entities.ElroPowerPlug;
import com.opitz.devices.utils.DataNotFoundException;
import com.opitz.jni.NativeRCSwitchAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 07.01.14
 * Time: 15:45
 */

@Service
public class ElroPowerPlugServiceImpl implements ElroPowerPlugService {

    @Autowired
    private ElroPowerPlugDAO elroPowerPlugDAO;


    @Override
    @Transactional
    public ElroPowerPlug setState(ElroPowerPlug elroPowerPlug, boolean state) {

        NativeRCSwitchAdapter jniAdapter= NativeRCSwitchAdapter.getInstance();

        if(state){
            jniAdapter.switchOn(elroPowerPlug.getGroupID(), elroPowerPlug.getSwitchID());
        }
        else if (!state){
            jniAdapter.switchOff(elroPowerPlug.getGroupID(), elroPowerPlug.getSwitchID());
        }



        elroPowerPlug.setLastKnownState(state);
        save(elroPowerPlug);    //saves the current last known state of the plug, of course only if the signal went through
        return elroPowerPlugDAO.load(elroPowerPlug.getId());

    }

    @Override
    @Transactional
    public Serializable save(ElroPowerPlug elroPowerPlug) {
        if(elroPowerPlug.getId() == null){
            return elroPowerPlugDAO.save(elroPowerPlug);
        }
        elroPowerPlugDAO.update(elroPowerPlug);
        return elroPowerPlug.getId();
    }

    @Transactional
    @Override
    public void delete(Integer id) throws DataNotFoundException {
        elroPowerPlugDAO.delete(elroPowerPlugDAO.load(id));
    }

    @Transactional
    @Override
    public List<ElroPowerPlug> findByLabel(String label) {
        return elroPowerPlugDAO.findByLabel(label);
    }

    @Transactional
    @Override
    public List<ElroPowerPlug> listAll() {
        return elroPowerPlugDAO.listAll();
    }
}
