package org.galaxy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.galaxy.net.HttpUtil;
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
	public void testStartMultiplayer() throws Exception {
		JSONArray players = getPlayers();
		System.out.println("players: " + players);
		
		GameInitiator.CallbackStartMultiplay callback = mock(GameInitiator.CallbackStartMultiplay.class);		
		startMultiplayer(callback, 500);
		
		verify(callback).timedout();
	}
	

	
	
	
}
