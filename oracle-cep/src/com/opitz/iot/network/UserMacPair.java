package com.opitz.iot.network;

public class UserMacPair {
	private String username;
	private String macAddress;
	
	public UserMacPair(){
		
	}
	public UserMacPair(String username, String macAddress){
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
