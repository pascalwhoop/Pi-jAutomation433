package com.opitz.iot.network;

import java.util.Date;

public class UserStateEvent {

	private String username;
	private UserState userState;
	private Date lastSeen;
	
	public UserStateEvent(){
		setLastSeen(new Date());
	}
	public UserStateEvent(String username, UserState state){
		this.username = username;
		this.userState = state;
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



	public Date getLastSeen() {
		return lastSeen;
	}



	public void setLastSeen(Date lastSeen) {
		this.lastSeen = lastSeen;
	}

	

}
