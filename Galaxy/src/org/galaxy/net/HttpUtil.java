package org.galaxy.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
	
	public String httpGet(String address) throws Exception {
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
	
}
