package com.opitz.iotprototype.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * User: Pascal Date: 04.04.14 Time: 13:08
 */

@Entity
public class DeviceGroup implements Serializable {

	Integer id;
	String label;

	// initialize to avoid null values
	Set<User> usersWithAccess = new HashSet<>();
	Set<ElroPowerPlug> elroPowerPlugs = new HashSet<>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic(optional = false)
	@Column(unique = true)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	//cause by using hbm save/update in dao	
	@Cascade(value = CascadeType.SAVE_UPDATE)
	public Set<User> getUsersWithAccess() {
		return usersWithAccess;
	}

	public void setUsersWithAccess(Set<User> usersWithAccess) {
		this.usersWithAccess = usersWithAccess;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	// cause by using hbm save/update in dao	
	@Cascade(value = CascadeType.SAVE_UPDATE)
	public Set<ElroPowerPlug> getElroPowerPlugs() {
		return elroPowerPlugs;
	}

	public void setElroPowerPlugs(Set<ElroPowerPlug> elroPowerPlugs) {
		this.elroPowerPlugs = elroPowerPlugs;
	}
}
