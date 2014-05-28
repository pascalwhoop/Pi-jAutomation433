package com.opitz.iotprototype.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: Pascal Date: 27.03.14 Time: 10:06
 * 
 * ORM class representation of our users.
 *
 *
 */

@Entity
public class User implements Serializable {

    /**
     * duplicate can be found in oracle-cep. Please make sure to update any new required fields added to this class in the oracle-cep module as well.
     */
        private Integer id;
        private String username;
        private NetworkNode personalDevice;
        private UserState state;

	/* private String passwordHash; */

    public User(){
        //have user state default. Gets overridden if existent by hibernate.
        state = UserState.OFFLINE;
    }

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
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@OneToOne(fetch = FetchType.EAGER)
	public NetworkNode getPersonalDevice() {
		return personalDevice;
	}

	public void setPersonalDevice(NetworkNode phone) {
		this.personalDevice = phone;
	}

    @Basic(optional = true)
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
