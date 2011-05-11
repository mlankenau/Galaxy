package org.galaxy;

import java.util.Date;

import static junit.framework.Assert.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.junit.Test;



public class ShipTest {

	@Test
	public void testJson() {
		Game game = new Game();
		game.initEmpty();
		
		long time = new Date().getTime();
		
		Planet a = new Planet(0, game.getMe(), new Vector(0,0), PlanetClass.planetClasses[1]);
		Planet b = new Planet(1, game.getMe(), new Vector(0,0), PlanetClass.planetClasses[1]);
		game.getPlanets().add(a);
		game.getPlanets().add(b);
		
		Ship ship = new Ship(game.getMe(),a, b, 10.f, time);
		String jsonString = ship.toJson();
		
		assertNotNull(jsonString);
		
		JSONObject obj = (JSONObject) JSONValue.parse(jsonString);
		Ship shipCopy = new Ship(game, obj);
		
		assertEquals(ship.getSource(), shipCopy.getSource());
		assertEquals(ship.getDest(), shipCopy.getDest());
		assertEquals(game.getOpponent(), shipCopy.getParty());
		assertEquals(ship.getSpeed(), shipCopy.getSpeed());
		assertEquals(ship.getDeviation(), shipCopy.getDeviation());
	}
	
	@Test
	public void testShipToJson() {
		Game game = new Game();
		game.initEmpty();
		
		long time = new Date().getTime();
		
		Planet a = new Planet(0, game.getMe(), new Vector(0,0), PlanetClass.planetClasses[1]);
		Planet b = new Planet(1, game.getMe(), new Vector(0,0), PlanetClass.planetClasses[1]);
		game.getPlanets().add(a);
		game.getPlanets().add(b);
		
		Ship ship = new Ship(game.getMe(),a, b, 10.f, time);
		game.getShips().add(ship);
		ship = new Ship(game.getMe(),a, b, 10.f, time);
		game.getShips().add(ship);
		
		String json = Remote.shipsToJson(game.getShips());
		JSONArray array = (JSONArray) JSONValue.parse(json);
		assertNotNull(array);
	}
	
	@Test
	public void jsonTest()  {
		String json = "[{\"party\": \"me\",  \"source\": \"0\",  \"destination\": \"1\",  \"speed\": 40.0,  \"lauchTime\": 1305093877554,  \"deviation\": 6.3005896},{  \"party\": \"me\",  \"source\": \"0\",  \"destination\": \"1\",  \"speed\": 40.0,  \"lauchTime\": 1305093877623,  \"deviation\": -11.564152},{  \"party\": \"me\",  \"source\": \"0\",  \"destination\": \"1\",  \"speed\": 40.0,  \"lauchTime\": 1305093877632,  \"deviation\": 14.888242}]";
		JSONArray array = (JSONArray) JSONValue.parse(json);
		assertNotNull(array);
	}
}
