package com.opitz.iot.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bea.wlevs.ede.api.Adapter;
import com.bea.wlevs.ede.api.EventBean;
import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.RunnableBean;
import com.bea.wlevs.ede.api.StreamSink;

public class UserNodeBean implements Adapter, RunnableBean, StreamSink { 
    
	public String serverUrlString;
    private Map<String, UserNode> users;
    private Map<String, UserState> userStates;
    
    private static final int SLEEP_MILLIS = 1000 * 60 * 10; //calls our server every 10 minutes and asks for new users. if user is added, state updates may be delayed if Bean is currently "asleep" 
    private boolean suspended;
    JSONParser jsonParser = new JSONParser();

	@Override
	public void run() {
		suspended = false;
        while (!isSuspended()) { 
        	
        	users = getUserMap();
        	
        	try {
                synchronized (this) {
                    wait(SLEEP_MILLIS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	/**
	 * calls the spring server and returns a hash map of (key)macAddress - (value)username
	 * @return
	 */
	public Map<String, UserNode> getUserMap() {
		 
		Object o = performHttpRequest();
		if(o == null){
			throw new NullPointerException("json object parsing failed!");
		}
		
        JSONObject jsonObject = (JSONObject) o;
        return getUserMapFromJSONObject(jsonObject);	
	}

	private Map<String, UserNode> getUserMapFromJSONObject(JSONObject jsonObject) {
		Map<String, String> keyValueMap = (HashMap<String, String>)jsonObject;
		Map<String, UserNode> userMap = new HashMap<String, UserNode>();
		for(Entry<String, String> entry : keyValueMap.entrySet()){
			userMap.put(entry.getKey(), new UserNode(entry.getValue(),entry.getKey()));
		}
		return userMap;
	}

	private Object performHttpRequest() {
		String line;
		StringBuffer jsonString = new StringBuffer();
		try {

			URL url = new URL(serverUrlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
		
			
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
		System.out.println("### requesting users. JSON: "  + jsonString.toString());
		
        
        return parseJSON(jsonString);
	}

	private Object parseJSON(StringBuffer jsonString) {
		Object o = null;
		try {
			o = jsonParser.parse(jsonString.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	
	
	public String getServerURL() {
		return serverUrlString;
	}

	public void setServerURL(String serverURL) {
		this.serverUrlString = serverURL;
	}

	@Override
    public synchronized void suspend() {
        suspended = true;
    }

    private synchronized boolean isSuspended() {
        return suspended;
    }

    public Map<String, UserNode> getUsers() {
		return users;
	}

	public void setUsers(Map<String, UserNode> users) {
		this.users = users;
	}

	public Map<String, UserState> getUserStates() {
		return userStates;
	}

	public void setUserStates(Map<String, UserState> userStates) {
		this.userStates = userStates;
	}

	/**
     * Method that is called when the processor kicks out an UserStateEvent.
     * We then extract the new state and push it into our cache so further iterations of our processor will work with the new state
     */
	@Override
	public void onInsertEvent(Object event) throws EventRejectedException {
		UserStateEvent userStateEvent = (UserStateEvent) event;
		System.out.println("###adding user state to userStateCache " + userStateEvent.getUsername() + userStateEvent.getTimestamp());
		userStates.put(userStateEvent.getUsername(), userStateEvent.getUserState());	
	}
}
