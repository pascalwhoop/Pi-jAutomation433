package com.opitz.devices.services;

import com.opitz.devices.entities.ElroPowerPlug;
import com.opitz.devices.utils.DataNotFoundException;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 07.01.14
 * Time: 14:42
 */
public interface ElroPowerPlugService {

    public Serializable save(ElroPowerPlug elroPowerPlug);

    public void delete(Integer id) throws DataNotFoundException;

    public List<ElroPowerPlug> findByLabel(String label);

    public List<ElroPowerPlug> listAll();
}
