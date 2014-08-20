package com.opitz.iotprototype.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Pascal Date: 04.04.14 Time: 13:08
 */

@Entity
public class DeviceGroup implements Serializable {

	Integer id;
	String label;

	// initialize to avoid null values
	Set<Device> devices = new HashSet<>();

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
	// cause by using hbm save/update in dao	
	@Cascade(value = CascadeType.SAVE_UPDATE)
	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}
}
