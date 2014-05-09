package com.opitz.iot.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bea.wlevs.ede.api.Adapter;
import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.RunnableBean;
import com.bea.wlevs.ede.api.StreamSink;

public class NetworkNodeOutputAdapter implements Adapter, StreamSink{

	private Map<String, NetworkNode> networkNodes;
	public String serverURL;
	//date flag that we use to send new nodes to server only every ten minutes
	private static Date lastPushToServer;

	
	
	
	@Override
	public void onInsertEvent(Object event) throws EventRejectedException {
		//we add our NetworkNodes (which are the class behind the event) to our cache. put will always overwrite old node data with new one.
		NetworkNode node = (NetworkNode) event;
		networkNodes.put(node.getMacAddress(),node);
		
				
		// case when we just started our server and this is the first time it tries to send the nodes.
		if(lastPushToServer == null){
			Date now = new Date();
			lastPushToServer = new Date(now.getTime() - 1000*595);
		}
		
		if(lastPushToServer.getTime() < (new Date().getTime() - 1000*60*10)){
			sendNetworkNodes();
			networkNodes.clear();
			lastPushToServer.setTime(System.currentTimeMillis());
		}
		
		
	}


	public void sendNetworkNodes(){
		String requestURLString = serverURL + "/addmultiple";
		
		JSONArray jsonArray = new JSONArray();
		for(NetworkNode node : networkNodes.values()){
			JSONObject obj = new JSONObject();
			obj.put("macAddress", node.getMacAddress());
			obj.put("dnsName", node.getDnsName());
			obj.put("lastKnownIPAddress", node.getLastKnownIPAddress());
			obj.put("lastSeen", node.getLastSeen().getTime());
			jsonArray.add(obj);
		}
		
		sendJSON(requestURLString, jsonArray.toJSONString());
	}

	public String sendJSON(String urlString, String payload) {

		System.out.println("sending event with all found nodes to server");
		System.out.println("Payload: " + payload);
		String line;
		StringBuffer jsonString = new StringBuffer();
		try {

			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
			
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch(ProtocolException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		//TODO just for debugging
		System.out.println(jsonString.toString());
		return jsonString.toString();
	}

	public String getServerURL() {
		return serverURL;
	}



	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}



	public Map getNetworkNodes() {
		return networkNodes;
	}



	public void setNetworkNodes(Map networkNodes) {
		this.networkNodes = networkNodes;
	}

	

}
