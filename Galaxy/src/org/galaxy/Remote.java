package org.galaxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Remote  {
	DatagramSocket socket = null;
	boolean first = true;
	
	public boolean isFirst() {
		return first;
	}

	public Remote(String host, int port, Party party) {
		try {
			socket = new DatagramSocket(10001);
			socket.connect(InetAddress.getByName(host), 10002);
		}
		catch (BindException be) {
			try {
				socket = new DatagramSocket(10002);
				socket.connect(InetAddress.getByName(host), 10001);
				first = false;
			}
			catch (Exception e) {
				e.printStackTrace();					
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				receiveThread();
			}			
		};
		new Thread(runnable).start();		
	}
	
	protected void receiveThread() {
		while (true) {
			byte[] buffer = new byte[10000]; 
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(packet);
				String s = new String(buffer, "UTF-8");
				System.out.println("received: " + s);								
				JSONArray array = (JSONArray) JSONValue.parse(s);
				for (int i=0; i<array.size(); i++) {
					JSONObject shipObj = (JSONObject) array.get(i);
					String party = shipObj.get("party").toString();
					String source = shipObj.get("source").toString();
					String destination = shipObj.get("destination").toString();
					String speed = shipObj.get("speed").toString();
					String lauchTime = shipObj.get("lauchTime").toString();
					String deviation = shipObj.get("deviation").toString();
				}
					
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void sendMove(List<Ship> ships) {
		StringBuffer message = new StringBuffer();
		message.append("[");
		for (Ship ship : ships) {
			message.append(ship.toJson());
		}
		message.append("]");
		System.out.println(message);
		
		byte[] bytes;
		try {
			bytes = message.toString().getBytes("UTF-8");
			System.out.println("size: " + bytes.length);
			DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
			socket.send(packet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void getMoves() {
	}

	
}
