package com.opitz.iot.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bea.wlevs.ede.api.Adapter;
import com.bea.wlevs.ede.api.RunnableBean;

public class UserNodeBean implements Adapter, RunnableBean { 
    
	public String serverUrlString;
    private Map<String, UserMacPair> users;
    
    private static final int SLEEP_MILLIS = 1000 * 60 * 10; //calls our server every 10 minutes and asks for new users. if user is added, state updates may be delayed if Bean is currently "asleep" 
    private boolean suspended;
    JSONParser jsonParser = new JSONParser();

	@Override
	public void run() {
		suspended = false;
        while (!isSuspended()) { 
        	
        	Map<String, UserMacPair> newUserMap = getUserMap();
        	if(newUserMap != null){
        		users.clear();
        		users.putAll(newUserMap);
        	}
        	
        	
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
	public Map<String, UserMacPair> getUserMap() {
		 
		Object o = performHttpRequest();
		if(o == null){
			throw new NullPointerException("json object parsing failed!");
		}
		
        JSONObject jsonObject = (JSONObject) o;
        return getUserMapFromJSONObject(jsonObject);	
	}

	private Map<String, UserMacPair> getUserMapFromJSONObject(JSONObject jsonObject) {
		Map<String, String> keyValueMap = (HashMap<String, String>)jsonObject;
		Map<String, UserMacPair> userMap = new HashMap<String, UserMacPair>();
		for(Entry<String, String> entry : keyValueMap.entrySet()){
			userMap.put(entry.getKey().toLowerCase(), new UserMacPair(entry.getValue().toLowerCase(),entry.getKey().toLowerCase()));
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

    public Map<String, UserMacPair> getUsers() {
		return users;
	}

	public void setUsers(Map<String, UserMacPair> users) {
		this.users = users;
	}




	
}
