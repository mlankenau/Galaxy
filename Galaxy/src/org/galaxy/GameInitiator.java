package org.galaxy;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

import org.galaxy.net.HttpUtil;
import org.galaxy.net.UDPUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class GameInitiator {
	public static final String BASE_SERVER_URL = "http://sharp-fog-360.heroku.com";
	
	//protected Remote remote = null;
	protected String myName = null;
	protected String baseUrl = BASE_SERVER_URL;
	protected HttpUtil httpUtil = new HttpUtil();
	
	DatagramSocket socket = null;
	
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

	
	public void startMultiplayer(CallbackStartMultiplay callbackStart, int myport, long timeout) throws SocketException {				
		Date start = new Date();
			
		socket = new DatagramSocket(myport);
		
		try {
			while (new Date().getTime() - start.getTime() < timeout) {				
				JSONArray players = getPlayers();								
				if (players.size() > 0) {
					JSONObject playerJson = (JSONObject) players.get(0);

					socket.connect(InetAddress.getByName((String) playerJson.get("ip")), Integer.parseInt((String) playerJson.get("port")));
					UDPUtil.send(socket, "connect");
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
