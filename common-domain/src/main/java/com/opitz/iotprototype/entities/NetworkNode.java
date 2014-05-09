package com.opitz.iotprototype.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Pascal Date: 06.03.14 Time: 15:17
 */
@Entity
public class NetworkNode implements Serializable {


    /**
     * duplicate can be found in oracle-cep. Please make sure to update any new required fields added to this class in the oracle-cep module as well.
     */
	private String macAddress;
	private String dnsName;
	private String lastKnownIPAddress;
	private String userLabel;
	private Date lastSeen;

	public NetworkNode(String macAddress, String dnsName, String ipAddress) {
		this.macAddress = macAddress;
		this.dnsName = dnsName;
		this.lastKnownIPAddress = ipAddress;
		this.lastSeen = new Date();
	}

	public NetworkNode() {
	}

	@Override
	/**
	 * equals disregards the last seen timestamp but compares the rest. last seen is only updated every 30 minutes via the cron job.
	 */
	public boolean equals(Object o) {
		NetworkNode anotherNode;
		if (o == null) {
			return false;
		} else {
			anotherNode = (NetworkNode) o;
		}

		boolean mac = anotherNode.macAddress == this.macAddress;
		boolean ip = anotherNode.lastKnownIPAddress == this.lastKnownIPAddress;
		boolean dns = anotherNode.dnsName == this.dnsName;

		if (anotherNode.macAddress == this.macAddress
				&& anotherNode.dnsName == this.dnsName
				&& anotherNode.lastKnownIPAddress == this.lastKnownIPAddress) {
			return true;
		} else {
			return false;
		}
	}

	@Id
	public String getMacAddress() {
		return macAddress;
	}

	private void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	@Basic(optional = true)
	public String getDnsName() {
		return dnsName;
	}

	public void setDnsName(String dnsName) {
		this.dnsName = dnsName;
	}

	@Basic(optional = true)
	public String getLastKnownIPAddress() {
		return lastKnownIPAddress;
	}

	public void setLastKnownIPAddress(String lastKnownIPAddress) {
		this.lastKnownIPAddress = lastKnownIPAddress;
	}

	@Basic(optional = true)
	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
	}

	@Basic(optional = false)
	public Date getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(Date lastSeen) {
		this.lastSeen = lastSeen;
	}
}
