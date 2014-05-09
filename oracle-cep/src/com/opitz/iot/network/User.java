package com.opitz.iot.network;

import java.io.Serializable;

/**
 * User: Pascal Date: 27.03.14 Time: 10:06
 * 
 * ORM class representation of our users.
 */

public class User implements Serializable {

        private String username;
        private NetworkNode personalDevice;
        private UserState state;

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public NetworkNode getPersonalDevice() {
		return personalDevice;
	}

	public void setPersonalDevice(NetworkNode phone) {
		this.personalDevice = phone;
	}

    
    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    /*
	 * public String getPasswordHash() { return passwordHash; }
	 * 
	 * public void setPasswordHash(String passwordHash) { this.passwordHash =
	 * passwordHash; }
	 */
}
