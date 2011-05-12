package org.galaxy;

import org.junit.Test;

public class GameInitiatorTest extends GameInitiator{
	String LOCAL_TEST_SERVER = "http://localhost:8090";
	
	public GameInitiatorTest() {
		super("tralala");
	}
	
	@Test
	public void testServer() throws Exception {
		enlist(LOCAL_TEST_SERVER, 1001, "marcus");
		enlist(LOCAL_TEST_SERVER, 1002, "sebastian");
		
		String players = getPlayers(LOCAL_TEST_SERVER);
		System.out.println("players: " + players);
		
	}
}
