package org.galaxy;

public class Move {
	private long timestamp;
	private Party owner;
	private Planet from;
	private Planet to;
	private int ships;
	
	public Move(long timestamp, Party owner, Planet from, Planet to, int ships) {
		this.timestamp = timestamp;
		this.owner = owner;
		this.from = from;
		this.to = to;
		this.ships = ships;
	}
		
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public Party getOwner() {
		return owner;
	}
	public void setOwner(Party owner) {
		this.owner = owner;
	}
	public Planet getFrom() {
		return from;
	}
	public void setFrom(Planet from) {
		this.from = from;
	}
	public Planet getTo() {
		return to;
	}
	public void setTo(Planet to) {
		this.to = to;
	}
	public int getShips() {
		return ships;
	}
	public void setShips(int ships) {
		this.ships = ships;
	}
	
	
}
