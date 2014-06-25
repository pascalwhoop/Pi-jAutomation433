package com.opitz.iot.network;

public class UserNodeStateEvent {
	
        private String username;
	private boolean nodeFound;
	
	public UserNodeStateEvent(String username, boolean found){
		this.username = username;
		this.nodeFound = found;
	}
	
	public UserNodeStateEvent(){
		
	}
	
	public boolean isNodeFound() {
		return nodeFound;
	}
	public void setNodeFound(boolean nodeFound) {
		this.nodeFound = nodeFound;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
