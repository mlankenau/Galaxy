package org.galaxy;

import java.util.ArrayList;
import java.util.Date;

public class Game {
	private ArrayList<Planet> planets = new ArrayList<Planet>();
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private ArrayList<Move> moves = new ArrayList<Move>();

	private Party player = null;
	private Party computer = null;
	private Party neutral = null;
	
	
	public Party getPlayer() {
		return player;
	}
	public Party getComputer() {
		return computer;
	}
	public Party getNeutral() {
		return neutral;
	}
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}
	public ArrayList<Ship> getShips() {
		return ships;
	}
	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}
	
	/**
	 * init game
	 */
	public void init() {
		float boarder = 30;

		player = new Party("player", 0xff00a000);
		computer = new Party("computer", 0xffa00000);
		neutral = new Party("", 0xffa0a0a0);

		for (int i = 0; i < 6; i++) {
			
			PlanetClass pc = PlanetClass.planetClasses[(int)(Math.random() * PlanetClass.planetClasses.length)];
			

			float x = 0;
			float y = 0;
			while (true) {
				x = (float) (boarder + Math.random() * (320.f - 2.f * boarder));
				y = (float) (boarder + Math.random() * (400.f - 2.f * boarder));

				boolean collision = false;
				for (Planet p : planets) {
					Vector vp = p.getPos();
					Vector dist = vp.sub(new Vector(x, y));
					if (dist.length() < p.getSize() + pc.getSize() + 10.f)
						collision = true;
				}

				if (!collision)
					break;
			}

			Party party = neutral;
			if (i == 0)
				party = player;
			if (i == 1)
				party = computer;

			planets.add(new Planet(party, new Vector(x, y), pc));
		}
	}

	
	/**
	 * find a planet at the coordinates
	 */
	public Planet findPlanet(float x, float y, Party party) {
		for (Planet planet : planets) {
			if ((party == null || planet.getParty() == party)
					&& planet.isHit(x, y, 30))
				return planet;
		}
		return null;
	}
	
	
	/**
	 * execute a cylce, move ships, grow planets
	 * @param duration
	 */
	public void cycle(float duration) {
		long time = new Date().getTime();
		
		for (Planet planet : planets) {
			planet.grow(duration);
		}

		ArrayList<Ship> deadShips = new ArrayList<Ship>();
		for (Ship ship : ships) {
			ship.move(time);

			for (Planet planet : planets) {
				if (planet.detectCollision(ship)
						&& planet != ship.getSource()) {
					
					// if it is his own party and not destination, ignore
					if (ship.getParty() == planet.getParty() && ship.getDest() != planet) continue;
					
					planet.collide(ship);
					deadShips.add(ship);
				}
			}
		}
		for (Ship ship : deadShips) {
			ships.remove(ship);
		}
	}
}
