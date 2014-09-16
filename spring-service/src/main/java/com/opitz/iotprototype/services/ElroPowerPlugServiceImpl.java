package com.opitz.iotprototype.services;

import com.opitz.iotprototype.daos.ElroPowerPlugDAO;
import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.utils.DataNotFoundException;
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
    public void setPhysicalState(ElroPowerPlug elroPowerPlug) {

        NativeRCSwitchAdapter jniAdapter= NativeRCSwitchAdapter.getInstance();
        System.out.println("++++++++" + elroPowerPlug.isLastKnownState() + " setting to Switch: " + elroPowerPlug.getGroupID() + " " + elroPowerPlug.getSwitchID());

        if(NativeRCSwitchAdapter.isWorking()){  //if native code is working perform switching, else do nothing but return the current plug from DB
            if(elroPowerPlug.isLastKnownState()){
                jniAdapter.switchOn(elroPowerPlug.getGroupID(), elroPowerPlug.getSwitchID());
            }
            else{
                jniAdapter.switchOff(elroPowerPlug.getGroupID(), elroPowerPlug.getSwitchID());
            }
        }
    }

    @Transactional
    @Override
    public Serializable save(ElroPowerPlug elroPowerPlug) {
        setPhysicalState(elroPowerPlug);

        if(elroPowerPlug.getId() == null){
            return elroPowerPlugDAO.save(elroPowerPlug);
        }
        elroPowerPlugDAO.update(elroPowerPlug);
        return elroPowerPlug.getId();
    }

    @Override
    public void setPhysicalStateAndSaveToDB(Integer id, boolean state) {
        ElroPowerPlug plug = load(id);
        plug.setLastKnownState(state);
        save(plug);
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

    @Override
    @Transactional
    public ElroPowerPlug load(Serializable id) {
        return elroPowerPlugDAO.load(id);
    }

    @Transactional
    @Override
    public List<ElroPowerPlug> listAll() {
        return elroPowerPlugDAO.listAll();
    }
}
