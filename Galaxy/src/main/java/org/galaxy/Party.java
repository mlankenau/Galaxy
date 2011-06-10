package org.galaxy;


public class Party {
	String name = "";
	int color;
	
	public Party(String name, int color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public int getColor() {
		return color;
	}
	
	public boolean hasGrows() {
		return !name.equals(""); 
	}
	
	public String getId() {
		return name;
	}
	
	public String toString() {
		return "party:"+name;
	}
	
}
