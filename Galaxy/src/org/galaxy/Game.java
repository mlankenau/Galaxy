package org.galaxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Game {
	private ArrayList<Planet> planets = new ArrayList<Planet>();
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private ArrayList<Move> moves = new ArrayList<Move>();

	private Party me = null;
	private Party opponent = null;
	private Party neutral = null;
	
	Remote remote = null;
	
	public Party getMe() {
		return me;
	}
	public Party getOpponent() {
		return opponent;
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
	
		
	public void initEmpty() {
		me = new Party("me", 0xff00a000);
		opponent = new Party("opponent", 0xffa00000);
		neutral = new Party("", 0xffa0a0a0);
		
		remote = new Remote(this, "localhost", 10001, opponent);
	}
	
	/**
	 * init game
	 */
	public void initRandom() {
		float boarder = 30;

		me = new Party("player", 0xff00a000);
		opponent = new Party("computer", 0xffa00000);
		neutral = new Party("", 0xffa0a0a0);

		remote = new Remote(this, "localhost", 10001, opponent);
		
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
				party = me;
			if (i == 1)
				party = opponent;

			planets.add(new Planet(i, party, new Vector(x, y), pc));
		}
	}

	
	public void chooseSide() {
		if (!remote.isFirst()) {
			takeOtherSide();
		}
	}
	
	private void takeOtherSide() {
		for (Planet planet : planets) {
			if (planet.getParty() == me) 
				planet.setParty(opponent);
			else if (planet.getParty() == opponent)
				planet.setParty(me);
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
		
		ships.addAll(remote.getMoves());
		
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
	
	
	public void move(Planet source, Planet target)  {
		List<Ship> newShips = source.launch(target, new Date().getTime()); 
		ships.addAll(newShips);
		remote.sendMove(newShips);
	}
	
	public Planet findPlanet(String id) {
		for (Planet p : planets) {
			if (p.getId().equals(id)) 
				return p;
		}
		return null;
	}
	
}
