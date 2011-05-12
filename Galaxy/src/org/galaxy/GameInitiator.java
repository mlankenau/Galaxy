package org.galaxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GameInitiator {
	Remote remote = null;
	
	public static final String BASE_SERVER_URL = "http://sharp-fog-360.heroku.com";
	
	String baseUrl = BASE_SERVER_URL; 
		
	
	protected static String httpGet(String address) throws Exception {
		URL url = new URL(address);
		URLConnection yc = url.openConnection();
	    BufferedReader in = new BufferedReader(
	                            new InputStreamReader(
	                            yc.getInputStream()));
	    String inputLine;
	
	    StringBuffer resultBuffer = new StringBuffer();
	    while ((inputLine = in.readLine()) != null) 
	    	resultBuffer.append(inputLine+ "\n");
	    in.close();
	    
	    return resultBuffer.toString();
	}
	
	
	protected static void enlist(String url, int port, String name) throws Exception {
		String cmdUrl = url + "/enlist/" + InetAddress.getLocalHost().getHostAddress() + "?name="+name + "&port="+port;
		httpGet(cmdUrl);
		
	}
	
	protected static String getPlayers(String url) throws Exception {		
		//URL urlConnection = new URL(url + "/players");
		//String result = readStream((InputStream)urlConnection.openStream());
		
		return httpGet(url + "/players");
	}
	
	
	
	public GameInitiator(String name) {
		
	}
	
	
	public void startMultiplayer() {		
		//getPlayers(baseUrl);
	}
		
	
	
}
