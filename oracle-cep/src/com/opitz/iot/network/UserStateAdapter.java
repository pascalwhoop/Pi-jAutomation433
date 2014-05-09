package com.opitz.iot.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

import com.bea.wlevs.ede.api.Adapter;
import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.StreamSink;

public class UserStateAdapter implements Adapter, StreamSink {

	public String serverURL;

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	@Override
	public void onInsertEvent(Object event) throws EventRejectedException {
		System.out.println("foo bar to the moon");
		UserStateEvent e = (UserStateEvent) event;
		
		//only users who's state has changed are passed as a userStateEvent. yet we dont want the missing ones only online and offline in our application
		if(e.getUserState() != UserState.MISSING){
			sendUserState(e.getUsername(), e.getUserState());
	    }

		

	}
	
	public void sendUserState(String username, UserState userState){
		String requestURLString = serverURL + "/" + username + "/state/" + userState;
		sendJSON(requestURLString, "");
	}

	public String sendJSON(String urlString, String payload) {

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
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		//TODO just for debugging
		System.out.println(jsonString.toString());
		return jsonString.toString();
	}

}
