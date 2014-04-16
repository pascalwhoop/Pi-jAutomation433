package com.opitz.iotprototype.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * User: Pascal Date: 04.04.14 Time: 13:08
 */

@Entity
public class DeviceGroup implements Serializable {

	Integer id;
	String label;

	Set<User> usersWithAccess;
	Set<ElroPowerPlug> elroPowerPlugs;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic(optional = false)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	public Set<User> getUsersWithAccess() {
		return usersWithAccess;
	}

	public void setUsersWithAccess(Set<User> usersWithAccess) {
		this.usersWithAccess = usersWithAccess;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	public Set<ElroPowerPlug> getElroPowerPlugs() {
		return elroPowerPlugs;
	}

	public void setElroPowerPlugs(Set<ElroPowerPlug> elroPowerPlugs) {
		this.elroPowerPlugs = elroPowerPlugs;
	}
}
