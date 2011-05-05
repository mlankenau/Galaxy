package org.galaxy;

import java.util.ArrayList;
import java.util.List;

public class Planet {
	private Vector pos;
	private float size;
	private float energy;
	private Party party;
	private PlanetClass pc;
	
	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public Planet(Party party, Vector pos, PlanetClass pc) {
		this.pos = pos;
		this.size = pc.getSize();
		this.energy = pc.getInitialEnergy();
		this.party = party;
		this.pc = pc;
	}

	public float getSize() {
		return size;
	}
	
	public int getEnergy() {
		return (int) Math.floor(energy);
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public void grow(float period) {
		if (party.hasGrows()) {
			energy += period * pc.getGrowth();
			if (energy > pc.getMaxEnergy()) energy = pc.getMaxEnergy();
		}
	}
	
	public boolean detectCollision(Ship ship) {
		Vector delta = pos.sub(ship.getPos());
		float length = delta.length();
		return length <= size;
	}
	
	public boolean isHit(float hitx, float hity, float tolerance) {
		Vector delta = pos.sub(new Vector(hitx, hity));
		float length = delta.length();
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
	
	public List<Ship> launch(Planet to, long time) {
		ArrayList<Ship> shipsToStart = new ArrayList<Ship>();
		int n = (int) (energy * 0.5f);
		energy -= n;
		for (int i=0; i<n; i++)
			shipsToStart.add(new Ship(party, this, to, 40, time));
		
		return shipsToStart;
	}
}
