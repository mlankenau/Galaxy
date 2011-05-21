package org.galaxy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.net.DatagramSocket;

import org.galaxy.net.HttpUtil;
import org.galaxy.net.UDPUtil;
import org.json.simple.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class GameInitiatorTest extends GameInitiator {
	final static String LOCAL_TEST_SERVER = "http://localhost:8090";
	
	public GameInitiatorTest() {
		super("tralala", LOCAL_TEST_SERVER);
	}
	
	@Before
	public void setup() throws Exception {
		new HttpUtil().httpGet(LOCAL_TEST_SERVER + "/clear");
	}
	
	@Test
	public void testServer() throws Exception {
		enlist(1001, "marcus");
		enlist(1002, "sebastian");
		
		JSONArray players = getPlayers();
		System.out.println("players: " + players);	
	}
	
	@Test
	public void testStartMultiplayerTimeout() throws Exception {
		JSONArray players = getPlayers();
		System.out.println("players: " + players);
		
		GameInitiator.CallbackStartMultiplay callback = mock(GameInitiator.CallbackStartMultiplay.class);		
		startMultiplayer(callback, 10002, 500);
		
		verify(callback).timedout();
	}
	

	
	@Test
	public void testStartMultiplayerConnectExistingPeer() throws Exception {
		DatagramSocket socket = new DatagramSocket(10001);
		
		try {
			enlist(10001, "Heinz");
			
			
			JSONArray players = getPlayers();
			System.out.println("players: " + players);
			
			GameInitiator.CallbackStartMultiplay callback = mock(GameInitiator.CallbackStartMultiplay.class);		
			startMultiplayer(callback, 10003, 500);
				
			String message = UDPUtil.receive(socket);
			assertEquals("connect", message);
		
		}
		finally {
			socket.close();
		}
	}
	
	
	
}
