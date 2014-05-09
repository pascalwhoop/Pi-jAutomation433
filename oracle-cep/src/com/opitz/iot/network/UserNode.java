package com.opitz.iot.network;

public class UserNode {
	private String username;
	private String macAddress;
	
	public UserNode(){
		
	}
	public UserNode(String username, String macAddress){
		this.username = username;
		this.macAddress = macAddress;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	
}
