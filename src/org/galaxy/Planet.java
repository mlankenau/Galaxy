package org.galaxy;

import java.util.ArrayList;
import java.util.List;

public class Planet {
	private float x;
	private float y;
	private float size;
	private float energy;
	private Party party;
	
	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public Planet(Party party, float x, float y, float size, int energy) {
		this.x  =x;
		this.y = y;
		this.size = size;
		this.energy = energy;
		this.party = party;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getSize() {
		return size;
	}
	
	public int getEnergy() {
		return (int) Math.floor(energy);
	}
	
	public Vector getVector() {
		return new Vector(x, y);
	}
	
	public void grow(float period) {
		if (party.hasGrows()) {
			energy += period * size*size / 1000;
		}
	}
	
	public boolean detectCollision(Ship ship) {
		float dx = x - ship.getX();
		float dy = y - ship.getY();
		float length = (float) Math.sqrt(dx*dx+dy*dy);
		return length <= size;
	}
	
	public boolean isHit(float hitx, float hity, float tolerance) {
		float dx = x - hitx;
		float dy = y - hity;
		float length = (float) Math.sqrt(dx*dx+dy*dy);
		return length <= size + tolerance;
	}
	
	public void collide(Ship ship) {
		if (ship.getParty() == party) {
			energy += 1;
		}
		else {
			energy -= 1;
			if (energy <= 1) {
				party = ship.getParty();
			}
		}
			
	}
	
	public List<Ship> launch(Planet to) {
		ArrayList<Ship> shipsToStart = new ArrayList<Ship>();
		int n = (int) (energy * 0.4f);
		energy -= n;
		for (int i=0; i<n; i++)
			shipsToStart.add(new Ship(party, this, to, 40));
		
		return shipsToStart;
	}
}
