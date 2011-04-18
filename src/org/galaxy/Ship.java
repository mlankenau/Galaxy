package org.galaxy;

import java.util.List;

public class Ship {
	Planet source = null;
	Planet dest = null;
	private Party party;
	float x;
	float y;
	float speed;	
	float fuzzyness = 5f;
	List<Vector> path = null;
	int pathIndex = 0;
	
	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
	
	public Ship(Party party, Planet source, Planet dest, float speed) {
		this.party = party;
		this.source = source;
		this.dest = dest;
		int minX = Math.min((int)source.getX(), (int)dest.getX());
		int maxX = Math.max((int)source.getX(), (int)dest.getX());
		int minY = Math.min((int)source.getY(), (int)dest.getY());
		int maxY = Math.max((int)source.getY(), (int)dest.getY());
		this.path = Vector.bezier(
		  source.getPos(),
		  new Vector(Math.random()*(maxX-minX)+minX, Math.random()*(maxY-minY)-minY),
		  new Vector(Math.random()*(maxX-minX)+minX, Math.random()*(maxY-minY)-minY),
		  dest.getPos(),
		  (int)(Math.random()*50 + 100)
		);
		
		this.speed = speed;
		x = source.getX();
		y = source.getY();
		
//		Vector delta = dest.getVector().sub(getVector()); 
//		delta = delta.normalize();
//		Vector move = delta.multiply(source.getSize());
//		
//		x += move.getX();
//		y += move.getY();		
	}
		
	public void move(float period) {
	  if(pathIndex < path.size()) pathIndex++;
	  x = path.get(pathIndex).getX();
	  y = path.get(pathIndex).getY();
	}
		
	/*
	public void move(float period) {		
		float dx = dest.getX() - x;
		float dy = dest.getY() - y;
		float length = (float) Math.sqrt(dx*dx+dy*dy);
		dx /= length;
		dy /= length;
		dx *= speed * period;
		dy *= speed * period;
		
		dx += (Math.random() * fuzzyness) - fuzzyness / 2.f;
		dy += (Math.random() * fuzzyness) - fuzzyness / 2.f;
		
		x += dx;
		y += dy;
	}
  */
  
	public Planet getSource() {
		return source;
	}

	public Planet getDest() {
		return dest;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}	
	
	public Vector getVector() {
		return new Vector(x, y);
	}
}
