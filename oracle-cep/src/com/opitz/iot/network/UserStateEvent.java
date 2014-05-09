package com.opitz.iot.network;

import java.util.Date;

public class UserStateEvent {

	private String username;
	private UserState userState;
	private final Date lastSeen;
	
	public UserStateEvent(){
		lastSeen = new Date();
	}
	
	public Date getTimestamp (){
		return lastSeen;
	}
	
	public UserState getUserState() {
		return userState;
	}
	public void setUserState(UserState userState) {
		this.userState = userState;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
