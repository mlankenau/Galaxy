package org.galaxy;

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
		
	}
	
}
