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


/**
 * This class passes our created events to the server.
 * @author Brokmeier, Pascal (pbr)
 *
 */
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
		UserStateEvent e = (UserStateEvent) event;
		
		System.out.println("#### firing event for " + e.getUsername() + " and state " + e.getUserState().toString());
		sendUserState(e.getUsername(), e.getUserState());
	 

		

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
			connection.setRequestMethod("PUT");
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
			e.printStackTrace();
		}
		
		//TODO just for debugging
		System.out.println(jsonString.toString());
		return jsonString.toString();
	}

}
