package org.galaxy;

public class GameFactory {
	
	
	
	public static Game getLevel(int level) {
		Game game = new Game();
		
		game.initEmpty();
		
		game.getPlanets().add(new Planet(game.getComputer(), new Vector(50, 50), PlanetClass.planetClasses[0]));		
		
		game.getPlanets().add(new Planet(game.getNeutral(), new Vector(70, 100), PlanetClass.planetClasses[1]));
		
		game.getPlanets().add(new Planet(game.getNeutral(), new Vector(200, 150), PlanetClass.planetClasses[1]));
		
		game.getPlanets().add(new Planet(game.getNeutral(), new Vector(250, 300), PlanetClass.planetClasses[2]));
		
		game.getPlanets().add(new Planet(game.getNeutral(), new Vector(350, 390), PlanetClass.planetClasses[1]));
		game.getPlanets().add(new Planet(game.getNeutral(), new Vector(100, 410), PlanetClass.planetClasses[1]));
		
		game.getPlanets().add(new Planet(game.getPlayer(), new Vector(320, 450), PlanetClass.planetClasses[0]));
		
		return game;
	
	}
}
