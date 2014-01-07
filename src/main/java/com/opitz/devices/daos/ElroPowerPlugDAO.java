package com.opitz.devices.daos;

import com.opitz.devices.entities.ElroPowerPlug;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 07.01.14
 * Time: 15:46
 */
public interface ElroPowerPlugDAO {

    public Serializable save(ElroPowerPlug elroPowerPlug);

    public void update(ElroPowerPlug elroPowerPlug);

    public void delete(ElroPowerPlug elroPowerPlug);

    public void delete(Serializable id);

    public List<ElroPowerPlug> findByLabel(String label);

    public ElroPowerPlug load(Serializable id);

    public List<ElroPowerPlug> listAll();
}
