package org.galaxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class Remote {
	DatagramSocket socket = null;
	
	
	public Remote(String host, int port, Party party) {
			try {
				socket = new DatagramSocket(8888);
				socket.connect(InetAddress.getByName(host), port);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
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
	
}
