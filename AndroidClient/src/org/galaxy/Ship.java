package org.galaxy;

public class Ship {
	Planet source = null;
	Planet dest =  null;
	private Party party;
	
	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
	
	Vector pos;
	float speed;
	
	float fuzzyness = 5f;
	
	public Ship(Party party, Planet source, Planet dest, float speed) {
		this.party = party;
		this.source = source;
		this.dest = dest;
		this.speed = speed;
		pos = source.getPos();

		Vector delta = dest.getPos().sub(pos); 
		delta = delta.normalize();
		Vector move = delta.multiply(source.getSize());		
		pos = pos.add(move);
	}
		
	public void move(float period) {		
		Vector delta = dest.getPos().sub(pos);
		delta = delta.normalize();
		delta = delta.multiply(speed * period);
		
		delta = delta.add(new Vector((float) (Math.random() * fuzzyness) - fuzzyness / 2.f, (float) (Math.random() * fuzzyness) - fuzzyness / 2.f));
		pos = pos.add(delta);
	}

	public Planet getSource() {
		return source;
	}

	public Planet getDest() {
		return dest;
	}

	
	public Vector getPos() {
		return pos;
	}
}
