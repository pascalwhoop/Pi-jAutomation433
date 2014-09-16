package com.opitz.iotprototype.services;

import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.utils.DataNotFoundException;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 07.01.14
 * Time: 14:42
 */
public interface ElroPowerPlugService {

    public void setPhysicalState(ElroPowerPlug elroPowerPlug);

    public void setPhysicalStateAndSaveToDB(Integer id, boolean state);

    public Serializable save(ElroPowerPlug elroPowerPlug);

    public void delete(Integer id) throws DataNotFoundException;

    public List<ElroPowerPlug> findByLabel(String label);

    public ElroPowerPlug load(Serializable id);

    public List<ElroPowerPlug> listAll();
}
