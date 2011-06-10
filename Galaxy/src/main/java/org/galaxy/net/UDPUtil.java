package org.galaxy.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.galaxy.Ship;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class UDPUtil {

	/**
	 * receive a packet from udp
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	public static String receive(DatagramSocket socket) throws IOException {
		byte[] buffer = new byte[100000]; 
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		return new String(buffer, packet.getOffset(), packet.getLength(), "UTF-8");
	}
	
	
	/**
	 * send a packet
	 * @param socket
	 * @param message
	 * @throws IOException
	 */
	public static void send(DatagramSocket socket, String message) throws IOException {
		byte[] bytes;
		try {
			bytes = message.toString().getBytes("UTF-8");
			DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
			socket.send(packet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
