package com.opitz.iotprototype.entities;

import javax.persistence.*;

/**
 * A ELRO Power Plug has 2 times 5 DIP switches which can either be set to 0 or
 * 1. The first 5 DIP's are a "system" code and the second 5 are a "unit" code.
 * However it doesn't matter how you encode it, all that matters is that you
 * don't have more than 1 plug with the same code (unless you explicitly intend
 * to do so)
 * 
 * One could use the System codes to represent separate rooms but this can also
 * be abstracted in the middleware.
 */

@Entity
@PrimaryKeyJoinColumn(name="DEVICE_ID")
public class ElroPowerPlug extends Device{


	private String switchID; // 5 bit binary code for a group
	private String groupID; // 5 bit binary code for a certain plug

	// Getter Setter


	@Basic(optional = false)
	public String getSwitchID() {
		return switchID;
	}

	public void setSwitchID(String switchID) {
		this.switchID = switchID;
	}

	@Basic(optional = false)
	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
}
