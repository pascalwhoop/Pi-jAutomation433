package com.opitz.devices.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * User: Pascal
 * Date: 06.03.14
 * Time: 15:17
 */
@Entity
public class LocalNetworkDevice {

    private Integer id;
    private String macAddress;
    private String label;
    private String lastKnownIPAddress;
    private Date lastSeen;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic(optional=false)
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Basic(optional=true)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic(optional=true)
    public String getLastKnownIPAddress() {
        return lastKnownIPAddress;
    }

    public void setLastKnownIPAddress(String lastKnownIPAddress) {
        this.lastKnownIPAddress = lastKnownIPAddress;
    }

    @Basic(optional=false)
    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }
}

