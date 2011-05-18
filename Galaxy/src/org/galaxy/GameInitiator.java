package org.galaxy;

import java.net.InetAddress;
import java.util.Date;

import org.galaxy.net.HttpUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

public class GameInitiator {
	public static final String BASE_SERVER_URL = "http://sharp-fog-360.heroku.com";
	
	//protected Remote remote = null;
	protected String myName = null;
	protected String baseUrl = BASE_SERVER_URL;
	protected HttpUtil httpUtil = new HttpUtil();
	
	
	public interface CallbackStartMultiplay {		
		void timedout();		
		void gameStart();
	}
	
	
	
	/**
	 * ctor
	 * @param name
	 * @param baseUrl
	 */
	public GameInitiator(String name, String baseUrl) {
		this.myName = name;
		this.baseUrl = baseUrl;
	}
	
	
	protected JSONArray getPlayers() throws Exception {				
		String data = httpUtil.httpGet(baseUrl + "/players"); 						
		return (JSONArray) JSONValue.parse(data); 				
	}
	
	protected void enlist(int port, String name) throws Exception {
		String cmdUrl = baseUrl + "/enlist/" + InetAddress.getLocalHost().getHostAddress() + "?name="+name + "&port="+port;
		httpUtil.httpGet(cmdUrl);		
	}

	
	public void startMultiplayer(CallbackStartMultiplay callbackStart, long timeout) {				
		Date start = new Date();
				
		try {
			while (new Date().getTime() - start.getTime() < timeout) {				
				JSONArray players = getPlayers();								
				if (players.size() > 0) {
					
				}
				
				Thread.sleep(Math.min(1000, timeout / 4));
			}
						
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		callbackStart.timedout();		
	}
	
	

		
	
	
}
