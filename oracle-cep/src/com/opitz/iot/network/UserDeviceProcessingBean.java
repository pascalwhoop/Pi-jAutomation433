package com.opitz.iot.network;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.StreamSender;
import com.bea.wlevs.ede.api.StreamSink;
import com.bea.wlevs.ede.api.StreamSource;


/**
 * Bean that compares our found nodes with the list of user-macAddress mappings. For every user, it checks wether his device was found or not and passes an 
 * UserNodeStateEvent reflecting the results.
 * @author Brokmeier, Pascal (pbr)
 *
 */
public class UserDeviceProcessingBean implements StreamSink, StreamSource{

	//are both auto injected by the engine
	private Map<String, NetworkNode> nodesBuffer;
	private Map<String, UserMacPair> userMacPairs;
	private Date delay = null;
	private StreamSender eventSender;
	

	/**
	 * Method that is triggered for every found node. Since we dont want to run the compareNodesAndRaiseEvents method too often (double for loop)
	 * we just wait a second before doing our work on a small buffer. 
	 */
	public void onInsertEvent(Object event) throws EventRejectedException {
		
		NetworkNode node = (NetworkNode) event;
		nodesBuffer.put(node.getMacAddress(), node);
		if(delay == null){
			delay = new Date();
		}else if(System.currentTimeMillis() - 1000 > delay.getTime()){
			//one second passed. lets do the comparing
			compareNodesAndRaiseEvents();
			delay = null;
			nodesBuffer.clear();
		}

	}
	
	/**
	 * use all known users and all nodes just found and match them up. all users for which a node is found a true event is sent, all users whose nodes
	 * are not found, a false event is sent
	 */
	
	public void compareNodesAndRaiseEvents(){
		for(UserMacPair userMacPair : userMacPairs.values()){
			NetworkNode networkNode = nodesBuffer.get(userMacPair.getMacAddress());
			if(networkNode != null){
				eventSender.sendInsertEvent(new UserNodeStateEvent(userMacPair.getUsername(), true));
			}else{
				eventSender.sendInsertEvent(new UserNodeStateEvent(userMacPair.getUsername(), false));
			}
		}
	}

	public Map<String, NetworkNode> getNodesBuffer() {
		return nodesBuffer;
	}

	public void setNodesBuffer(Map<String, NetworkNode> nodesBuffer) {
		this.nodesBuffer = nodesBuffer;
	}

	public Map<String, UserMacPair> getUserMacPairs() {
		return userMacPairs;
	}

	public void setUserMacPairs(Map<String, UserMacPair> userMacPairs) {
		this.userMacPairs = userMacPairs;
	}
	
	@Override
	public void setEventSender(StreamSender sender) {
		eventSender = sender;
		
	}


}
