package com.opitz.iotprototype.entities;

import javax.persistence.*;

/**
 * User: Pascal
 * Date: 27.03.14
 * Time: 10:06
 *
 * ORM class representation of our users.
 */

@Entity
public class User {

    private Integer id;
    private String username;
    private NetworkNode personalDevice;
    /*private String passwordHash;*/


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
/*
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }*/
}
